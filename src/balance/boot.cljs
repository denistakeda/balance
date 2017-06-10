(ns balance.boot
  (:require
    [balance.libs.react-native :refer [save-to-storage! get-from-storage]]
    [cljs.reader               :as reader]
    [balance.db                :refer [conn]]
    [datascript.transit        :as transit]
    [datascript.core           :as d]
    [re-frame.core             :refer [dispatch]])
  (:import
    [goog.async Debouncer]))

(def store-name "LifeBalanceAppStorage")

(defn save-store! []
  (save-to-storage! store-name (transit/write-transit-str @conn)))

(defn reset-database [new-db]
  (d/reset-conn! conn (transit/read-transit-str new-db)))

(defn load-store! []
  (get-from-storage store-name reset-database))

(defn debounce [f interval]
  (let [dbnc (Debouncer. f interval)]
    (fn [& args] (.apply (.-fire dbnc) dbnc (to-array args)))))

(def save-store-debounced!
  (debounce save-store! 800))



