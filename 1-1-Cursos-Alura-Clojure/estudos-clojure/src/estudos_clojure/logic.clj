(ns estudos-clojure.logic)

(defn total-do-item
  [[_ detalhes]]
  (* (get detalhes :quantidade 0) (get detalhes :preco-unitario 0)))

(defn total-do-pedido
  [pedido]
  (->> pedido
       (map total-do-item)
       (reduce +)))

(defn total-dos-pedidos
  [pedidos]
  (->> pedidos
       (map :itens)
       (map total-do-pedido)
       (reduce +)))

(defn quantia-de-pedidos-e-gasto-total-por-usuario
  [[usuario pedidos]]
  {:usuario-id       usuario
   :total-de-pedidos (count pedidos)
   :preco-total      (total-dos-pedidos pedidos)})

(defn resumo-por-usuario [pedidos]
  (->> pedidos
       (group-by :usuario)
       (map quantia-de-pedidos-e-gasto-total-por-usuario)))


(defn cabe-na-fila?
  [hospital departamento]
  (-> hospital
      (get departamento)
      (count)
      (< 5))
  )

(defn chega-em
  [hospital departamento pessoa]

  (if (cabe-na-fila? hospital departamento)
    (update hospital departamento conj pessoa)
    (throw (ex-info "Fila já está cheia" {:tentando-adicionar pessoa})))
  )

(defn chega-em-pausado
  [hospital departamento pessoa]
  (println "Tentando adicionar a pessoa" pessoa)
  (if (cabe-na-fila? hospital departamento)
    (do
      (Thread/sleep 1000)
      (update hospital departamento conj pessoa)
      (println "Dei o update " pessoa))
    (throw (ex-info "Fila já está cheia" {:tentando-adicionar pessoa})))
  )


(defn atende
  [hospital departamento]
  (update hospital departamento pop)
  )

(defn proxima
  [hospital departamento]
  (-> hospital
      departamento
      peek))

(defn transfere
  [hospital de para]
  (let [pessoa (proxima hospital de)]
    (-> hospital
        (atende de)
        (chega-em para pessoa)))
  )

(defn atende-completo
  [hospital departamento]
  {
   :paciente (update hospital departamento peek)
   :hospital (update hospital departamento pop)
   }
  )

(defn atende-completo-com-jusxt
  [hospital departamento]
  (let [fila (get hospital departamento)
        peek-pop (juxt peek pop)
        [pessoa fila-atualizada] (peek-pop fila)
        hospital-atualizado (update hospital assoc departamento fila-atualizada)]
    {
     :paciente pessoa
     :hospital hospital-atualizado
     })
  )