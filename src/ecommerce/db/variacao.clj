(ns ecommerce.db.variacao
  (:use clojure.pprint)
  (:require [datomic.api :as d]
            [ecommerce.model :as model]
            [schema.core :as s]))

(s/defn adiciona-variacao!
        [conn, produto-id :- java.util.UUID, variacao :- s/Str, preco :- BigDecimal]
        (d/transact conn [{:db/id          "variacao-temporaria"
                           :variacao/nome  variacao
                           :variacao/preco preco
                           :variacao/id    (model/uuid)}
                          {:produto/id       produto-id
                           :produto/variacao "variacao-temporaria"}]))