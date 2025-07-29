(ns estudos-clojure.aula-3-boolean)

(defn aplica-desconto?
  [valor-bruto]
  (if (> valor-bruto 100)
    true
    false))


(defn aplica-desconto-when
  [valor-bruto]
  (when (> valor-bruto 100)
    true))

(defn executa-calculo
  [aplica? valor-bruto desconto]
  (if aplica?
    (- valor-bruto desconto)
    valor-bruto))

(defn valor-descontado-aula-3
  "Retorna o valor"
  [aplica? valor-bruto desconto]
  (let [taxa-desconto (/ desconto 100)
        desconto (* valor-bruto taxa-desconto)]
    (executa-calculo aplica? valor-bruto desconto)))


(println (valor-descontado-aula-3 false 500 10))


