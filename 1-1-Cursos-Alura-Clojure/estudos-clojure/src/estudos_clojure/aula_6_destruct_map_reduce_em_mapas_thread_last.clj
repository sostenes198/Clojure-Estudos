(ns estudos-clojure.aula-6-destruct-map-reduce-em-mapas-thread-last)

(def pedido {
             :mochila  {:quantidade 2, :preco 80},
             :camiseta {:quantidade 3, :preco 40}
             })

(defn imprime-e-15
  [valor]
  (println "valor " valor (class valor))
  15
  )

(println (map imprime-e-15 pedido))

(defn imprime-e-15
  [[chave valor]]
  (println chave "e " valor (class valor))
  valor
  )

(println (map imprime-e-15 pedido))

(defn preco-dos-produto
  [[_ valor]]
  (* (:quantidade valor) (:preco valor))
  )

(println (map preco-dos-produto pedido))
(println (reduce + (map preco-dos-produto pedido)))

(defn total-do-pedido
  [pedido]
  (reduce + (map preco-dos-produto pedido)))

(println (total-do-pedido pedido))

; THREAD LAST

(defn preco-total-do-produto
  [produto]
  (* (:quantidade produto) (:preco produto)))


(defn total-do-pedido
  [pedido]
  (->> pedido
       vals
       (map preco-total-do-produto,,,)
       (reduce +,,,))
  )

(println (total-do-pedido pedido))


(def pedido {
             :mochila  {:quantidade 2, :preco 80},
             :camiseta {:quantidade 3, :preco 40}
             :chaveiro {:quantidade 1}
             })

(defn gratuito?
  [item]
  (<= (get item :preco 0) 0))

(println (filter gratuito? pedido))


(println (filter (fn [[_ item]] (gratuito? item)) pedido))
(println (filter #(gratuito? (second %)) pedido))

(defn pago?
  [item]
  (not (gratuito? item)))

(println (pago? {:preco 50}))
(println (pago? {:preco 0}))


(def pago? (comp not gratuito?))

(println ((comp not gratuito?) {:preco 50}))
(println (pago? {:preco 0}))
(println (pago? {:preco 50}))