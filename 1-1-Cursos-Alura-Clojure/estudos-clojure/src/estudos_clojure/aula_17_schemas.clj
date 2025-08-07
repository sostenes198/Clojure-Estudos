(ns estudos-clojure.aula-17-schemas
  (:use [clojure pprint])
  (:require [schema.core :as s]))

(defn adiciona-paciente
  [pacientes paciente]
  (if-let [id (:id paciente)]
    (assoc pacientes id paciente)
    (throw (ex-info "Paciente não possui id" {:paciente paciente})))
  )

(defn adiciona-visita
  [visitas, paciente, novas-visitas]
  (if (contains? visitas paciente)
    (update visitas paciente concat novas-visitas)
    (assoc visitas paciente novas-visitas))
  )

(defn imprime-relatorio-de-paciente
  [visitas, paciente]
  (println "Visitas do paciente" paciente "são" (get visitas paciente)))

(defn testa-uso-de-pacientes []
  (let [soso {:id 15 :nome "Soso"}
        quel {:id 16 :nome "Quel"}
        paulo {:id 17 :nome "Paulo"}
        pacientes (reduce adiciona-paciente {} [soso, quel, paulo])
        visitas {}
        visitas (adiciona-visita visitas 15 ["01/01/2019"])
        visitas (adiciona-visita visitas 16 ["01/02/2019", "01/01/2020"])
        visitas (adiciona-visita visitas 15 ["01/03/2019"])
        ]
    (pprint pacientes)
    (pprint visitas)
    (imprime-relatorio-de-paciente visitas (:id soso))
    ))

(testa-uso-de-pacientes)

(s/set-fn-validation! true)

(pprint (s/validate Long 15))
;(pprint (s/validate Long "15"))

(s/defn test-simples [x :- Long]
  (println x))

(test-simples 15)
;(test-simples "Soso")

(s/defn imprime-relatorio-de-paciente
  [visitas, paciente :- Long]
  (println "Visitas do paciente" paciente "são" (get visitas paciente)))

(testa-uso-de-pacientes)

(s/defn novo-paciente
  [id :- Long, nome :- s/Str]
  {:id id, :nome nome})

(pprint (novo-paciente 15 "Soso"))