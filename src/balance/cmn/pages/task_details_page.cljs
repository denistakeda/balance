(ns balance.cmn.pages.task-details-page
  (:require [balance.libs.react-native :as rn]
            [reagent.core :as r]
            [re-frame.core :refer [subscribe]]
            [balance.libs.rnrf :refer [scene-keys]]))

(def page-key (:task-details-page scene-keys))
(def page-title "Task Details")

;;(defn page []
;;  [rn/view {:style {:margin-top 64}}
;;   [rn/text (str "I'm a task details page")]])

(defn page []
  (fn []
    (let [task (subscribe [:get-current-task])]
      [rn/view {:style {:margin-top 64}}
       [rn/text (str @task)]])))

