(ns example-metosin-service.main
  (:gen-class)
  (:require
   [example-metosin-service.server :as server]))

(defonce server (atom nil))

(def port
  (-> (System/getenv "PORT")
    (or "3000")
    (Integer/parseInt)))

(defn -main [& args]
  (println (format "Starting webserver on port: %s." port))
  (server/start server {:port port}))
