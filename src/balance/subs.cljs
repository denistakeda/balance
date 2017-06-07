(ns balance.subs
  (:require
    [re-frame.core :refer [reg-sub reg-sub-raw]]
    [balance.db    :refer [conn]]
    [posh.reagent  :refer [q pull]]))

(reg-sub-raw
  :task-ids
  (fn [_ _]
    (q '[ :find  [?tid ...]
          :where [?tid :task/title]] conn)))

(reg-sub-raw
  :task-preview
  (fn [_ [_ id]]
    (pull conn '[:task/title :task/description]  id)))

(reg-sub-raw
  :task-details
  (fn [_ [_ id]]
    (pull conn '[*]  id)))
