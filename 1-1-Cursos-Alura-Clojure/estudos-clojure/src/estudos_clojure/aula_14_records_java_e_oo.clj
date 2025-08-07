(ns estudos-clojure.aula-14-records-java-e-oo
  (:require [estudos-clojure.logic :as h.logic])
  (:use [clojure pprint]))

(defrecord Paciente [id nome nascimento])

(println (->Paciente 15 "Soso" "12/09/1995"))
(pprint (->Paciente 15 "Soso" "12/09/1995"))
(pprint (Paciente. 15 "Soso" "12/09/1995"))
(pprint (map->Paciente {:id 15 :nome "Soso" :nascimento "12/09/1995"}))

(let [soso (->Paciente 15 "Soso" "12/09/1995")]
  (println (:id soso))
  (println (vals soso))
  (println (class soso))
  (println (record? soso))
  (println (.nome soso))
  )

(pprint (= (->Paciente 15 "Guilherme" "18/9/1981") (->Paciente 15 "Guilherme" "18/9/1981")))
(pprint (= (->Paciente 153 "Guilherme" "18/9/1981") (->Paciente 15 "Guilherme" "18/9/1981")))

(defrecord PacienteParticular [id nome nascimento])
(defrecord PacientePlanoDeSaude [id nome nascimento plano])

(println "\n")

(defprotocol Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]))

(extend-type PacienteParticular
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (>= valor 50)))

(extend-type PacientePlanoDeSaude
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (let [plano (:plano paciente)]
      (not (some #(= % procedimento) plano)))))

(let [particular (->PacienteParticular 15 "Soso" "12/09/1995")
      plano (->PacientePlanoDeSaude 15 "Soso" "12/09/1995" [:raio-x :ultrasom])]
  (pprint (deve-assinar-pre-autorizacao? particular :raio-x 500))
  (pprint (deve-assinar-pre-autorizacao? particular :raio-x 40))
  (pprint (deve-assinar-pre-autorizacao? plano :raio-x 40))
  (pprint (deve-assinar-pre-autorizacao? plano :coleta-de-sangue 40))
  )

(println "\n")

(defprotocol Dateable
  (to-ms [this ]))

(extend-type java.lang.Number
  Dateable
  (to-ms [this] this))

(pprint (to-ms 56))

(extend-type java.util.Date
  Dateable
  (to-ms [this]
    (.getTime this)))

(pprint (to-ms (java.util.Date.)))

(extend-type java.util.Calendar
  Dateable
  (to-ms [this]
    (-> this
        (.getTime)
        (to-ms))))

(pprint (to-ms (java.util.GregorianCalendar.)))



(defn carrega-paciente [id]
  (println "Carregando" id)
  (Thread/sleep 1000)
  { :id id, :carregado-em (h.logic/agora)})

;(pprint (carrega-paciente 15))
;(pprint (carrega-paciente 30))

; função pura ;)
(defn- carrega-se-nao-existe
  [cache id carregadora]
  (if (contains? cache id)
    cache
    (let [paciente (carregadora id)]
      (assoc cache id paciente))))

;(pprint (carrega-se-nao-existe {}, 15, carrega-paciente))
;
;(pprint (carrega-se-nao-existe { 15 { :id 15 } }, 15, carrega-paciente))



(defprotocol Carregavel
  (carrega! [this id]))

(defrecord Cache
  [cache carregadora]

  Carregavel
  (carrega! [this id]
    (swap! cache carrega-se-nao-existe id carregadora)
    (get @cache id)))


(def pacientes (->Cache (atom {}), carrega-paciente))
(pprint pacientes)
(carrega! pacientes 15)
(carrega! pacientes 30)
(carrega! pacientes 15)
(pprint pacientes)