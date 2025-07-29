(ns estudos-clojure.aula-13-mutabilidade-com-atoms-e-refs
  (:use [clojure pprint])
  (:require [estudos-clojure.model :as h.model]
            [estudos-clojure.logic :as h.logic]))

; root binding
(def hospital (h.model/novo-hospital))

(defn simula-um-dia
  []
  (def hospital (h.logic/chega-em hospital :espera "111"))
  (def hospital (h.logic/chega-em hospital :espera "222"))
  (def hospital (h.logic/chega-em hospital :espera "333"))
  (pprint hospital)

  (def hospital (h.logic/chega-em hospital :laboratorio1 "444"))
  (def hospital (h.logic/chega-em hospital :laboratorio3 "555"))
  (pprint hospital)


  (def hospital (h.logic/atende hospital :laboratorio1))
  (def hospital (h.logic/atende hospital :espera))
  (pprint hospital)

  (def hospital (h.logic/chega-em hospital :espera "666"))
  (def hospital (h.logic/chega-em hospital :espera "777"))
  (def hospital (h.logic/chega-em hospital :espera "888"))
  (def hospital (h.logic/chega-em hospital :espera "999"))
  (pprint hospital)
  )

;(simula-um-dia)

(defn chega-sem-ser-malvado!
  [hospital pessoa]
  (swap! hospital h.logic/chega-em :espera pessoa)
  (println "apos inserir " pessoa))

(defn starta-thread-de-chegada
  [hospital pessoa]
  (.start (Thread. (fn [] (chega-sem-ser-malvado! hospital pessoa))))
  )


(defn simula-um-dia-em-paralelo-mapv-partial
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["1111" "2222" "3333" "4444" "5555" "6666"]
        starta (partial starta-thread-de-chegada hospital)]

    (mapv starta pessoas)
    (.start (Thread. (fn [] (Thread/sleep 4000)
                       (pprint hospital)))))

  )

;(simula-um-dia-em-paralelo-mapv-partial)

(defn simula-um-dia-em-paralelo-com-doseq
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas (range 6)]

    (doseq [pessoa pessoas]
      (starta-thread-de-chegada hospital pessoa))
    (.start (Thread. (fn [] (Thread/sleep 4000)
                       (pprint hospital)))))

  )

;(simula-um-dia-em-paralelo-com-doseq)

(defn simula-um-dia-em-paralelo-com-dotimes
  []
  (let [hospital (atom (h.model/novo-hospital))]

    (dotimes [pessoa 6]
      (starta-thread-de-chegada hospital pessoa))
    (.start (Thread. (fn [] (Thread/sleep 4000)
                       (pprint hospital)))))

  )

;(simula-um-dia-em-paralelo-com-dotimes)

(defn testa-atomao
  []
  (let [hospital-silveira (atom {:espera h.model/fila_vazia})]
    (pprint hospital-silveira)
    (pprint @hospital-silveira)

    (assoc @hospital-silveira :laboratorio1 h.model/fila_vazia)
    (pprint @hospital-silveira)

    (swap! hospital-silveira assoc :laboratorio1 h.model/fila_vazia)
    (pprint @hospital-silveira)

    (swap! hospital-silveira update :laboratorio1 conj "1111")
    (pprint @hospital-silveira))
  )

;(testa-atomao)
;
;

(defn chega-em!
  [hospital pessoa]
  (swap! hospital h.logic/chega-em :espera pessoa))

(defn transfere!
  [hospital de para]
  (swap! hospital h.logic/transfere de para))

(defn simula-um-dia
  []
  (let [hospital (atom (h.model/novo-hospital))]
    (chega-em! hospital "Raquel")
    (chega-em! hospital "Soso")
    (chega-em! hospital "João")
    (chega-em! hospital "Maria")
    (transfere! hospital :espera :laboratorio1)
    (transfere! hospital :espera :laboratorio2)
    (transfere! hospital :espera :laboratorio2)
    (transfere! hospital :laboratorio2 :laboratorio3)
    (pprint hospital))
  )

;(simula-um-dia)

(defn cabe-na-fila?
  [fila]
  (-> fila
      count
      (< 5))
  )

(defn chega-em
  [fila pessoa]
  (if (cabe-na-fila? fila)
    (conj fila pessoa)
    (throw (ex-info "Fila já está cheia" {:tentando-adicionar pessoa})))
  )

(defn chega-em-ref-set!
  [hospital pessoa]
  (let [fila (get hospital :espera)]
    (ref-set fila (chega-em @fila pessoa)))
  )

(defn chega-em-alter!
  [hospital pessoa]
  (let [fila (get hospital :espera)]
    (alter fila chega-em pessoa))
  )

(defn simula-um-dia
  []
  (let [hospital {
                  :espera       (ref h.model/fila_vazia)
                  :laboratorio1 (ref h.model/fila_vazia)
                  :laboratorio2 (ref h.model/fila_vazia)
                  :laboratorio3 (ref h.model/fila_vazia)
                  }]
    (dosync (chega-em-alter! hospital "1"))
    (dosync (chega-em-alter! hospital "2"))
    (dosync (chega-em-alter! hospital "3"))
    (dosync (chega-em-alter! hospital "4"))
    (dosync (chega-em-alter! hospital "5"))
    ;(dosync (chega-em-alter! hospital "6"))
    (pprint hospital))
  )

;(simula-um-dia)
;

(defn async-chega-em! [hospital pessoa]
  (future
    (Thread/sleep 1000)
    (dosync
      ;(println "Tentando o código sincronizado " pessoa "\n")
      (chega-em-ref-set! hospital pessoa)))
  )

(defn simula-um-dia-async
  []
  (let [hospital {
                  :espera       (ref h.model/fila_vazia)
                  :laboratorio1 (ref h.model/fila_vazia)
                  :laboratorio2 (ref h.model/fila_vazia)
                  :laboratorio3 (ref h.model/fila_vazia)
                  }
        futures (mapv #(async-chega-em! hospital %) (range 10))]

    (future
      (dotimes [n 4]
        (Thread/sleep 2000)
        (pprint hospital)
        (println futures))))
  )

(simula-um-dia-async)

;(println (future 15))
;(println @(future ((Thread/sleep 1000) 15)))
