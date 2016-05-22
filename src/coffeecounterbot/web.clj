(ns coffeecounterbot.web
  (:require [telegram.client]
            [environ.core :refer [env]]))

(defn process-upd [upd]
  (let [token (env :token)
        chat-id (-> upd :message :chat :id)]
    (telegram.client/get-updates token (inc (upd :update_id)))
    (telegram.client/send-message token chat-id ["SPACE"])))

(defn -main []
  (let [token (env :token)]
    (while true
      (let [updates ((telegram.client/get-updates token) :result)]
        (dorun
          (map #(process-upd %)
               updates)))
      (Thread/sleep 3000))))
