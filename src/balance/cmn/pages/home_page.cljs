(ns balance.cmn.pages.home-page
  (:require
   [balance.libs.react-native              :as rn]
   [balance.libs.react-router-native       :as rr]
   [re-frame.core                          :refer [subscribe]]
   [balance.cmn.components.task-list-item  :refer [animated-item]]))

(def styles (rn/create-stylesheet
             {:page               {:margin-top     15
                                   :flex-grow      1}
              :create-new-wrapper {:padding 10}
              :task-list          {:flex-grow 1}}))

(defn new-task-button []
  [rn/view { :style (:create-new-wrapper styles)}
    [rr/link { :to (rr/get-url :task-details { :taskId -1}) }
     [rn/text "Create"]]])

(defn page []
  (let [task-ids (subscribe [:task-ids])]
    (fn []
      [rn/view { :style (:page styles) }
       [new-task-button]
       [rn/flat-list { :data        @task-ids
                       :render-item (fn [item] [animated-item item])
                       :style       (:task-list styles) } ]])))
