(ns balance.cmn.pages.home-page
  (:require
    [balance.libs.react-native           :as rn]
    [reagent.core                        :as r]
    [balance.cmn.pages.task-details-page :refer [get-task-path]]
    [re-frame.core                       :refer [subscribe dispatch]]
    [balance.libs.react-router-native    :as rr]))

(def styles {:page             {:margin-top     15
                                :flex-grow      1
                                :flex-direction "row"}
             :task-list        {:flex-grow 1}
             :task-item        {:padding 10}
             :task-title       {:font-size 18
                                :color "gray"}
             :task-description {:font-size 12}})

(def path "/")

(defn page []
  (let [task-ids (subscribe [:task-ids])]
    (fn []
      [rn/view { :style (:page styles) }
       [rn/flat-list { :data        @task-ids
                       :render-item (fn [item] [task-item item])
                       :style       (:task-list styles) } ]])))

(defn task-item [task-id]
  (let [task (subscribe [:task-preview task-id])]
    (fn [task-id]
      [rr/link { :to (get-task-path task-id) }
       [rn/view { :style (:task-item styles) }
        [rn/text { :style (:task-title styles) }
         (:task/title @task)]
        [rn/text { :style (:task-description styles) }
         (:task/description @task)]]])))
