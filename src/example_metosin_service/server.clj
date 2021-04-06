(ns example-metosin-service.server
  (:require
    [aleph.http :as http]
    [camel-snake-kebab.core :as csk]
    [muuntaja.core :as m]
    [reitit.ring :as ring]
    [reitit.ring.middleware.muuntaja :as muuntaja-middleware]
    [ring.middleware.defaults :as defaults]

    [example-metosin-service.routes :as r]))

(def ring-opts
  {:data {:middleware [[defaults/wrap-defaults defaults/api-defaults]
                       muuntaja-middleware/format-negotiate-middleware
                       muuntaja-middleware/format-response-middleware
                       muuntaja-middleware/format-request-middleware]
          :muuntaja   (m/create
                        (assoc-in m/default-options
                                  [:formats "application/json" :encoder-opts]
                                  {:encode-key-fn csk/->camelCaseString}))}})

(def app
  (ring/ring-handler
    (ring/router r/routes ring-opts)))

(defn start [server configuration]
  (reset! server (http/start-server #'app configuration)))

(defn stop [server]
  (when @server
    (.close ^java.io.Closeable @server)))
