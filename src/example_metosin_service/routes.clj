(ns example-metosin-service.routes
  (:require
    [halboy.resource :as hal]
    [halboy.json :as hal-json]
    [hype.core :as hype]

    [example-metosin-service.users :as users]))

(def successful-defaults {:status  200
                          :headers {"content-type" "application/hal+json"}})

(def routes
  [""
   [["/" :discovery]
    ["/ping" :ping]
    [["/users/" :user-id]
     [["" :user]]]
    ["/users" :users]
    [true :no-route]]])

(def handlers
  {:discovery {:get {:handler (fn handler [request]
                                (assoc successful-defaults
                                  :body (->
                                          (hal/new-resource)
                                          (hal/add-links
                                            {:self      (hype/absolute-url-for request routes :discovery)
                                             :discovery (hype/absolute-url-for request routes :discovery)
                                             :ping      (hype/absolute-url-for request routes :ping)
                                             :users     (hype/absolute-url-for request routes :users)})
                                          (hal-json/resource->map))))}}
   :ping      {:get {:handler (fn handler [request]
                                (assoc successful-defaults
                                  :body (->
                                          (hal/new-resource)
                                          (hal/add-links
                                            {:self      (hype/absolute-url-for request routes :ping)
                                             :discovery (hype/absolute-url-for request routes :discovery)})
                                          (hal-json/resource->map))))}}
   :users     {:post {:handler (fn handler [request]
                                 (assoc successful-defaults
                                   :status 201
                                   :body (users/create request routes (:body-params request))))}}})
