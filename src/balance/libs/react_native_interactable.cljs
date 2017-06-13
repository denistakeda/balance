(ns balance.libs.react-native-interactable
  (:require [reagent.core :as r]))

(def RNI (js/require "react-native-interactable"))

(def interactable-view (-> RNI .-View r/adapt-react-class))
