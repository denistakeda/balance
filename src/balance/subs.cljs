(ns balance.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
  :get-greeting
  (fn [db _]
    (:greeting db)))

(reg-sub
  :get-tasks
  (fn [db _]
    (:tasks db)))

(reg-sub
  :get-current-task-id
  (fn [db _]
    (:current-task-id db)))

(reg-sub
  :get-tasks-keys
  :<- [:get-tasks]
  (fn [tasks _]
    (->> tasks
         keys
         (map #(assoc {} :key %)))))

(reg-sub
  :get-current-task
  :<- [:get-current-task-id]
  :<- [:get-tasks]
  (fn [[current-task-id tasks] _]
    (-> tasks
        (get current-task-id)
        (assoc :id current-task-id))))

(reg-sub
  :get-task
  :<- [:get-tasks]
  (fn [tasks [_ task-id]]
    (assoc (tasks task-id) :id task-id)))

