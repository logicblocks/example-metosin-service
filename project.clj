(def version
  (or (System/getenv "VERSION")
    "0.0.0+LOCAL"))

(defproject example-metosin-service version
  :description "An example Clojure service using Metosin libraries"
  :url "https://github.com/logicblocks/example-metosin-service"

  :license "MIT License"

  :dependencies [[org.clojure/clojure "1.10.3"]
                 [aleph "0.4.7-alpha5"]
                 [b-social/hype "1.0.0"]
                 [camel-snake-kebab "0.4.2"]
                 [danlentz/clj-uuid "0.1.9"]
                 [halboy "5.1.1"]
                 [integrant "0.8.0"]
                 [juxt/crux-core "21.02-1.15.0-beta"]
                 [metosin/reitit "0.5.12"]
                 [metosin/muuntaja "0.6.8"]
                 [metosin/jsonista "0.3.1"]
                 [ring/ring-defaults "0.3.2"]]
  :plugins [[lein-eftest "0.5.9"]
            [lein-ancient "0.6.15"]
            [lein-kibit "0.1.6"]
            [lein-cljfmt "0.6.7"]
            [lein-bikeshed "0.5.1"]
            [jonase/eastwood "0.3.3"]
            [venantius/yagni "0.1.6"]]
  :profiles {:shared    {:dependencies
                         [[ring/ring-mock "0.4.0"]
                          [freeport "1.0.0"]
                          [faker "0.3.2"]]}
             :dev       [:shared]
             :unit      [:shared {:test-paths ^:replace ["test/unit"]}]
             :component [:shared {:test-paths ^:replace ["test/component"]
                                  :eftest     {:multithread? false}}]
             :uberjar   {:aot          :all
                         :uberjar-name "example-metosin-service.jar"}}
  :test-paths ["test/unit" "test/component"]
  :cljfmt {:indents ^:replace {#".*" [[:inner 0]]}}
  :aliases {"test" ["do"
                    ["with-profile" "component" "eftest" ":all"]]})
