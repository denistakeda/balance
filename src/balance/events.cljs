(ns balance.events
  (:require
   [re-frame.core :refer [reg-event-db after]]
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

(reg-event-db
  :open-task-details
  validate-spec
  (fn [db [_ task-id]]
    (rnrf/gt-task-details-page!)
    (assoc db :current-task-id task-id)))

(reg-event-db
  :update-task
  validate-spec
  (fn [db [_ task-id path value]]
    (assoc-in db (into [:tasks task-id] path) value)))

(reg-event-db
  :create-task
  (fn [db _]
    (rnrf/gt-task-details-page!)
    (let [id (cljs.core/random-uuid)]
      (-> db
          (assoc-in [:tasks id] {})
          (assoc :current-task-id id)))))

