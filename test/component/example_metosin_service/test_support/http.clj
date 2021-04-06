(ns example-metosin-service.test-support.http
  (:require
    [aleph.http :as http]
    [jsonista.core :as j]
    [camel-snake-kebab.core :as csk]
    [camel-snake-kebab.extras :as cske]))

(defn- decode-body [response]
  (update response :body #(when (<= 200 (:status response) 299)
                            (->>
                              %
                              (j/read-value)
                              (cske/transform-keys csk/->kebab-case-keyword)))))

(defn GET [url]
  (let [response @(http/get url {:throw-exceptions? false})]
    (decode-body response)))
