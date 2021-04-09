(ns example-metosin-service.users.users
  (:require [clj-uuid :as uuid]
            [crux.api :as crux]
            [clojure.set :as set]))

(defn create [node user]
  (let [id (str (uuid/v1))
        user (assoc user :id id)]
    (->>
      [[:crux.tx/put (set/rename-keys user {:id :crux.db/id})]]
      (crux/submit-tx node)
      (crux/await-tx node))
    user))

(defn by-id [node id]
  (->
    node
    (crux/db)
    (crux/entity id)
    (set/rename-keys {:crux.db/id :id})))