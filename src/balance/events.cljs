(ns balance.events
  (:require
   [re-frame.core      :refer [reg-event-db after reg-fx reg-event-fx reg-cofx inject-cofx]]
   [cljs.core          :refer [random-uuid]]
   [data-frame.core    :refer [reg-event-ds]]
   [balance.db         :refer [app-db conn]]
   [balance.libs.react-router-native :as rr]
   [balance.boot       :refer [save-store-debounced!]]))

;; -- Interceptors ------------------------------------------------------------
;;
;; See https://github.com/Day8/re-frame/blob/master/docs/Interceptors.md
;;

(defn save-database
  "Save database to the local storage"
  [_ _]
  (save-store-debounced!))

(def default-interceptors [(after save-database)])

;; -- Effects ---------------------------------------------------------------

(reg-fx
  :navigate
  (fn [[path-key & params]]
    (rr/navigate-to path-key (or (first params) {}))))

;; -- Helpers --------------------------------------------------------------

(defn filter-nil [m]
  "Remove from map all key-value pairs where value is nil"
  (into {} (filter (comp some? val) m)))

;; -- Handlers --------------------------------------------------------------

(reg-event-db
 :initialize-db
 (fn [_ _]
   app-db))

(reg-event-fx
  :commit-task
  default-interceptors
  (fn [_ [_ task]]
    (let [task (filter-nil task)]
      (if (contains? task :task/title)
        { :navigate [:back] }
        { :transact [task]
          :navigate [:back] }))))
