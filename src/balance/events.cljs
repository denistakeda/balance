(ns balance.events
  (:require
   [re-frame.core      :refer [reg-event-db after reg-fx reg-event-fx reg-cofx inject-cofx]]
   [clojure.spec.alpha :as s]
   [data-frame.core    :refer [reg-event-ds]]
   [balance.db         :refer [app-db conn]]
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


;; -- Handlers --------------------------------------------------------------

(reg-event-db
 :initialize-db
 (fn [_ _]
   app-db))

(reg-event-ds
  :update-task
  default-interceptors
  (fn [_ [_ id path value]]
    [[:db/add id path value]]))

