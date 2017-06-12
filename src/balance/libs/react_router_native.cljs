(ns balance.libs.react-router-native
  (:require
    [reagent.core              :as r]
    [balance.libs.icons        :refer [icon RNVI]]
    [balance.libs.react-native :as rn]))

(def ReactRouterNative (js/require "react-router-native"))
(def PathToRegexp (js/require "path-to-regexp"))
(def create-history (-> "history/createMemoryHistory" js/require .-default))

(def router (-> ReactRouterNative .-Router r/adapt-react-class))
(def route (-> ReactRouterNative .-Route r/adapt-react-class))
(def link (-> ReactRouterNative .-Link r/adapt-react-class))


(def paths { :home         "/"
             :task-details "/tasks/:taskId" })

(defn get-url [url-key params]
  ((.compile PathToRegexp (url-key paths)) (clj->js params)))


(def history (create-history))

(defn go-back []
  (.goBack history))

(defn navigate-to [url-key params]
  (if (= url-key :back)
    (go-back)
    (.push history (get-url url-key params))))



;; Top Menu ---------------------------------------------
(def styles { :wrapper          { :margin-top       15
                                  :padding          10 }
              :top-menu         { :flex-direction   "row"
                                  :align-items      "center"}
              :title            { :flex-grow        1
                                  :justify-content  "center"
                                  :align-items      "center"}
              :title-text       { :font-size        18
                                  :font-weight      "bold" }
              :back-button-icon { :padding-vertical 5 }})

(defn screen [{:keys [back-button title on-back]} children]
  [rn/view { :style (:wrapper styles) }
   [rn/view { :style (:top-menu styles) }
    (when back-button
      [rn/touchable-opacity { :on-press #((or on-back go-back)) }
       [icon { :name   "chevron-left"
               :size   25
               :style  (:back-button-icon styles) }]])
    (when title
      [rn/view { :style (:title styles) }
       [rn/text { :style (:title-text styles) } title]])]
   children])
