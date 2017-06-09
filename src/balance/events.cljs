(ns balance.events
  (:require
   [re-frame.core      :refer [reg-event-db after reg-fx reg-event-fx reg-cofx inject-cofx]]
   [clojure.spec.alpha :as s]
   [balance.db         :refer [app-db conn]]
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
  [_ _]
  (save-store-debounced!))

(def default-interceptors [(after save-database)])

;; -- Effects ---------------------------------------------------------------
;;
;; See https://github.com/Day8/re-frame/blob/master/docs/Effects.md
;;

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

;; -- Helpers ---------------------------------------------------------------

(defn reg-event-ds
  ([k interceptors handler]
    (reg-event-fx
      k
      (into [] (concat [(inject-cofx :ds)] interceptors))
      (fn [{:keys [ds]} signal]
        { :transact (handler @ds signal) })))
  ([k handler]
     (reg-event-ds k [] handler)))

;; -- Handlers --------------------------------------------------------------

(reg-event-db
 :initialize-db
 (fn [_ _]
   app-db))

(reg-event-ds
  :update-task
  (fn [_ [_ id path value]]
    [[:db/add id path value]]))

