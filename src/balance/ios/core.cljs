(ns balance.ios.core
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [dispatch-sync]]
            [balance.libs.react-native :as rn]
            [balance.cmn.routing :refer [routing]]))

(defn app-root []
  [routing])

(defn init []
      (dispatch-sync [:initialize-db])
      (.registerComponent rn/app-registry "Balance" #(r/reactify-component app-root)))
