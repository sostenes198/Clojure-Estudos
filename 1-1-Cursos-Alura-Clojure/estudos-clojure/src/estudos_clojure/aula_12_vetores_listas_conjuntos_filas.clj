(ns estudos-clojure.aula-12-vetores-listas-conjuntos-filas
  (:use [clojure pprint]))

(defn testa-vetor []
  (let [espera [111 222]]
    (println "Testa vetor")
    (println espera)
    (println (conj espera 333))
    (println (conj espera 444))
    (println (pop espera))
    ))
(testa-vetor)

(println "\n")

(defn testa-lista []
  (let [espera '(111 222)]
    (println "Testa lista")
    (println espera)
    (println (conj espera 333))
    (println (conj espera 444))
    (println (pop espera))
    ))
(testa-lista)


(println "\n")

(defn testa-conjunto []
  (let [espera #{111 222}]
    (println "Testa lista")
    (println espera)
    (println (conj espera 333))
    (println (conj espera 444))
    ;(println (pop espera))
    ))
(testa-conjunto)


(println "\n")
(defn testa-fila []
  (let [espera (conj clojure.lang.PersistentQueue/EMPTY "111" "222")]
    (println "Testa fila")
    (println (seq espera))
    (println (seq (conj espera 333)))
    (println (seq (conj espera 444)))
    (println (seq (pop espera)))
    (println (peek espera))
    (pprint espera)
    ))
(testa-fila)