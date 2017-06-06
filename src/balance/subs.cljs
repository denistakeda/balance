(ns balance.subs
  (:require [re-frame.core :refer [reg-sub reg-sub-raw]]
            [balance.db :refer [conn]]
            [posh.reagent :refer [q pull]]))

(reg-sub
  :ds
  (fn [db _]
    (:ds db)))

(reg-sub-raw
  :task-ids
  (fn [_ _]
    (q '[ :find  [?tid ...]
          :where [?tid :task/title]] conn)))

(reg-sub-raw
  :task
  (fn [_ [_ id]]
    (pull conn '[:task/title :task/description]  id)))

(reg-sub
  :current-task
  (fn [db _]
    (:current-task db)))
