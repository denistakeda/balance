(ns balance.events
  (:require
   [re-frame.core      :refer [reg-event-db after reg-fx reg-event-fx reg-cofx inject-cofx]]
   [clojure.spec.alpha :as s]
   [balance.db         :refer [app-db conn]]
   [balance.libs.rnrf  :as rnrf]
   [posh.reagent       :refer [transact! pull q]]
   [balance.boot       :refer [save-store-debounced!]]))

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

(def default-interceptors [(after save-database)])

;; -- Effects ---------------------------------------------------------------
;;
;; See https://github.com/Day8/re-frame/blob/master/docs/Effects.md
;;

(reg-fx
  :navigate
  (fn [value]
    (apply rnrf/gt-screen! value)))

(reg-fx
  :transact
  (fn [datoms]
    (transact! conn datoms)))

;; -- Coeffects --------------------------------------------------------------
;;
;; See https://github.com/Day8/re-frame/blob/master/docs/Coeffects.md
;;

(reg-cofx
  :ds
  (fn [coeffects _]
    (assoc coeffects :ds conn)))

;; -- Handlers --------------------------------------------------------------

(reg-event-db
 :initialize-db
 (fn [_ _]
   app-db))

(reg-event-db
  :update-current-task
  (fn [db [_ field value]]
    (assoc-in db [:current-task field] value)))

(reg-event-fx
  :open-task-details
  [(inject-cofx :ds)]
  (fn [{:keys [db ds]} [_ task-id]]
    (let [task (pull ds '[*] task-id)]
      { :db       (assoc db :current-task @task)
        :navigate [:task-details-page] })))

(reg-event-fx
  :commit-current-task
  (fn [{:keys [db]} _]
    { :transact [(:current-task db)]
      :db       (dissoc db :current-tasks)
      :navigate [:back] }))
