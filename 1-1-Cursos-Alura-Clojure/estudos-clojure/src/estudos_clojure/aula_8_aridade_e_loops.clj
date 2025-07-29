(ns estudos-clojure.aula-8-aridade-e-loops)

(defn conta
  ([elementos] (conta 0 elementos))
  ([total-ate-agora elementos]
   (if (seq elementos)
     (recur (inc total-ate-agora) (next elementos))
     total-ate-agora)))


(defn conta-com-for
  [elementos]
  (loop [total-ate-agora 0
         elementos-restantes elementos]
    (if (seq elementos-restantes)
      (recur (inc total-ate-agora) (next elementos-restantes))
      total-ate-agora)
    ))

(println "Conta")
(println (conta [1 2 3 4 5 6]))
(println (conta []))

(println "Conta com for")
(println (conta-com-for [1 2 3 4 5 6]))
(println (conta-com-for []))
