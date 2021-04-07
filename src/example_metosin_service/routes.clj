(ns example-metosin-service.routes
  (:require
   [halboy.resource :as hal]
   [halboy.json :as hal-json]
   [hype.core :as hype]))

(def successful-get-defaults {:status  200
                              :headers {"content-type" "application/json"}})

(def routes
  [""
   [["/" ::discovery]
    ["/ping" ::ping]
    [true :no-route]]])

(def handlers
  {::discovery {:get {:handler (fn handler [request]
                                 (assoc successful-get-defaults
                                   :body (->
                                           (hal/new-resource)
                                           (hal/add-links
                                             {:self      (hype/absolute-url-for request routes ::discovery)
                                              :discovery (hype/absolute-url-for request routes ::discovery)
                                              :ping      (hype/absolute-url-for request routes ::ping)})
                                           (hal-json/resource->map))))}}
   ::ping {:get {:handler (fn handler [request]
                            (assoc successful-get-defaults
                              :body (->
                                      (hal/new-resource)
                                      (hal/add-links
                                        {:self      (hype/absolute-url-for request routes ::ping)
                                         :discovery (hype/absolute-url-for request routes ::discovery)})
                                      (hal-json/resource->map))))}}})
