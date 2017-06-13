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

(def save-database-interceptor (after save-database))

;; -- Effects ---------------------------------------------------------------

(reg-fx
  :navigate
  (fn [[path-key & params]]
    (rr/navigate-to path-key (or (first params) {}))))

;; -- Co-effects -----------------------------------------------------------

(reg-cofx
 :now
 (fn [coeffects _]
   (assoc coeffects :now (js.Date.))))

;; -- Helpers --------------------------------------------------------------

(defn filter-nil [m]
  "Remove from map all key-value pairs where value is nil"
  (into {} (filter (comp some? val) m)))

(defn insert-creation-date [task now]
  (if (neg? (:db/id task))
    (assoc task :task/creation-date now)
    task))

(defn extend-task [task now]
  (-> task
       filter-nil
       (assoc :task/last-update-date now)
       (insert-creation-date now)))

;; -- Handlers --------------------------------------------------------------

(reg-event-db
 :initialize-db
 (fn [_ _]
   app-db))

(reg-event-fx
  :commit-task
  [save-database-interceptor
   (inject-cofx :now)]
  (fn [{:keys [now]} [_ task]]
    (let [extended-task (extend-task task now)]
      (if (contains? exteded-task :task/title)
        { :navigate [:back] }
        { :transact [extended-task]
          :navigate [:back] }))))
