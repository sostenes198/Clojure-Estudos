(ns estudos-clojure.aula-4-colecoes)

(def precos [30 700 1000])

(println (precos 0))
; (println (precos 17))
(println (get precos 2))
(println (get precos 17 0))

(println (conj precos 5))

(println precos)

(println (update precos 0 inc))
(println (update precos 0 dec))

(println precos)

(defn soma-1
  [valor]
  (println "Estou somando um em " valor)
  (+ valor 1))

(println (update precos 0 soma-1))


(defn valor-descontado
  "Retorna o valor com desconto de 10%"
  [valor-bruto]
  (let [taxa-desconto (/ 10 100)
        desconto (* valor-bruto taxa-desconto)]
    (if (> valor-bruto 100)
      (- valor-bruto desconto)
      valor-bruto)))


(println (map valor-descontado precos))

(println (range 10))

(println (filter even? (range 10)))

(println (reduce + precos))


(defn minha-soma
  "Somando valores"
  [valor-1 valor-2]
  (println "somando" valor-1 valor-2)
  (+ valor-1 valor-2))

(println (reduce minha-soma 0 precos))

