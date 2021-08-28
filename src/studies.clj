(ns autofocus-clj-ver-1.studies)

(def x [:a :b :c :d])

(def y {0 :q
        1 :r
        2 :s})

;; TODO: replace "get + assoc" with "update" 
(get x 0)
(get y 0)
(assoc x 0 :z)
(assoc y 0 :t)
(update x 1 str) ;; update = get + assoc
(update y 1 str)
;; note: You can treat vectors as if they are maps and avoid unnecessary looping.

;; interesting note: when the count of key-value pairs goes over 7, the order is not guarenteed (shuffled)
(def map-list {0 {:text "a" :status :marked}
               1 {:text "b" :status :clean}})

;; Avi could use more destructuring practice
;; sequential and associative (for maps, with keys)
;; maps mean "constant time O(1) lookups"
(keep (fn [[k v]] (when (= :clean (get v :status)) k)) map-list)

;; options for creating a "view" of your data (see "Haskell lenses")