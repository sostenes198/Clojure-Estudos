(ns estudos-clojure.aula-11-lazy-eager
  (:require [estudos-clojure.db :as l.db]
            [estudos-clojure.logic :as l.logic]))

(defn gastou-bastante?
  [info-do-usuario]
  (> (:preco-total info-do-usuario) 500))


(let [pedidos (l.db/todos-os-pedidos)
      resumo (l.logic/resumo-por-usuario pedidos)]
  (println "keep" (keep gastou-bastante? resumo))
  (println "filter" (filter gastou-bastante? resumo))
  )


(defn filtro1 [x]
  (println "filtro1" x)
  x)

(println (map filtro1 (range 10)))

(defn filtro2 [x]
  (println "filtro" x)
  x)

(println (map filtro2 (map filtro1 (range 10))))

(->> (range 10)
     (map filtro1)
     (map filtro2)
     (println))


; CHUNKS de 32
(->> (range 50)
     (map filtro1)
     (map filtro2)
     (println))