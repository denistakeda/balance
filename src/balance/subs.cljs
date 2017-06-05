(ns balance.subs
  (:require [re-frame.core :refer [reg-sub]]
            [posh.reagent :refer [q pull]]))

(reg-sub
  :ds
  (fn [db _]
    (println @(:ds db))
    (:ds db)))

(reg-sub
  :task-ids
  :<- [:ds]
  (fn [ds _]
    @(q '[ :find  [?tid ...]
          :where [?tid :task/title]] ds)))

(reg-sub
  :task
  :<- [:ds]
  (fn [ds [_ id]]
    @(pull ds '[:task/title :task/description] id)))
