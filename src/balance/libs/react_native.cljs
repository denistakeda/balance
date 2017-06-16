(ns balance.libs.react-native
  (:require
   [reagent.core :as r]
   [balance.styles.variables :refer [style-variables]]
   [camel-snake-kebab.core :refer [->camelCase ->kebab-case-keyword]]))

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
;; -- Styles ------------------------------------------------

(defn camel-case-keys
  "Recursively transforms all map keys into camel case."
  [m]
  (if (map? m)
    (into {} (map (fn [[k v]] [(->camelCase k) (camel-case-keys v)]) m))
    m))

(defn kebab-case-keys
  "Transforms all map keys into kebab case."
  [m]
  (if (map? m)
    (into {} (map (fn [[k v]] [(->kebab-case-keyword k) v]) m))
    m))

(defn populate-style-variables [variables m]
  (cond
    (map? m)
    (into {} (map (fn [[k v]] [k (populate-style-variables variables v)]) m))

    (keyword? m)
    (m variables)

    :default
    m))

(defn create-stylesheet [styles]
  (-> ReactNative
      .-StyleSheet
      (.create (clj->js (camel-case-keys styles)))
      js->clj
      kebab-case-keys))

(defn create-stylesheet-with-variables [variables styles]
  (->> styles
       (populate-style-variables variables)
       create-stylesheet))

(def stylesheet (partial create-stylesheet-with-variables style-variables))

;; -- Alert --------------------------------------------------

(defn alert [title]
      (.alert (.-Alert ReactNative) title))
