(ns datafy-test.alt
  (:require [clojure.datafy :as d]
            [clojure.core.protocols :as p]))

(defprotocol PName
  (getFirst [this])
  (getLast [this]))
(deftype Name
    [^String first
     ^String last]
  PName
  (getFirst [this] (.-first this))
  (getLast [this] (.-last this)))

(defprotocol PPerson
  (getName [this]))
(deftype Person
    [^Name name]
  PPerson
  (getName [this] (.-name this)))

(def p (Person. (Name. "John" "Dow")))

(extend-protocol
    p/Datafiable
  Name
  (datafy [o]
    (into {} (bean o)))
  Person
  (datafy [o]
    (into {} (bean o))))

(as-> (d/datafy p) <>
  (d/nav <> :name (:name <>))
  (d/datafy <>)
  (d/nav <> :last (:last <>)))
