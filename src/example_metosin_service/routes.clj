(ns example-metosin-service.routes
  (:require
   [halboy.resource :as hal]
   [halboy.json :as hal-json]
   [hype.core :as hype]

   [example-metosin-service.users.users :as users]
   [example-metosin-service.users.mapping :as user-mapping]))

(def successful-defaults
  {:status  200
   :headers {"content-type" "application/hal+json"}})

(def routes
  [""
   [["/" :discovery]
    ["/ping" :ping]
    ["/users" :users]
    [true :no-route]]])

(defn handlers [dependencies]
  {:discovery
   {:get
    {:handler
     (fn handler [request]
       (assoc successful-defaults
         :body
         (->
           (hal/new-resource)
           (hal/add-links
             {:self      (hype/absolute-url-for request routes :discovery)
              :discovery (hype/absolute-url-for request routes :discovery)
              :ping      (hype/absolute-url-for request routes :ping)
              :users     (hype/absolute-url-for request routes :users)})
           (hal-json/resource->map))))}}
   :ping
   {:get
    {:handler
     (fn handler [request]
       (assoc successful-defaults
         :body
         (->
           (hal/new-resource)
           (hal/add-links
             {:self      (hype/absolute-url-for request routes :ping)
              :discovery (hype/absolute-url-for request routes :discovery)})
           (hal-json/resource->map))))}}
   :users
   {:post
    {:handler
     (fn handler [request]
       (assoc successful-defaults
         :status 201
         :body (->>
                 (:body-params request)
                 (users/create (:crux dependencies))
                 (user-mapping/->user-resource request routes))))}}})
