(ns balance.libs.rnrf
  (:require [reagent.core :as r]))

(def RNRF (js/require "react-native-router-flux"))

(def router (r/adapt-react-class (.-Router RNRF)))
(def scene (r/adapt-react-class (.-Scene RNRF)))
(def actions (.-Actions RNRF))

(defn create-actions [ss]
  (.create actions ss))

(def scene-keys {:home-page "home"
                 :task-details-page "task_details"})

(defn gt-screen! [page-id & params]
  (if (= page-id :back)
    (.pop actions)
    (apply (aget actions (page-id scene-keys)) params)))

