(ns coffeecounterbot.web
  (:require [telegram.client]
            [environ.core :refer [env]])
  )

(defn -main []
  (let [token (env :token)]
    (while true
      (let [updates ((telegram.client/get-updates token) :result)]
        (dorun
          (map #((let [upd % chat-id (-> upd :message :chat :id)]
                   (telegram.client/get-updates token (inc (upd :update_id)))
                   (telegram.client/send-message token chat-id ["SPACE"])))
               updates))))
    (Thread/sleep 300)))