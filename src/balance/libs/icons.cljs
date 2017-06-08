(ns balance.libs.icons
  (:require [reagent.core :as r]))

(def RNVI (js/require "react-native-vector-icons/FontAwesome"))

(def icon (-> RNVI .-default r/adapt-react-class))
(def icon-button (-> RNVI .-Button r/adapt-react-class))

