(ns example-metosin-service.routes)

(def successful-get-defaults {:status  200
                              :headers {"content-type" "application/json"}})

(def routes
  [["/" {:get {:handler (fn handler [_]
                          (assoc successful-get-defaults
                            :body {:status "OK"}))}}]])
