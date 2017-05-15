(ns balance.cmn.routing
  (:require [re-frame.core :refer [subscribe]]
            [reagent.core :as r]
            [balance.libs.react-native :as rn]
            [balance.cmn.icons :as icons]
            [balance.events]
            [balance.subs]))

(defn routing []
  (let [greeting (subscribe [:get-greeting])]
    (fn []
      [rn/view {:style {:flex-direction "column" :margin 40 :align-items "center"}}
       [rn/text {:style {:font-size 30 :font-weight "100" :margin-bottom 20 :text-align "center"}} @greeting]
       [rn/image {:source icons/logo-img
               :style  {:width 80 :height 80 :margin-bottom 30}}]
       [rn/touchable-highlight {:style {:background-color "#999" :padding 10 :border-radius 5}
                             :on-press #(rn/alert "HELLO!")}
        [rn/text {:style {:color "white" :text-align "center" :font-weight "bold"}} "press me"]]])))
