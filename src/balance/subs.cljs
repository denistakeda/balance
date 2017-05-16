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
  :get-tasks-keys
  :<- [:get-tasks]
  (fn [tasks _]
    (->> tasks
         keys
         (map #(assoc {} :key %)))))

(reg-sub
  :get-task
  :<- [:get-tasks]
  (fn [tasks [_ task-id]]
    (println (str (assoc (tasks task-id) :id task-id)))
    (assoc (tasks task-id) :id task-id)))

