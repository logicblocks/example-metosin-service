(ns example-metosin-service.users
  (:require [clj-uuid :as uuid]
            [halboy.resource :as hal]
            [hype.core :as hype]
            [halboy.json :as hal-json]))

(defn create [request routes user]
  (let [id (str (uuid/v1))]
    (->
      (hal/new-resource)
      (hal/add-links
        {:self      (hype/absolute-url-for request routes :user {:path-params {:user-id id}})
         :discovery (hype/absolute-url-for request routes :discovery)})
      (hal/add-properties
        {:id        id
         :firstName (:first-name user)
         :lastName  (:last-name user)})
      (hal-json/resource->map))))