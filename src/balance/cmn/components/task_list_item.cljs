(ns balance.cmn.components.task-list-item
  (:require
   [balance.libs.react-native              :as rn]
   [balance.libs.react-native-interactable :as i]
   [reagent.core                           :as r]
   [balance.libs.react-router-native       :as rr]
   [balance.libs.icons                     :refer [icon]]
   [re-frame.core                          :refer [subscribe dispatch]]))

(def styles { :item-container     {:flex-grow        1
                                   :justify-content  "center"
                                   :background-color "white"}
             :actions            {:position "absolute"
                                  :right    0
                                  :top      0
                                  :bottom   0
                                  :justify-content "space-between"
                                  :flex-direction "row"}
             :finish-task-button {:padding          10
                                  :background-color "#4caf50"
                                  :justify-content  "center"}
             :close-task-button  {:padding          13
                                  :background-color "red"
                                  :justify-content "center"}
             :task-item          {:padding 10
                                  :background-color "white"}
             :task-title         {:font-size 18
                                  :color "gray"}
             :task-description   {:font-size 12}})


(defn task-item [task-id]
  (let [task (subscribe [:task-details task-id])]
    (fn [task-id]
      [rr/link { :to (rr/get-url :task-details { :taskId task-id }) }
       [rn/view { :style (:task-item styles) }
        [rn/text { :style (:task-title styles) }
         (when (:task/finish-date @task)
           [icon {:name "check"
                  :size 20
                  :color "#4caf50"}])
         (:task/title @task)]
        [rn/text { :style (:task-description styles) }
         (:task/description @task)]]])))

(defn actions [task-id on-press]
  [rn/view {:style (:actions styles)}
   [rn/view {:style (:close-task-button styles)}
    [rn/touchable-opacity {:on-press #(do (dispatch [:task-remove task-id])
                                          (on-press))}
     [icon {:name "times" :size 30 :color "white"}]]]
   [rn/view {:style (:finish-task-button styles)}
    [rn/touchable-opacity {:on-press #(do (dispatch [:task-done task-id])
                                          (on-press))}
     [icon {:name "check" :size 30 :color "white"}]]]])

(defn animated-item [task-id]
  (let [int-ref (r/atom nil)]
    (fn [task-id]
      [rn/view {:style (:item-container styles)}
       [rn/view
        [actions task-id #(.snapTo @int-ref (clj->js {:index "closed"}))]
        [i/interactable-view {:ref             #(reset! int-ref %)
                              :horizontal-only true
                              :snap-points     (clj->js [{:x 0    :id "closed"}
                                                         {:x -100  :id "right-menu"}])}
         [task-item task-id]]]])))
