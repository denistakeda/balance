(ns balance.cmn.pages.task-details-page
  (:require
    [balance.libs.react-native        :as rn]
    [balance.libs.react-router-native :as rr]
    [reagent.core                     :as r]
    [re-frame.core                    :refer [subscribe dispatch]]))

(def styles (rn/create-stylesheet
             {:title       {:min-height 40
                            :font-size 20
                            :color "gray"}
              :description {:min-height 40
                            :font-size 14}
              :back-button {:padding-vertical 10
                            :color "blue"
                            :font-size 18}}))

(defn page [props]
  (let [task-id     (-> props :match .-params .-taskId js/parseInt)
        task        (subscribe [:task-details task-id])
        title       (r/atom (:task/title @task))
        description (r/atom (:task/description @task))]
    (fn [props]
      [rr/screen { :history     (:history props)
                   :title       "Task Details"
                   :back-button true
                   :on-back     #(dispatch [:commit-task { :db/id            task-id
                                                           :task/title       @title
                                                           :task/description @description}])}
       [rn/view
         [rn/text-input { :style                  (:title styles)
                          :default-value          @title
                          :auto-correct           false
                          :multiline              true
                          :placeholder            "Title"
                          :placeholder-text-color "gray"
                          :on-change-text         #(reset! title %) }]
         [rn/text-input { :style                  (:description styles)
                          :default-value          @description
                          :auto-correct           false
                          :multiline              true
                          :placeholder            "Description"
                          :placeholder-text-color "gray"
                          :on-change-text         #(reset! description %) }]]])))
