(defproject property-auction "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring/ring-core "1.6.0-beta5"]
                 [ring/ring-jetty-adapter "1.6.0-beta5"]
                 [compojure "1.6.0-beta1"]
                 [enlive "1.1.6"]
                 [hiccup "1.0.4"]
                 [congomongo "0.5.0"]
                 [amalloy/mongo-session "0.0.2"]
                 [lib-noir "0.9.9"]
                 [clj-time "0.12.0"]]
  :main server
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
