(ns balance.cmn.pages.home-page
  (:require [balance.libs.react-native :as rn]
            [reagent.core :as r]
            [re-frame.core :refer [subscribe dispatch]]
            [balance.libs.rnrf :refer [scene-keys]]))

(def styles {:page             {:margin-top     64
                                :flex-grow      1
                                :flex-direction "row"}
             :task-list        {:flex-grow 1}
             :task-item        {:padding 10}
             :task-title       {:font-size 18
                                :color "gray"}
             :task-description {:font-size 12}})

(def page-key (:home-page scene-keys))
(def page-title "Home")

(defn render-item [item]
  (r/as-element [task-item (-> item .-item .-key)]))

(defn page []
  (fn []
    (let [tasks-keys (subscribe [:get-tasks-keys])]
      [rn/view {:style (:page styles)}
       [rn/flat-list {:data        @tasks-keys
                      :render-item render-item
                      :style       (:task-list styles)}]])))

(defn task-item [task-id]
  (fn []
    (let [task (subscribe [:get-task task-id])
          {:keys [title description]} @task]
      [rn/touchable-highlight {:on-press #(dispatch [:open-task-details task-id])}
       [rn/view {:style (:task-item styles)}
        [rn/text {:style (:task-title styles)} title]
        [rn/text {:style (:task-description styles)} description]]])))
