(ns coffeecounterbot.web
  (:require [telegram.client]
            [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [ring.adapter.jetty :as jetty]
            [clojure.data.json :as json]
            [clj-http.client :as client]
            [clojure.core.async
             :as a
             :refer [>! <! >!! <!! go chan buffer close! thread
                     alts! alts!! timeout]]
            [environ.core :refer [env]]))

(defn webhook [upd]
  (let [token (env :token)
        chat-id (-> upd :message :chat :id)]
    (telegram.client/send-message token chat-id ["SPACE"])
    {:status 200}))
  
(defroutes app
           (GET "/" []
                {:status  200
                 :headers {"Content-Type" "text/plain"}
                 :body    "YO!"})
           (POST "/webhook" {body :body}
                 (webhook (json/read-str body)))
           (ANY "*" []
                {:status  404
                 :headers {"Content-Type" "text/plain"}
                 :body    "404"}))

(defn -main [& [port]]
  (client/post (str "https://api.telegram.org/bot" token "/" "setWebhook")
               {:form-params data
                :as :json})
  (let [port (Integer. (or port (env :port) 5000))]
        (jetty/run-jetty #'app {:port port :join? false}))

