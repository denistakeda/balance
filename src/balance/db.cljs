(ns balance.db
  (:require [clojure.spec.alpha :as s]))

;; spec of app-db
(s/def ::greeting string?)
(s/def ::app-db
  (s/keys :req-un [::greeting]))

;; initial state of app-db
(def app-db
  {:greeting "Hello Clojure in iOS and Android!"
   :app-state {:current-task-id nil}
   :db {:tasks {
                 #uuid "40c344a2-27dc-4731-b6a8-55e92494422d"
                 {:title       "Learn Clojure"
                  :description "Just learn it a little bit"}

                 #uuid "dd7eeeea-546b-4569-ba78-c5b62a5c4789"
                 {:title       "Have a coffe"
                  :description "Just relax"}

                 #uuid "e04bb6b6-03d9-4102-a13e-ad8d142493cb"
                 {:title       "Implement scroll to bottom"
                  :description "Issue #DIF-777"}}}})
