(ns estudos-clojure.aula-7-recursao-e-recursao-de-calda)


(map println ["daniela" "guilherme" "carlos" "paulo" "lucia" "lucas"])
(println (first ["daniela" "guilherme" "carlos" "paulo" "lucia" "lucas"]))
(println (rest ["daniela" "guilherme" "carlos" "paulo" "lucia" "lucas"]))
(println (rest []))
(println (next ["daniela" "guilherme" "carlos" "paulo" "lucia" "lucas"]))
(println (next ["guilherme" "carlos" "paulo" "lucia" "lucas"]))
(println (next []))
(println (seq []))
(println (seq [1 2 3]))

(println "\n\n\nMEU MAPA")

(defn meu-mapa
  [funcao sequencia]
  (let [primeiro (first sequencia)]
    (if (not (nil? primeiro))
      (do
        (funcao primeiro)
        (recur funcao (rest sequencia))))))


(defn meu-mapa
  [funcao sequencia]
  (let [primeiro (first sequencia)]
    (if (not (nil? primeiro))
      (do (funcao primeiro)
          (recur funcao (rest sequencia))))))

(meu-mapa println ["daniela" false "guilherme" "carlos" "paulo" "lucia" "lucas"])
(meu-mapa println [])
(meu-mapa println nil)
(meu-mapa println (range 100000))
