(ns example-metosin-service.server
  (:require
   [aleph.http :as http]
   [camel-snake-kebab.core :as csk]
   [integrant.core :as ig]
   [muuntaja.core :as m]
   [reitit.core :as reitit]
   [reitit.ring :as ring]
   [reitit.ring.middleware.muuntaja :as muuntaja-middleware]
   [ring.middleware.defaults :as defaults]

   [example-metosin-service.routes :as r]
   [crux.api :as crux]))

(defn registry-expand [registry]
  (fn [data opts]
    (if (keyword? data)
      (some-> data
        registry
        (reitit/expand opts)
        (assoc :name data))
      (reitit/expand data opts))))

(defn ring-opts [crux]
  {:data
   {:middleware
    [[defaults/wrap-defaults defaults/api-defaults]
     muuntaja-middleware/format-negotiate-middleware
     muuntaja-middleware/format-response-middleware
     muuntaja-middleware/format-request-middleware]
    :muuntaja
    (-> m/default-options
      (assoc-in
        [:formats "application/json" :decoder-opts]
        {:decode-key-fn csk/->kebab-case-keyword})
      (m/create))}
   :expand
   (registry-expand (r/handlers {:crux crux}))})

(defmethod ig/init-key :service/app [_ {:keys [crux]}]
  (ring/ring-handler
    (ring/router r/routes (ring-opts crux))))

(defmethod ig/init-key :service/crux [_ _]
  (crux/start-node {}))

(defmethod ig/init-key :adapter/service [_ {:keys [app port]}]
  (http/start-server app {:port port}))

(defmethod ig/halt-key! :adapter/service [_ server]
  (when server
    (.close ^java.io.Closeable server)))

(defmethod ig/init-key :service/port [_ {:keys [port]}]
  (or port
    (-> (System/getenv "PORT")
      (or "3000")
      (Integer/parseInt))))

(def config
  {:adapter/service {:port (ig/ref :service/port)
                     :app  (ig/ref :service/app)}
   :service/app     {:crux (ig/ref :service/crux)}
   :service/crux    {}
   :service/port    {}})