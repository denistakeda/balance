(ns balance.db
  (:require [clojure.spec.alpha :as s]
            [datascript.core :as d]
            [posh.reagent :refer [posh!]]))

;; spec of app-db
(s/def ::greeting string?)
(s/def ::app-db
  (s/keys :req-un [::greeting]))

;; initial state of app-db
(def schema {})

(def conn (d/create-conn schema))

(d/transact! conn [ { :db/id -1
                      :task/title "Learn Clojure"
                      :task/description "Just learn it a little bit" }
                    { :db/id -2
                      :task/title "Have a coffe"
                      :task/abc "abc"
                      :task/description "Just relax" }
                    { :db/id -3
                      :task/title "Implement scroll to bottom"
                      :task/description "Issue #DIF-777" } ])

(posh! conn)

(def app-db
  { :greeting "Hello Clojure in iOS and Android!"
    :ds       conn })

(defn entity
  ([pattern id] (d/pull @conn pattern id))
  ([id] (entity '[*] id)))
