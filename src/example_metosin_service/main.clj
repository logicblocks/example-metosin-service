(ns example-metosin-service.main
  (:gen-class)
  (:require
   [example-metosin-service.server :as server]
   [integrant.core :as ig]))

(defn -main [& args]
  (let [system (ig/init server/config)]
    (println "Starting webserver")))
