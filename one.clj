; https://adventofcode.com/2017/day/1

(ns advent.one)

(defn- wrapped-captcha
  [captcha]
  (conj captcha (first captcha)))

(defn- partition'
  [coll n step]
  (partition n step coll))

(defn- pair-matches? [pair]
  (= (first pair) (last pair)))

(defn- matching-pairs
  [pairs]
  (filter pair-matches? pairs))

(defn- values-to-sum-from-matching-pairs
  [matching-pairs]
  (map first matching-pairs))

(defn- sum-captcha-values
  [captcha-values]
  (reduce + 0 captcha-values))

(defn solved-captcha
  [captcha]
  (-> captcha
      (wrapped-captcha)
      (partition' 2 1)
      (matching-pairs)
      (values-to-sum-from-matching-pairs)
      (sum-captcha-values)))

(solved-captcha [1 1 1 1]) ; => 4
(solved-captcha [1 1 2 2]) ; => 3
(solved-captcha [1 2 3 4]) ; => 0
(solved-captcha [9 1 2 1 2 1 2 9]) ; => 9
