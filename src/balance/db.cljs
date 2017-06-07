(ns balance.db
  (:require
    [clojure.spec.alpha :as s]
    [datascript.core    :as d]
    [posh.reagent       :refer [posh!]]))

;; initial state of app-db
(def schema {})

(def conn (d/create-conn schema))

(posh! conn)

(def app-db
  { :greeting "Hello Clojure in iOS and Android!" })
