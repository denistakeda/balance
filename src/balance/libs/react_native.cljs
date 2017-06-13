(ns balance.libs.react-native
  (:require [reagent.core :as r]))

(def ReactNative (js/require "react-native"))

(def app-registry (.-AppRegistry ReactNative))
(def text (-> ReactNative .-Text r/adapt-react-class))
(def image (-> ReactNative .-Image r/adapt-react-class))
(def view (-> ReactNative .-View r/adapt-react-class))
(def touchable-highlight (-> ReactNative .-TouchableHighlight r/adapt-react-class))
(def touchable-opacity (-> ReactNative .-TouchableOpacity r/adapt-react-class))
(def text-input (-> ReactNative .-TextInput r/adapt-react-class))
(def animated-view (-> ReactNative .-Animated .-View r/adapt-react-class))
(def animated-value (-> ReactNative .-Animated .-Value))

;; Async storage ------------------------------------------
(def async-storage (.-AsyncStorage ReactNative))

(defn save-to-storage! [key value]
  (.setItem async-storage key value))

(defn get-from-storage [key cb]
  (.then (.getItem async-storage key) cb))

;; FlatList optimizations ----------------------------------
(def ^:private FlatList (-> ReactNative .-FlatList r/adapt-react-class))

(defn- key-extractor [item]
  item)

(defn- optimized-render-item [render-item item]
  (r/as-element (render-item ((js->clj item) "item"))))

(defn flat-list [params]
  [FlatList (merge params { :key-extractor key-extractor
                            :render-item   (partial optimized-render-item (:render-item params))})])
 ;; ---------------------------------------------------------

(defn alert [title]
      (.alert (.-Alert ReactNative) title))
