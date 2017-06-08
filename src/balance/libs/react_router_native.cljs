(ns balance.libs.react-router-native
  (:require
    [reagent.core              :as r]
    [balance.libs.icons        :refer [icon RNVI]]
    [balance.libs.react-native :as rn]))

(def ReactRouterNative (js/require "react-router-native"))

(def native-router (-> ReactRouterNative .-NativeRouter r/adapt-react-class))
(def route (-> ReactRouterNative .-Route r/adapt-react-class))
(def link (-> ReactRouterNative .-Link r/adapt-react-class))

(defn back-button [props]
  (let [go-back (-> props :history .-goBack)]
    [rn/touchable-opacity { :on-press #(go-back) }
     [icon { :name   "chevron-left"
             :size   25
             :style { :padding-vertical 5 } }]]))
