(ns estudos-clojure.model
  (:require [schema.core :as s]))

(def fila_vazia clojure.lang.PersistentQueue/EMPTY)

(defn novo-hospital []
  {:espera       fila_vazia
   :laboratorio1 fila_vazia
   :laboratorio2 fila_vazia
   :laboratorio3 fila_vazia})

; Uma variacao baseada na palestra a seguir, mas com extend-type e com gregoriancalendar
; From Sean Devlin's talk on protocols at Clojure Conj
(defprotocol Dateable
  (to-ms [this]))

(extend-type java.lang.Number
  Dateable
  (to-ms [this] this))

(extend-type java.util.Date
  Dateable
  (to-ms [this] (.getTime this)))

(extend-type java.util.Calendar
  Dateable
  (to-ms [this] (to-ms (.getTime this))))

(def fila-vazia clojure.lang.PersistentQueue/EMPTY)

(defn novo-hospital []
  {:espera       fila-vazia
   :laboratorio1 fila-vazia
   :laboratorio2 fila-vazia
   :laboratorio3 fila-vazia})

(defn novo-departamento []
  fila-vazia)

(s/def PacienteID s/Str)
(s/def Departamento (s/queue PacienteID))
(s/def Hospital {s/Keyword Departamento})