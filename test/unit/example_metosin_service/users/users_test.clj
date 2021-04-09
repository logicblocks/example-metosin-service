(ns example-metosin-service.users.users-test
  (:require [clojure.test :refer :all]
            [example-metosin-service.users.users :as users]
            [crux.api :as crux]))

(deftest create-test
  (let [node (crux/start-node {})
        first-name "Bob"
        last-name "Andersson"
        user (users/create node {:first-name first-name
                                 :last-name  last-name})]
    (testing "has given properties"
      (is (= (:first-name user) first-name))
      (is (= (:last-name user) last-name))
      (is (some? (:id user))))))

(deftest by-id-test
  (let [node (crux/start-node {})
        first-name "Bob"
        last-name "Andersson"
        created-user (users/create node {:first-name first-name
                                         :last-name  last-name})
        user (users/by-id node (:id created-user))]
    (testing "returns user"
      (is (= user {:id         (:id created-user)
                   :first-name first-name
                   :last-name  last-name})))))