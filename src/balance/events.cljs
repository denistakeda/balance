(ns balance.events
  (:require
   [re-frame.core :refer [reg-event-db after reg-fx reg-event-fx]]
   [clojure.spec.alpha :as s]
   [balance.db :as db :refer [app-db]]
   [balance.libs.rnrf :as rnrf]))

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

(def validate-spec
  (if goog.DEBUG
    (after (partial check-and-throw ::db/app-db))
    []))

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
 validate-spec
 (fn [_ _]
   app-db))

(reg-event-db
 :set-greeting
 validate-spec
 (fn [db [_ value]]
   (assoc db :greeting value)))

(reg-event-fx
  :open-task-details
  validate-spec
  (fn [{:keys [db]} [_ task-id]]
    {:db       (assoc-in db [:app-state :current-task-id] task-id)
     :navigate :task-details-page}))


(reg-event-db
  :update-task
  validate-spec
  (fn [db [_ task-id path value]]
    (assoc-in db (into [:db :tasks task-id] path) value)))

(reg-event-fx
  :create-task
  validate-spec
  (fn [{:keys [db]} _]
    {:navigate :task-details-page
     :db       (let [id (cljs.core/random-uuid)]
                 (-> db
                   (assoc-in [:db :tasks id] {})
                   (assoc-in [:app-state :current-task-id] id)))}))

