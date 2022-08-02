(ns ecommerce.aula5
  (:use clojure.pprint)
  (:require [datomic.api :as d]
            [ecommerce.db.config :as db.config]
            [ecommerce.db.produto :as db.produto]
            [ecommerce.db.venda :as db.venda]
            [schema.core :as s]))

(s/set-fn-validation! true)

(db.config/apaga-banco!)
(def conn (db.config/abre-conexao!))
(db.config/cria-schema! conn)
(db.config/cria-dados-de-exemplo! conn)

(def produtos (db.produto/todos (d/db conn)))
(def primeiro (first produtos))
(pprint primeiro)

(def venda1 (db.venda/adiciona! conn (:produto/id primeiro) 3))
(def venda2 (db.venda/adiciona! conn (:produto/id primeiro) 4))
(def venda3 (db.venda/adiciona! conn (:produto/id primeiro) 8))
(pprint venda1)

(pprint @(db.venda/altera-situacao! conn venda1 "preparando"))
(pprint @(db.venda/altera-situacao! conn venda2 "preparando"))
(pprint @(db.venda/altera-situacao! conn venda2 "a caminho"))
(pprint @(db.venda/altera-situacao! conn venda2 "entregue"))

(pprint (db.venda/historico (d/db conn) venda2))

(db.venda/cancela! conn venda1)
(pprint (db.venda/historico (d/db conn) venda1))

(pprint (count (db.venda/todas-nao-canceladas (d/db conn))))
(pprint (count (db.venda/todas-inclusive-canceladas (d/db conn))))
(pprint (count (db.venda/canceladas (d/db conn))))

(pprint (db.venda/historico-geral (d/db conn) #inst "2011-08-02T22:25:39.121-00:00"))
(pprint (db.venda/historico-geral (d/db conn) #inst "2022-08-02T22:57:12.299-00:00"))

;8654844 :venda/id 1 15 true
;8654844 :venda/situacao "nova" 15 true
;8654844 :venda/situacao "nova" 20 false
;8654844 :venda/situacao "cancelada" 20 true
;17
;:venda/id 1




