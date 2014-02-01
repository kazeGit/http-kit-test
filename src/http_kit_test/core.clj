(ns http-kit-test.core
  (:gen-class)
  (:use [org.httpkit.server :only [run-server]]
        [compojure.core :only [defroutes GET POST]]
        [compojure.handler :only [site]])
  (:require [ring.middleware.reload :as reload]
            [compojure.route :as route]
            [clojure.tools.cli :refer [parse-opts]]))

(defroutes all-routes
  (GET "/" [] "handling-page")
  (route/not-found "<p>Page not found.</p>")) ;; all other, return 404

(defn opts-debug? [opts]
  ((comp :debug :options) opts))
(defn opts-port [opts]
  ((comp :port :options) opts))

(def cli-options
  ;; An option with a required argument
  [["-p" "--port PORT" "Port number"
    :default 8080
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
   ;; A boolean option
   ["-d" "--debug" "Enables hot reloading" :default false]])

(defmacro if-without-errors [opts & exprs]
  `(let [errors#  (~opts :errors)
         summary# (~opts :summary)]
    (if-not (nil? errors#)
      (do
        (println "Parsing errors:" errors#)
        (if-not (nil? summary#) (println (str "Summary:\n" summary#))))
      (do ~@exprs))))

(defn -main [& args]
  (let [opts (parse-opts args cli-options)]
    (if-without-errors opts
      (let [in-dev? (opts-debug? opts)
            port (opts-port opts)
            handler (if in-dev?
                      (reload/wrap-reload (site #'all-routes)) ;; only reload when dev
                      (site all-routes))]
        (println "Server is runnin in" (if in-dev? "DEBUG" "PRODUCTION") "mode on port" port)
        (run-server handler {:port port})))))

