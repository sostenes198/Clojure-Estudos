(ns estudos-clojure.aula-15-defmulti-defmethod
  (:use [clojure pprint]))

(defrecord PacienteParticular [id nome nascimento situacao])
(defrecord PacientePlanoDeSaude [id nome nascimento situacao plano])

(defprotocol Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]))

(defn nao-eh-urgente? [paciente]
  (not= :urgente (:situacao paciente :normal))
  )

(extend-type PacienteParticular
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (and (>= valor 50)
         (nao-eh-urgente? paciente))))

(extend-type PacientePlanoDeSaude
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (let [plano (:plano paciente)]
      (and (not (some #(= % procedimento) plano))
           (nao-eh-urgente? paciente)))))

(let [particular (->PacienteParticular 15 "Soso" "12/09/1995" :urgente)
      plano (->PacientePlanoDeSaude 15 "Soso" "12/09/1995" :urgente [:raio-x :ultrasom])]
  (pprint (deve-assinar-pre-autorizacao? particular :raio-x 500))
  (pprint (deve-assinar-pre-autorizacao? particular :raio-x 40))
  (pprint (deve-assinar-pre-autorizacao? plano :raio-x 40))
  (pprint (deve-assinar-pre-autorizacao? plano :coleta-de-sangue 40))
  )

; não se coloca multi no final, apenas para fins de estudos e comparar as implementações
(defmulti deve-assinar-pre-autorizacao-multi? class)
(defmethod deve-assinar-pre-autorizacao-multi? PacienteParticular
  [paciente]
  (println "Invocando paciente particular")
  true)
(defmethod deve-assinar-pre-autorizacao-multi? PacientePlanoDeSaude
  [paciente]
  (println "Invocando paciente plano de saúde")
  false)

(println "\n")

(let [particular (->PacienteParticular 15 "Soso" "12/09/1995" :urgente)
      plano (->PacientePlanoDeSaude 15 "Soso" "12/09/1995" :urgente [:raio-x :ultrasom])]
  (pprint (deve-assinar-pre-autorizacao-multi? particular))
  (pprint (deve-assinar-pre-autorizacao-multi? plano))
  )

(defn minha-funcao [p]
  (println p)
  (class p))

;(defmulti multi-test minha-funcao)
;(multi-test "Guilherme")
;

(println "\n")

(defn tipo-de-autorizador [pedido]
  (let [paciente (:paciente pedido)
        situacao (:situacao paciente)
        urgencia? (= :urgente situacao)]
    (if urgencia?
      :sempre-autorizado
      (class paciente))))

(defmulti deve-assinar-pre-autorizacao-do-pedido? tipo-de-autorizador)
(defmethod deve-assinar-pre-autorizacao-do-pedido? :sempre-autorizado [pedido]
  false)
(defmethod deve-assinar-pre-autorizacao-do-pedido? PacienteParticular [pedido]
  (>= (:valor pedido 0) 50))
(defmethod deve-assinar-pre-autorizacao-do-pedido? PacientePlanoDeSaude [pedido]
  (not (some #(= % (:procedimento pedido)) (:plano (:paciente pedido)))))

(let [particular (->PacienteParticular 15 "Soso" "12/09/1995" :normal)
      plano (->PacientePlanoDeSaude 15 "Soso" "12/09/1995" :urgente [:raio-x :ultrasom])]
  (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente particular, :valor 100, :procedimento :coleta-de-sangue}))
  (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente plano, :valor 100, :procedimento :coleta-de-sangue}))
  )