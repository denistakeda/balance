(ns balance.boot
  (:require
    [balance.libs.react-native :refer [save-to-storage! get-from-storage]]
    [cljs.reader               :as reader]
    [re-frame.core             :refer [dispatch]])
  (:import
    [goog.async Debouncer]))

(def store-name "LifeBalanceAppStorage")

(defn save-store! [store]
  (save-to-storage! store-name (pr-str store)))

(defn load-store! []
  (get-from-storage store-name #(dispatch [:initialize-db (reader/read-string %)])))

(defn debounce [f interval]
  (let [dbnc (Debouncer. f interval)]
    (fn [& args] (.apply (.-fire dbnc) dbnc (to-array args)))))

(def save-store-debounced!
  (debounce save-store! 3000))



