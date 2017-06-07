(ns balance.libs.react-router-native
  (:require
    [reagent.core              :as r]
    [balance.cmn.icons         :as icons]
    [balance.libs.react-native :as rn]))

(def ReactRouterNative (js/require "react-router-native"))

(def native-router (r/adapt-react-class (.-NativeRouter ReactRouterNative)))
(def route (r/adapt-react-class (.-Route ReactRouterNative)))
(def link (r/adapt-react-class (.-Link ReactRouterNative)))

(defn back-button [props]
  (let [go-back (-> props :history .-goBack)]
    [rn/touchable-opacity { :on-press #(go-back) }
     [rn/image { :source icons/back-img
                 :style  { :width       40
                           :height      40
                           :margin-left -10 }}]]))
