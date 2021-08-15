(ns autofocus-clj-ver-1.core
  (:gen-class))

(def example-item
  {:id "1234567890"
   :status :clean
   :text "Wash the dishes"
   :is-hidden false})

;; ;; pure-ish
;; (defn reset-list
;;   "clears our to-do list, useful for testing purposes"
;;   [input-list]
;;   [])

;; pure
(defn add-item-to-list [input-list new-item]
  (conj input-list new-item))

;; pure
(defn stringify-list
  "enables convenient printing of our to-do list, `list-to-string`"
  [list-input]
  (if (zero? (count list-input))
    "list is empty"
    (pr-str list-input)))

;; TODO: convert to a test
(defn demo1
  "prints out empty list, adds a new item, and then prints again"
  []
  (let [app-state1 {:current-list []}
        app-state2 (assoc app-state1
                          :current-list
                          (add-item-to-list (app-state1 :current-list) example-item))]
    (println "start of demo1")
    (println "before: " (stringify-list (app-state1 :current-list)))
    (println "after: " (stringify-list (app-state2 :current-list)))
    (println "end of demo1")))

(def fruit
  '("apple" "banana" "cherry"))

(defn text-to-todo-item
  "converts text input to new to-do list item"
  [text-input]
  {:id "1234567890" ;; TODO: replace hardcoded non-unique id w/ incremental, unique
   :status :clean
   :text text-input
   :is-hidden false})

;; (defn demo2
;;   "adds three items to list, prints, automarks the first markable item, and then prints again"
;;   []
;;   (let [current-list (atom [])
;;         ;; build fruit to-do items
;;         items-to-add (map text-to-todo-item fruit)]
;;     (println "start of demo2")
;;     (println "before: " 
;;              (stringify-list current-list))
    
;;     ;; add fruit to-do items to list
;;     (map 
;;      #(add-item-to-list @current-list %)
;;      items-to-add)

;;     (println "after: " 
;;              (stringify-list current-list))
;;   ;; (reset-list)
;;     (println "end of demo2")))

(defn -main
  "runs the entire AutoFocus program"
  []
  (demo1)
  (println "------------")
  ;;(demo2)
  )

(-main)

;; questions
;; - [x] Why does the first printout of the to-do list appear to be incorrect (not empty) without a reset? (see first comment in -main)
;;       - answer: https://clojureverse.org/t/how-to-understand-atom-updates-in-real-time/8043