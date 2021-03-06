(ns example-metosin-service.ping-resource-test
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

  (deftest ping-resource-GET-on-success
    (let [base-url (str (:address configuration) "/ping")
          result (navigator/discover base-url)
          resource (navigator/resource result)]
      (testing "returns status code 200"
        (is (= 200 (navigator/status result))))

      (testing "includes a self link"
        (let [link (hal/get-href resource :self)]
          (is (absolute? link))
          (is (ends-with? link "/ping"))))

      (testing "includes a link to discovery"
        (let [link (hal/get-href resource :discovery)]
          (is (absolute? link))
          (is (ends-with? link "/")))))))
