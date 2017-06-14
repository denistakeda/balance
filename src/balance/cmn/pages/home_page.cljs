(ns balance.cmn.pages.home-page
  (:require
   [balance.libs.react-native              :as rn]
   [balance.libs.react-native-interactable :as i]
   [balance.libs.icons                     :refer [icon]]
   [reagent.core                           :as r]
   [re-frame.core                          :refer [subscribe dispatch]]
   [balance.libs.react-router-native       :as rr]))

(def styles {:page               {:margin-top     15
                                  :flex-grow      1}
             :item-container     {:flex-grow        1
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
             :create-new-wrapper {:padding 10}
             :task-list          {:flex-grow 1}
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
