(ns balance.cmn.pages.task-details-page
  (:require [balance.libs.react-native :as rn]
            [reagent.core :as r]
            [re-frame.core :refer [subscribe dispatch]]
            [balance.libs.rnrf :refer [scene-keys]]
            [balance.db :refer [entity]]))

(def page-key (:task-details-page scene-keys))
(def page-title "Task Details")

(def styles {:page        {:margin-top 64
                           :padding 10}
             :title       {:min-height 40
                           :font-size 20
                           :color "gray"}
             :description {:min-height 40
                           :font-size 14}})

(defn page []
  (fn []
    (let [task @(subscribe [:current-task])]
      [rn/view { :style (:page styles) }
       [rn/text-input { :style                  (:title styles)
                        :default-value          (:task/title task)
                        :auto-correct           false
                        :multiline              true
                        :placeholder-text-color "gray"
                        :on-change-text         #(dispatch [:update-current-task :title %]) }]
       [rn/text-input { :style                  (:description styles)
                        :default-value          (:task/description task)
                        :auto-correct           false
                        :multiline              true
                        :placeholder-text-color "gray"
                        :on-change-text         #(dispatch [:update-current-task :description %]) }]])))
