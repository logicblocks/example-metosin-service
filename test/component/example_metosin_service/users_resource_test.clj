(ns example-metosin-service.users-resource-test
  (:require
   [clojure.test :refer :all]
   [clojure.string :refer [ends-with?]]
   [halboy.resource :as hal]
   [halboy.navigator :as navigator]
   [org.bovinegenius.exploding-fish :refer [absolute?]]

   [example-metosin-service.test-support.system
    :refer [test-system-configuration
            with-system-lifecycle]]))

(let [configuration (test-system-configuration)]
  (use-fixtures :once (with-system-lifecycle (:ig-config configuration)))

  (deftest users-resource-POST-on-success
    (let [base-url (:address configuration)
          discovery-result (navigator/discover
                             base-url
                             {:follow-redirects false})
          first-name "Bob"
          last-name "Andersson"
          users-result (navigator/post
                         discovery-result
                         :users
                         {"firstName" first-name
                          "lastName"  last-name})
          users-resource (navigator/resource users-result)]
      (testing "returns status code 201"
        (is (= 201 (navigator/status users-result))))

      (testing "includes a link to discovery"
        (let [link (hal/get-href users-resource :discovery)]
          (is (absolute? link))
          (is (ends-with? link "/"))))

      (testing "includes a self-link"
        (let [link (hal/get-href users-resource :self)]
          (is (absolute? link))
          (is (ends-with? link "/users"))))

      (testing "has given properties"
        (is (= (hal/get-property users-resource :firstName) first-name))
        (is (= (hal/get-property users-resource :lastName) last-name))
        (is (some? (hal/get-property users-resource :id)))))))
