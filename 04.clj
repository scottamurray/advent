; https://adventofcode.com/2017/day/4

(ns advent.four)
(require '[clojure.string :as str])

(defn passphrase-tokens
  [passphrase]
  (str/split passphrase #"\s"))

(defn token-frequencies
  [tokens]
  (vals (frequencies tokens)))

(defn all-frequencies-one?
  [token-frequencies]
  (every? #{1} token-frequencies))

(defn is-valid-passphrase?
  [passphrase]
  (-> passphrase
      (passphrase-tokens)
      (token-frequencies)
      (all-frequencies-one?)))

(defn num-valid-passphrases
  [passphrases]
  (count (filter is-valid-passphrase? passphrases)))

(def passphrases (str/split-lines (slurp "passphrases.txt")))

(num-valid-passphrases passphrases) ; => 455
