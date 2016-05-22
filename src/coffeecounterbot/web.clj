(ns coffeecounterbot.web
  (:require [telegram.client]
            [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [ring.adapter.jetty :as jetty]
            [clojure.core.async
             :as a
             :refer [>! <! >!! <!! go chan buffer close! thread
                     alts! alts!! timeout]]
            [environ.core :refer [env]]))

(defn process-upd [upd]
  (let [token (env :token)
        chat-id (-> upd :message :chat :id)]
    (telegram.client/get-updates token (inc (upd :update_id)))
    (telegram.client/send-message token chat-id ["SPACE"])))

(defroutes app
           (ANY "/repl" {:as req}
                (drawbridge req))
           (GET "/" []
                {:status  200
                 :headers {"Content-Type" "text/plain"}
                 :body    (pr-str ["Hello" :from 'Heroku])})
           (ANY "*" []
                {:status  404
                 :headers {"Content-Type" "text/plain"}
                 :body    (pr-str ["Not found " :from 'Heroku])}))

(defn -main [& [port]]
  (go (let [port (Integer. (or port (env :port) 5000))]
        (jetty/run-jetty (wrap-app #'app) {:port port :join? false})) )
  (go (let [token (env :token)]
        (while true
          (let [updates ((telegram.client/get-updates token) :result)]
            (dorun
              (map #(process-upd %)
                   updates)))
          (Thread/sleep 3000)))))


