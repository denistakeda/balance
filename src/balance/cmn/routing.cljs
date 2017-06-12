(ns balance.cmn.routing
  (:require
    [reagent.core                        :as r]
    [balance.cmn.pages.home-page         :as home-page]
    [balance.cmn.pages.task-details-page :as task-detais-page]
    [balance.libs.react-router-native    :as rr]
    [balance.libs.react-native           :as rn]
    [balance.events]
    [balance.subs]))

(defn routing []
  [rr/router { :history rr/history }
   [rn/view
     [rr/route { :exact     true
                 :path      (:home rr/paths)
                 :component (r/reactify-component home-page/page) }]
     [rr/route { :path      (:task-details rr/paths)
                 :component (r/reactify-component task-detais-page/page) }]]])
