(ns balance.cmn.pages.home-page
  (:require [balance.libs.react-native :as rn]
            [reagent.core :as r]
            [re-frame.core :refer [subscribe]]))

(def styles {:page      {:margin-top     64
                         :flex-grow      1
                         :flex-direction "row"}
             :task-list {:flex-grow 1}})

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
    (let [task (subscribe [:get-task task-id])]
      [rn/view
       [rn/text (str @task)]])))
