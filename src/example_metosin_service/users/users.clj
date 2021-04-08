(ns example-metosin-service.users.users
  (:require [clj-uuid :as uuid]))

(defn create [user]
  (let [id (str (uuid/v1))]
    (assoc user :id id)))