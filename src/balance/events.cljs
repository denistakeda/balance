(ns balance.events
  (:require
   [re-frame.core :refer [reg-event-db after reg-fx reg-event-fx]]
   [clojure.spec.alpha :as s]
   [balance.db :as db :refer [app-db]]
   [balance.libs.rnrf :as rnrf]
   [balance.boot :refer [save-store-debounced!]]))

;; -- Interceptors ------------------------------------------------------------
;;
;; See https://github.com/Day8/re-frame/blob/master/docs/Interceptors.md
;;
(defn check-and-throw
  "Throw an exception if db doesn't have a valid spec."
  [spec db [event]]
  (when-not (s/valid? spec db)
    (let [explain-data (s/explain-data spec db)]
      (throw (ex-info (str "Spec check after " event " failed: " explain-data) explain-data)))))

(defn save-database
  "Save database to the local storage"
  [db _]
  (save-store-debounced! db))

(def default-interceptors
  (let [check-and-throw-interceptor (after (partial check-and-throw ::db/app-db))
        save-database-interceptor   (after save-database)]
    (if goog.DEBUG
      [check-and-throw-interceptor
       save-database-interceptor]
      [save-database-interceptor])))

;; -- Effects ---------------------------------------------------------------
;;
;; See https://github.com/Day8/re-frame/blob/master/docs/Effects.md
;;
(reg-fx
  :navigate
  (fn [value]
    (apply rnrf/gt-screen! value)))

;; -- Handlers --------------------------------------------------------------

(reg-event-db
 :initialize-db
 default-interceptors
 (fn [_ [_ db]]
   (or db app-db)))

(reg-event-fx
  :open-task-details
  default-interceptors
  (fn [{:keys [db]} [_ task-id]]
    {:db       (assoc db :current-task-id task-id)
     :navigate [:task-details-page task-id]}))
