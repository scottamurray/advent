; https://adventofcode.com/2017/day/5

(ns advent.five)
(require '[clojure.string :as str])

(def jump-table (->> (slurp "jump-table.txt")
                     (str/split-lines)
                     (map read-string)
                     (vec)))

(defn offset-outside-jump-table?
  "Returns true if the given offset is outside the jump table."
  [offset jump-table]
  (>= offset (count jump-table)))

(defn jump-value-inc
  "Returns a new jump table with the value at the given offset incremented."
  [jump-table offset]
  (let [current-jump-value (nth jump-table offset)]
    (assoc jump-table offset (inc current-jump-value))))

(defn new-offset
  "Returns a new jump table offset given a jump table instruction value."
  [current-offset jump-value]
  (+ current-offset jump-value))

(defn num-jumps-to-escape
  "Recursive function that returns the number of jumps needed to escape
  from the given jump table."
  [jump-table]
  (loop [jump-table jump-table
         offset 0
         moves 0]
    (if (offset-outside-jump-table? offset jump-table)
      moves
      (let [current-jump-value (nth jump-table offset)]
        (recur (jump-value-inc jump-table offset)
               (new-offset offset current-jump-value)
               (inc moves))))))

(num-jumps-to-escape jump-table) ; => 376976
