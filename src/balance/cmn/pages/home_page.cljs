(ns balance.cmn.pages.home-page
  (:require
    [balance.libs.react-native           :as rn]
    [reagent.core                        :as r]
    [re-frame.core                       :refer [subscribe dispatch]]
    [balance.libs.react-router-native    :as rr]))

(def styles {:page               {:margin-top     15
                                  :flex-grow      1}
             :create-new-wrapper {:padding 10}
             :task-list          {:flex-grow 1}
             :task-item          {:padding 10}
             :task-title         {:font-size 18
                                  :color "gray"}
             :task-description   {:font-size 12}})

(defn task-item [task-id]
  (let [task (subscribe [:task-preview task-id])]
    (fn [task-id]
      [rr/link { :to (rr/get-url :task-details { :taskId task-id }) }
       [rn/view { :style (:task-item styles) }
        [rn/text { :style (:task-title styles) }
         (:task/title @task)]
        [rn/text { :style (:task-description styles) }
         (:task/description @task)]]])))

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
                       :render-item (fn [item] [task-item item])
                       :style       (:task-list styles) } ]])))
