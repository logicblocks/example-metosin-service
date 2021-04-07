(ns example-metosin-service.test-support.system
  (:require
   [freeport.core :refer [get-free-port!]]

   [example-metosin-service.server :as server]))

(defn test-system-configuration []
  (let [port (get-free-port!)]
    {:port    port
     :address (format "http://localhost:%s" port)}))

(defn with-system-lifecycle [system-atom configuration]
  (fn [f] (try
            (do
              (server/start system-atom configuration)
              (f))
            (finally
              (server/stop system-atom)))))
