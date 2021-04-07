(ns example-metosin-service.discovery-resource-test
  (:require
   [clojure.test :refer :all]
   [halboy.resource :as hal]

   [org.bovinegenius.exploding-fish :refer [absolute?]]
   [clojure.string :refer [ends-with?]]

   [example-metosin-service.test-support.system
    :refer [test-system-configuration
            with-system-lifecycle]]
   [halboy.navigator :as navigator]))

(let [test-system (atom nil)
      configuration (test-system-configuration)]
  (use-fixtures :once (with-system-lifecycle test-system configuration))

  (deftest discovery-resource-GET-on-success
    (let [base-url (str (:address configuration) "/")
          result (navigator/discover base-url)
          resource (navigator/resource result)]
      (testing "returns status code 200"
        (is (= 200 (navigator/status result))))

      (testing "includes a self link"
        (let [self-link (hal/get-href resource :self)]
          (is (absolute? self-link))
          (is (ends-with? self-link "/"))))

      (testing "includes a link to discovery"
        (let [discovery-link (hal/get-href resource :discovery)]
          (is (absolute? discovery-link))
          (is (ends-with? discovery-link "/"))))

      (testing "includes a link to discovery"
        (let [discovery-link (hal/get-href resource :ping)]
          (is (absolute? discovery-link))
          (is (ends-with? discovery-link "/ping")))))))
