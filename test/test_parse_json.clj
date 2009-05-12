(ns name.choi.joshua.fnparse.test-parse-json
  (:use clojure.contrib.test-is)
  (:require [name.choi.joshua.fnparse.json :as j]))

(defstruct node-s :kind :content)
(defstruct state-s :remainder :column :row)
(def make-node (partial struct node-s))
(def make-state (partial struct state-s))

(deftest number-lit
  (is (= (j/number-lit (make-state "123]" 3 4))
         [(make-node :scalar 123) (make-state (seq "]") 6 4)]))
  (is (= (j/number-lit (make-state "-123]" 3 4))
         [(make-node :scalar -123) (make-state (seq "]") 7 4)]))
  (is (= (j/number-lit (make-state "-123e3]" 3 4))
         [(make-node :scalar -123e3) (make-state (seq "]") 9 4)]))
  (is (= (j/number-lit (make-state "-123.9e3]" 3 4))
         [(make-node :scalar -123.9e3) (make-state (seq "]") 11 4)])))

;(deftest load-stream
;  (is (= (j/load-stream "[1, 2, 3]") [1 2 3])
;      "loading a flat vector containing integers")
;  (is (= (j/load-stream "[\"a\", \"b\\n\", \"\\u1234\"]")
;         ["a" "b\n" "\u1234"])
;      "loading a flat vector containing strings")
;  (is (= (j/load-stream "{\"a\": 1, \"b\\n\": 2, \"\\u1234\": 3)")
;         {"a" 1, "b\n" 2, "\u1234" 3))
;      "loading a flat object containing strings and integers"))

(time (run-tests))
