(ns balance.libs.react-native
  (:require [reagent.core :as r]))

(def ReactNative (js/require "react-native"))

(def app-registry (.-AppRegistry ReactNative))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight ReactNative)))
(def touchable-opacity (r/adapt-react-class (.-TouchableOpacity ReactNative)))
(def text-input (r/adapt-react-class (.-TextInput ReactNative)))
(def async-storage (.-AsyncStorage ReactNative))

;; FlatList optimizations ----------------------------------
(def ^:private FlatList (r/adapt-react-class (.-FlatList ReactNative)))

(defn- key-extractor [item]
  item)


(defn render-item [item]
  (r/as-element [task-item ((js->clj item) "item")]))


(defn- optimized-render-item [render-item item]
  (r/as-element (render-item ((js->clj item) "item"))))

(defn flat-list [params]
  [FlatList (merge params { :key-extractor key-extractor
                            :render-item   (partial optimized-render-item (:render-item params))})])
 ;; ---------------------------------------------------------

(defn alert [title]
      (.alert (.-Alert ReactNative) title))

(defn save-to-storage! [key value]
  (.setItem async-storage key value))

(defn get-from-storage [key cb]
  (.then (.getItem async-storage key) cb))

