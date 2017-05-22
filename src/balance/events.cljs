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
    (rnrf/gt-screen! value)))

;; -- Handlers --------------------------------------------------------------

(reg-event-db
 :initialize-db
 default-interceptors
 (fn [_ [_ db]]
   (or db app-db)))

(reg-event-db
 :set-greeting
 default-interceptors
 (fn [db [_ value]]
   (assoc db :greeting value)))

(reg-event-fx
  :open-task-details
  default-interceptors
  (fn [{:keys [db]} [_ task-id]]
    {:db       (assoc-in db [:app-state :current-task-id] task-id)
     :navigate :task-details-page}))


(reg-event-db
  :update-task
  default-interceptors
  (fn [db [_ task-id path value]]
    (assoc-in db (into [:db :tasks task-id] path) value)))

(reg-event-fx
  :create-task
  default-interceptors
  (fn [{:keys [db]} _]
    {:navigate :task-details-page
     :db       (let [id (cljs.core/random-uuid)]
                 (-> db
                   (assoc-in [:db :tasks id] {})
                   (assoc-in [:app-state :current-task-id] id)))}))

