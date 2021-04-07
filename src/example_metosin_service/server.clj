(ns example-metosin-service.server
  (:require
   [aleph.http :as http]
   [camel-snake-kebab.core :as csk]
   [muuntaja.core :as m]
   [reitit.core :as reitit]
   [reitit.ring :as ring]
   [reitit.ring.middleware.muuntaja :as muuntaja-middleware]
   [ring.middleware.defaults :as defaults]

   [example-metosin-service.routes :as r]))

(defn registry-expand [registry]
  (fn [data opts]
    (if (keyword? data)
      (some-> data
        registry
        (reitit/expand opts)
        (assoc :name data))
      (reitit/expand data opts))))

(def ring-opts
  {:data   {:middleware [[defaults/wrap-defaults defaults/api-defaults]
                         muuntaja-middleware/format-negotiate-middleware
                         muuntaja-middleware/format-response-middleware
                         muuntaja-middleware/format-request-middleware]
            :muuntaja   (-> m/default-options
                          (assoc-in
                            [:formats "application/json" :decoder-opts]
                            {:decode-key-fn csk/->kebab-case-keyword})
                          (m/create))}
   :expand (registry-expand r/handlers)})

(def app
  (ring/ring-handler
    (ring/router r/routes ring-opts)))

(defn start [server configuration]
  (reset! server (http/start-server #'app configuration)))

(defn stop [server]
  (when @server
    (.close ^java.io.Closeable @server)))
