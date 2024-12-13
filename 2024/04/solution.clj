(ns solution
  (:require [clojure.string :as str]))

(def read-input
  (str/split-lines (slurp "input.txt")))

(defn word-start [])

(def solution-1
  (let [input-data read-input]
    (doseq [line (seq input-data)]
      (doseq [character (seq line)]
        (if (= character \X) () ())))))

solution-1


;; (defn solution-1 []
;;   (let [input-data (read-input "input.txt")]
;;     (str (get-in input-data [1 1]))))


;; (with-open [rdr (reader "input.txt")]
;;   (doseq [line (line-seq rdr)]
;;     (doseq [character (seq line)]
;;       (if (= character \X) (println "x is something") (println "not x")))))