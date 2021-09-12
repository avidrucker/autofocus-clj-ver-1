(ns autofocus-clj-ver-1.util)

;; for a list of numbers
(defn get-next-biggest-number
  "For a list of numbers XS and a given number Y,
   returns the next largest number after Y from XS
   Eg: {xs: (1 3 5)} and {y: 2} ==> 3"
  [xs y]
  (first (filter #(< y %) xs)))

;; (get-next-biggest-number '(1 3 5) 2) ;; => 3
;; (get-next-biggest-number '(5 7 9 11 12) 10) ;; => 11