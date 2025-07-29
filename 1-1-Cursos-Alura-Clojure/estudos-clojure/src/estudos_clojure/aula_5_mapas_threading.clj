(ns estudos-clojure.aula-5-mapas-threading)

(def estoque {"Mochila" 10, "Camiseta" 5})
(println estoque)

(println "Temos" (count estoque))

(println "Chaves são: " (keys estoque))
(println "Valores são: " (vals estoque))

; keyword
; #mochila

(def estoque {
              :mochila  10,
              :camiseta 5
              })

(println (assoc estoque :cadeira 3))
(println (assoc estoque :mochila 1))

(println (update estoque :mochila inc))

(println (update estoque :mochila #(- % 3)))


(def pedido {
             :mochila  {:quantidade 2, :preco 80},
             :camiseta {:quantidade 3, :preco 40}
             })

(println "\n\n\n")
(println pedido)

(def pedido (assoc pedido :chaveiro {:quantidade 3, :preco 55}))

(println pedido)

(println (pedido :mochila))
(println (pedido :cadeira))
(println (get pedido :mochila))
(println (get pedido :cadeira))
(println (get pedido :cadeira {}))
(println (:mochila pedido))
(println (:cadeira pedido {}))

(println (:quantidade (:mochila pedido)))
(println (update-in pedido [:mochila :quantidade] inc))

; THREADING

(-> pedido
    :mochila
    :quantidade
    println)