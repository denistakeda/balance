(ns balance.libs.react-router-native
  (:require [reagent.core :as r]))

(def ReactRouterNative (js/require "react-router-native"))

(def native-router (r/adapt-react-class (.-NativeRouter ReactRouterNative)))
(def route (r/adapt-react-class (.-Route ReactRouterNative)))
(def link (r/adapt-react-class (.-Link ReactRouterNative)))
