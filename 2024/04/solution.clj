(ns solution
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]))

(defn valid-lookup? [direction line-idx character-idx]
  (and  (> (+ line-idx (direction 0)) 0) (<= (+ line-idx (direction 0)) 139) ; TODO: un-hardcode these!
        (> (+ character-idx (direction 1)) 0) (<= (+ character-idx (direction 1)) 140))) ; TODO: un-hardcode these!

(defn next-letter? [rdr direction line-idx character-idx next-valid-character]
  (println (+ line-idx (direction 0)))
  (println direction)
  (println (+ character-idx (direction 1)))
  ;; (printf "%d this line?\n" (+ line-idx (direction 0)))
  (and (valid-lookup? direction line-idx character-idx)
       (let [next-line-idx (+ line-idx (direction 0))
             next-character-idx (+ character-idx (direction 1))
             line (nth (line-seq rdr) next-line-idx)
             character (nth (seq line) next-character-idx)]
         (= next-valid-character character))))

(defn xmas? [rdr direction line-idx character-idx current-character]
  (let [xmas "XMAS"
        next-valid-character (get xmas (+ (str/index-of xmas current-character) 1))]
    (or (nil? next-valid-character) (and (next-letter? rdr direction line-idx character-idx next-valid-character) (xmas? rdr direction (+ line-idx (direction 0)) (+ character-idx (direction 1)) next-valid-character)))))

(defn word-explore [rdr line-idx character-idx]
  (let [directions '[[-1 -1]
                     [-1 0]
                     [-1 1]
                     [0 -1]
                     [0 1]
                     [1 -1]
                     [1 0]
                     [1 1]]
        total 0]
    (doseq [direction directions] (if (xmas? rdr direction line-idx character-idx \X) (+ total 1)))
    (printf "total -- %d\n" total)))

(def solution-1
  (with-open [rdr (io/reader "input.txt")] ;; TODO: convert to full 2d- array to avoid buffer io issues
    (doseq [[line-idx line] (map-indexed vector (line-seq rdr))]
      (doseq [[character-idx character] (map-indexed vector (seq line))]
        (if (= character \X) (word-explore rdr line-idx character-idx))))))

solution-1


;; (defn solution-1 []
;;   (let [input-data (read-input "input.txt")]
;;     (str (get-in input-data [1 1]))))


;; (with-open [rdr (reader "input.txt")]
;;   (doseq [line (line-seq rdr)]
;;     (doseq [character (seq line)]
;;       (if (= character \X) (println "x is something") (println "not x")))))