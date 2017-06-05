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
    (if-let [task-id @(subscribe [:current-task-id])]
      [task-details (entity task-id)]
      [task-details { :db/id (- (rand-int 10000)) }])))

(defn task-details [task]
  (let [title       (r/atom (:task/title task))
        description (r/atom (:task/description task))]
    [rn/view { :style (:page styles) }
     [rn/text-input { :style                  (:title styles)
                      :default-value          @title
                      :auto-correct           false
                      :multiline              true
                      :placeholder-text-color "gray"
                      :on-change-text         #(reset! title %) }]
     [rn/text-input { :style                  (:description styles)
                      :default-value          @description
                      :auto-correct           false
                      :multiline              true
                      :placeholder-text-color "gray"
                      :on-change-text         #(reset! description %) }]]))
