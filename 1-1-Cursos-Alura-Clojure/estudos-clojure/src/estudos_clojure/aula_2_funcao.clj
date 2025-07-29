(ns estudos-clojure.aula-2-funcao)

(defn valor-descontado-aula-2
  "Retorna o valor com desconto de 10%"
  [valor-bruto]
  (let [taxa-desconto (/ 10 100)
        desconto (* valor-bruto taxa-desconto)]
    (if (> valor-bruto 100)
      (- valor-bruto desconto)
      valor-bruto)))

(println
  (valor-descontado-aula-2 100))