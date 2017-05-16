(ns balance.cmn.pages.home-page
  (:require [balance.libs.react-native :as rn]
            [reagent.core :as r]
            [re-frame.core :refer [subscribe]]))

(def styles {:page             {:margin-top     64
                                :flex-grow      1
                                :flex-direction "row"}
             :task-list        {:flex-grow 1}
             :task-item        {:padding 10}
             :task-caption     {:font-size 18
                                :color "gray"}
             :task-description {:font-size 12}})

(defn render-item [item]
  (r/as-element [task-item (-> item .-item .-key)]))

(defn home-page []
  (fn []
    (let [tasks-keys (subscribe [:get-tasks-keys])]
      [rn/view {:style (:page styles)}
       [rn/flat-list {:data        @tasks-keys
                      :render-item render-item
                      :style       (:task-list styles)}]])))

(defn task-item [task-id]
  (fn []
    (let [task (subscribe [:get-task task-id])
          {:keys [caption description]} @task]
      [rn/view {:style (:task-item styles)}
       [rn/text {:style (:task-caption styles)} caption]
       [rn/text {:style (:task-description styles)} description]])))
