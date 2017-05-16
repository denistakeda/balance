(ns balance.cmn.routing
  (:require [reagent.core :as r]
            [balance.cmn.icons :as icons]
            [balance.libs.rnrf :as rnrf]
            [balance.cmn.pages.home-page :as home-page]
            [balance.cmn.pages.task-details-page :as task-detais-page]
            [balance.events]
            [balance.subs]))

(defn routing []
  [rnrf/router
   [rnrf/scene {:key "root"}
    [rnrf/scene {:key       home-page/page-key
                 :component (r/reactify-component home-page/page)
                 :title     home-page/page-title
                 :default   true}]
    [rnrf/scene {:key       task-detais-page/page-key
                 :component (r/reactify-component task-detais-page/page)
                 :title     task-detais-page/page-title}]]])
