(ns balance.ios.core
  (:require [reagent.core :as r :refer [atom]]
            [balance.boot :refer [load-store!]]
            [re-frame.core :refer [dispatch-sync]]
            [balance.libs.react-native :as rn]
            [balance.cmn.routing :refer [routing]]))

(defn app-root []
  [routing])

(defn init []
      (dispatch-sync [:initialize-db])
      (load-store!)
      (.registerComponent rn/app-registry "Balance" #(r/reactify-component app-root)))
