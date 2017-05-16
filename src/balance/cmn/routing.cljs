(ns balance.cmn.routing
  (:require [re-frame.core :refer [subscribe]]
            [reagent.core :as r]
            [balance.libs.react-native :as rn]
            [balance.cmn.icons :as icons]
            [balance.libs.rnrf :as rnrf]
            [balance.events]
            [balance.subs]))

(defn home-page []
  [rn/view
   [rn/text "Home page"]])

(defn new-task-page []
  [rn/view
   [rn/text "New task page"]])

(defn routing []
  [rnrf/router
   [rnrf/scene {:key "root"}
    [rnrf/scene {:key       "home"
                 :component (r/reactify-component home-page)
                 :title     "Home"}]
    [rnrf/scene {:key       "new-task"
                 :component (r/reactify-component new-task-page)
                 :title     "Create task"}]]])
