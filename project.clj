(defproject coffeecounterbot "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://coffeecounterbot.herokuapp.com"
  ;:main coffeecounterbot.web
  :license {:name "FIXME: choose"
            :url "http://example.com/FIXME"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.0"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 ;[ring/ring-devel "1.2.2"]
                 ;[ring-basic-authentication "1.0.5"]
                 [environ "1.0.3"]
                 ;[com.cemerick/drawbridge "0.0.6"]
                 [clj-http "2.0.1"]
                 [org.clojure/data.json "0.2.6"]
                 [telegram "0.2.8"]
                 [org.clojure/core.async "0.2.374"]]
  :min-lein-version "2.0.0"
  :plugins [[environ/environ.lein "0.2.1"]]
  :hooks [environ.leiningen.hooks]
  :uberjar-name "coffeecounterbot-standalone.jar"
  :profiles {:production {:env {:production true}}})
