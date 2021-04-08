(ns example-metosin-service.users.mapping
  (:require [halboy.resource :as hal]
            [hype.core :as hype]
            [halboy.json :as hal-json]))

(defn ->user-resource [request routes user]
  (->
    (hal/new-resource)
    (hal/add-links
      {:self      (hype/absolute-url-for request routes :users)
       :discovery (hype/absolute-url-for request routes :discovery)})
    (hal/add-properties
      {:id        (:id user)
       :firstName (:first-name user)
       :lastName  (:last-name user)})
    (hal-json/resource->map)))