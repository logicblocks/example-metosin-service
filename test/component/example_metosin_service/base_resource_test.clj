(ns example-metosin-service.base-resource-test
  (:require
    [clojure.test :refer :all]

    [example-metosin-service.test-support.system
     :refer [test-system-configuration
             with-system-lifecycle]]
    [example-metosin-service.test-support.http :as http]))

(let [test-system (atom nil)
      configuration (test-system-configuration)]
  (use-fixtures :once (with-system-lifecycle test-system configuration))

  (deftest base-resource-GET-on-success
    (let [ping-url (str (:address configuration) "/")
          result (http/GET ping-url)]
      (testing "returns status code 200"
        (is (= 200 (:status result))))
      (testing "returns OK"
        (is (= {:status "OK"} (:body result)))))))
