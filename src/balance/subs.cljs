(ns balance.subs
  (:require
    [re-frame.core   :refer [reg-sub reg-sub-raw]]
    [balance.db      :refer [conn]]
    [data-frame.core :refer [reg-query-sub reg-pull-sub]]))

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
