(defproject example-metosin-service "0.1.0-SNAPSHOT"
  :description "An example Clojure service using Metosin libraries"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [aleph "0.4.7-alpha5"]
                 [b-social/hype "1.0.0"]
                 [danlentz/clj-uuid "0.1.9"]
                 [camel-snake-kebab "0.4.2"]
                 [halboy "5.1.1"]
                 [metosin/reitit "0.5.12"]
                 [metosin/muuntaja "0.6.8"]
                 [metosin/jsonista "0.3.1"]
                 [ring/ring-defaults "0.3.2"]]
  :plugins [[jonase/eastwood "0.4.0"]
            [lein-eftest "0.5.9"]
            [lein-cljfmt "0.7.0"]]
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
