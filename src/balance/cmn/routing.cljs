(ns balance.cmn.routing
  (:require [reagent.core :as r]
            [balance.cmn.icons :as icons]
            [balance.libs.rnrf :as rnrf]
            [balance.cmn.pages.home-page :refer [home-page]]
            [balance.events]
            [balance.subs]))

(defn routing []
  [rnrf/router
   [rnrf/scene {:key "root"}
    [rnrf/scene {:key       "home"
                 :component (r/reactify-component home-page)
                 :title     "Home"
                 :default   true}]]])
