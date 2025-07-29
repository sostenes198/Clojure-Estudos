(ns estudos-clojure.aula-9-outras-ordenacoes-com-mapa
  (:require [estudos-clojure.db :as l.db]))

(println (l.db/todos-os-pedidos))

(println (group-by :usuario (l.db/todos-os-pedidos)))

(defn minha-funcao-de-agrupamento
  [elemento]
  (println "elemento " elemento)
  (:usuario elemento))

(println (group-by minha-funcao-de-agrupamento (l.db/todos-os-pedidos)))


(println (count (vals (group-by :usuario (l.db/todos-os-pedidos)))))


(println (map count (vals (group-by :usuario (l.db/todos-os-pedidos)))))

; THREAD LAST

(defn total-do-item
  [[item-id detalhes]]
  (* (get detalhes :quantidade 0) (get detalhes :preco-unitario 0)))

(defn total-do-pedido
  [pedido]
  (reduce + (map total-do-item pedido)))

(defn total-dos-pedidos
  [pedidos]
  (->> pedidos
       (map :itens)
       (map total-do-pedido)
       (reduce +)))

(defn conta-total-por-usuario
  [[usuario pedidos]]
  {
   :usuario-id       usuario
   :total-de-pedidos (count pedidos)
   :preto-total      (total-dos-pedidos pedidos)
   })

(->> (l.db/todos-os-pedidos)
     (group-by :usuario)
     (map conta-total-por-usuario)
     println)