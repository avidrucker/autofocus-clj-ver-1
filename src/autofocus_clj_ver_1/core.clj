(ns autofocus-clj-ver-1.core
  (:gen-class)
  (:require [clojure.pprint :as p]))

(def example-item
  {:id "1234567890"
   :status :clean
   :text "Wash the dishes"
   :is-hidden false})

;; pure
(defn add-item-to-list [input-list new-item]
  (conj input-list new-item))

;; pure
(defn stringify-list
  "enables convenient printing of our to-do list, `list-to-string`"
  [list-input]
  (if (zero? (count list-input))
    "list is empty"
    list-input))

;; TODO: convert to a test
(defn demo1
  "prints out empty list, adds a new item, and then prints again"
  []
  (let [app-state1 {:current-list []}
        app-state2 (assoc app-state1
                          :current-list
                          (add-item-to-list
                           (app-state1 :current-list)
                           example-item))]

    (binding [p/*print-right-margin* 30]
      (println "start of demo1")
      (println "before:")
      (p/pprint (stringify-list (app-state1 :current-list)))
      (println "after:")
      (p/pprint (stringify-list (app-state2 :current-list)))
      (println "end of demo1"))))

(def fruit
  '("apple" "banana" "cherry"))

(defn text-to-todo-item
  "converts text input to new to-do list item"
  [text-input]
  {:id "1234567890" ;; TODO: replace hardcoded non-unique id w/ incremental, unique
   :status :clean
   :text text-input
   :is-hidden false})

;; ;; TODO: implement stub
;; (defn is-auto-markable-list?
;;   "Determines whether a list is ready to be 'auto-marked'"
;;   [input-list]
;;   (condp
;;    (zero? (count input-list)) true
;;     (and
;;      (some :clean input-list)
;;      (not-any? :marked input-list)) true
;;     false ;; default else
;;     ))

;; ;; TODO: implement stub
;; (defn index-of-first-markable
;;   [input-list]
;;   0)

;; ;; TODO: implement stub
;; (defn mark-first-markable!
;;   "Marks the first markable item of a list.
;;    If no markable items are found, the list is returned as-is.
;;    I am calling this internally 'auto-marking'."
;;   [input-list]
;;   input-list)

;; "dot item"
(defn mark-item
  "Changes the status attribute of an item, 
   indicating that it is ready to do.
   This is also called 'dotting an item'."
  [input-item]
  (assoc input-item :status :marked))

;; temporary function to enable demo2
;; note: this function will be replaced
;;    by `mark-first-markable-item`
(defn mark-first-item [input-list]
  (assoc 
   input-list 
   0 
   (mark-item (first input-list))))

;; TODO: Convert to a test
;; TODO: implement automark of first markable item
(defn demo2
  "adds three items to list, prints, 
   automarks the first markable item, 
   and then prints again"
  []
  (let [app-state1 [] ;; {:current-list []}
        ;; Build fruit to-do items
        items-to-add (map text-to-todo-item fruit)
        ;; Note: Mapping here converts vector to list
        ;;       ... which makes vector/sequence only
        ;;       functions not behave (at all or as expected)
        ;; question: Is there a cleaner way to map over
        ;;     a data source (in this case the fruit list)
        ;;     without needing to flatten and vec back to
        ;;     the desired shape? (flat vector of hashmaps)  
        ;; Add fruit to-do items to to-do list
        app-state2 (vec (flatten (map
                            #(add-item-to-list 
                              app-state1 %)
                            items-to-add)))
        app-state3 (mark-first-item app-state2)
        ]

    (binding [p/*print-right-margin* 30]
      (println "start of demo2")
      (println "before (1):")
      (p/pprint (stringify-list app-state1))
      (println "after (2):")
      (p/pprint (stringify-list app-state2))
      (println "final (3):")
      (p/pprint (stringify-list app-state3))
      (println "end of demo2"))))

(defn -main
  "runs the entire AutoFocus program"
  []
  (println "============")
  (demo1)
  (println "------------")
  (demo2)
  (println "============"))

(-main)

;; questions
;; - [x] Why does the first printout of the to-do list appear to be incorrect (not empty) without a reset? (see first comment in -main)
;;       - answer: https://clojureverse.org/t/how-to-understand-atom-updates-in-real-time/8043