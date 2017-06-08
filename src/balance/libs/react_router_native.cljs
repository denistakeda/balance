(ns balance.libs.react-router-native
  (:require
    [reagent.core              :as r]
    [balance.libs.icons        :refer [icon RNVI]]
    [balance.libs.react-native :as rn]))

(def ReactRouterNative (js/require "react-router-native"))

(def native-router (-> ReactRouterNative .-NativeRouter r/adapt-react-class))
(def route (-> ReactRouterNative .-Route r/adapt-react-class))
(def link (-> ReactRouterNative .-Link r/adapt-react-class))


(def history (.-history ReactRouterNative))
(def location (.-location ReactRouterNative))



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

(defn screen [{:keys [history back-button title]} children]
  (let [go-back (.-goBack history)]
    [rn/view { :style (:wrapper styles) }
     [rn/view { :style (:top-menu styles) }
      (when back-button
        [rn/touchable-opacity { :on-press #(go-back) }
         [icon { :name   "chevron-left"
                 :size   25
                 :style  (:back-button-icon styles) }]])
      (when title
        [rn/view { :style (:title styles) }
         [rn/text { :style (:title-text styles) } title]])]
     children]))
