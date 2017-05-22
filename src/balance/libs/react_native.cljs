(ns balance.libs.react-native
  (:require [reagent.core :as r]))

(def ReactNative (js/require "react-native"))

(def app-registry (.-AppRegistry ReactNative))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def flat-list (r/adapt-react-class (.-FlatList ReactNative)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight ReactNative)))
(def text-input (r/adapt-react-class (.-TextInput ReactNative)))
(def async-storage (.-AsyncStorage ReactNative))

(defn alert [title]
      (.alert (.-Alert ReactNative) title))

(defn save-to-storage! [key value]
  (.setItem async-storage key value))

(defn get-from-storage [key cb]
  (.then (.getItem async-storage key) cb))

