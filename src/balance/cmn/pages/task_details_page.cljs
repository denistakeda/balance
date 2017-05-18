(ns balance.cmn.pages.task-details-page
  (:require [balance.libs.react-native :as rn]
            [reagent.core :as r]
            [re-frame.core :refer [subscribe dispatch]]
            [balance.libs.rnrf :refer [scene-keys]]))

(def page-key (:task-details-page scene-keys))
(def page-title "Task Details")

(def styles {:page        {:margin-top 64
                           :padding 10}
             :title       {:min-height 40
                           :font-size 20
                           :color "gray"}
             :description {:min-height 40
                           :font-size 14}})

(defn- update-task [id path value]
  (dispatch [:update-task id path value]))

(defn page []
  (fn []
    (let [task (subscribe [:get-current-task])
          {:keys [id title description]} @task]
      [rn/view {:style (:page styles)}
       [rn/text-input {:style                  (:title styles)
                       :default-value          title
                       :placeholder            "Title"
                       :multiline              true
                       :auto-correct           false
                       :placeholder-text-color "gray"
                       :on-change-text         #(update-task id [:title] %)}]
       [rn/text-input {:style                  (:description styles)
                       :default-value          description
                       :placeholder            "Task Description"
                       :multiline              true
                       :auto-correct           false
                       :placeholder-text-color "gray"
                       :on-change-text         #(update-task id [:description] %)}]])))

