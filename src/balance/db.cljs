(ns balance.db
  (:require
    [clojure.spec.alpha :as s]
    [datascript.core    :as d]
    [data-frame.core    :as df]))

(def schema {})

(def conn (d/create-conn schema))

(df/connect! conn)

(def app-db
  { :greeting "Hello Clojure in iOS and Android!" })
