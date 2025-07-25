(ns estudos-clojure.core)


(defn valor-descontado
  "Retorna o valor com desconto de 10%"
  [valor-bruto]
  (let [desconto (/ 10 100)]
    (* valor-bruto (- 1 desconto))))
