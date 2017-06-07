(ns balance.cmn.pages.task-details-page
  (:require
    [balance.libs.react-native        :as rn]
    [balance.libs.react-router-native :as rr]
    [reagent.core                     :as r]
    [re-frame.core                    :refer [subscribe dispatch]]))

(def path "/task/:taskId")

(defn get-task-path [task-id]
  (str "/task/" task-id))

(def styles {:page        {:margin-top 10
                           :padding 10}
             :title       {:min-height 40
                           :font-size 20
                           :color "gray"}
             :description {:min-height 40
                           :font-size 14}
             :back-button {:padding-vertical 10
                           :color "blue"
                           :font-size 18}})

(defn page [props]
  (let [task-id (-> props :match .-params .-taskId js/parseInt)
        task    (subscribe [:task-details task-id])]
    (fn [props]
      [rn/view { :style (:page styles) }
       [rr/back-button props]
       [rn/text-input { :style                  (:title styles)
                        :default-value          (:task/title @task)
                        :auto-correct           false
                        :multiline              true
                        :placeholder            "Title"
                        :placeholder-text-color "gray"
                        :on-change-text         #(dispatch [:update-task task-id :task/title %]) }]
       [rn/text-input { :style                  (:description styles)
                        :default-value          (:task/description @task)
                        :auto-correct           false
                        :multiline              true
                        :placeholder            "Description"
                        :placeholder-text-color "gray"
                        :on-change-text         #(dispatch [:update-task task-id :task/description %]) }]])))
