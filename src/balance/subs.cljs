(ns balance.subs
  (:require
    [re-frame.core :refer [reg-sub reg-sub-raw]]
    [balance.db    :refer [conn]]
    [posh.reagent  :refer [q pull]]))

;; -- Helpers ---------------------------------------

(defn reg-query-sub [sub-name query]
  (reg-sub-raw
    sub-name
    (fn [_ [_ & params]]
      (let [pre-q (partial q query conn)]
        (apply pre-q params)))))

(defn reg-pull-sub [sub-name pattern]
  (reg-sub-raw
    sub-name
    (fn [_ [_ id]]
      (pull conn pattern id))))

;; -- Subscriptions ---------------------------------

(reg-query-sub
  :task-ids
  '[ :find  [?tid ...]
     :where [?tid :task/title]])

(reg-pull-sub
  :task-preview
  '[:task/title :task/description])

(reg-pull-sub
  :task-details
  '[*])
