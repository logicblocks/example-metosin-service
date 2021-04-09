(ns example-metosin-service.test-support.system
  (:require
   [freeport.core :refer [get-free-port!]]
   [example-metosin-service.server :as server]

   [integrant.core :as ig]))

(defn test-system-configuration []
  (let [port (get-free-port!)]
    {:address   (format "http://localhost:%s" port)
     :ig-config (merge server/config
                  {:service/port {:port port}})}))

(defn with-system-lifecycle [configuration]
  (fn [f]
    (let [system (ig/init configuration)]
      (try
        (f)
        (finally
          (ig/halt! system))))))
