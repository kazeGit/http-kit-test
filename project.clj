(defproject http-kit-test "0.1.0-SNAPSHOT"
  :description "Writing simple example with http-kit, ring and compojure"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main http-kit-test.core
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [javax.servlet/servlet-api "2.5"]
                 [ring "1.2.1"]
                 [compojure "1.1.6"]
                 [http-kit "2.1.16"]
                 [org.clojure/tools.cli "0.3.1"]])
