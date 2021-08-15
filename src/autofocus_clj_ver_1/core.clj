(ns autofocus-clj-ver-1.core
  (:gen-class))

(def example-item
  {:id "1234567890"
   :status :clean
   :text "Wash the dishes"
   :is-hidden false})

(def my-list
  "a mutable vector which will hold our to-do list items"
  (atom []))

(defn reset-list
  "clears our to-do list, useful for testing purposes"
  []
  (reset! my-list []))

(defn add-item-to-list [new-item]
  (swap! my-list conj new-item))

(defn stringify-list
  "enables convenient printing of our to-do list, `list-to-string`"
  []
  (if (zero? (count @my-list))
    "list is empty"
    (pr-str @my-list)))

(defn demo1
  "prints out empty list, adds a new item, and then prints again"
  []
  (println "start of demo1")
  (reset-list) ;; note: atom state is preserved between calls
  (println (stringify-list))
  (add-item-to-list example-item)
  (println (stringify-list))
  (reset-list) ;; clear the to-do list at the end of the demo
  (println "end of demo1")
  )

(def fruit
  '("apple" "banana" "cherry"))

(defn text-to-todo-item
  "takes a text input to create a new to-do list item"
  [text-input]
  {:id "1234567890" ;; TODO: replace hardcoded non-unique id w/ incremental, unique
   :status :clean
   :text text-input
   :is-hidden false})

(defn demo2
  "adds three items to list, prints, automarks the first markable item, and then prints again"
  []
  (println "start of demo2")
  (reset-list)
  (println "before: " (stringify-list))
  (map add-item-to-list
       (map text-to-todo-item fruit))
  (println "after: " (stringify-list))
  ;; (reset-list)
  (println "end of demo2"))

(defn -main
  "runs the entire AutoFocus program"
  []
  ;;(demo1)
  (demo2))

(-main)

;; questions
;; - [x] Why does the first printout of the to-do list appear to be incorrect (not empty) without a reset? (see first comment in -main)
;;       - answer: https://clojureverse.org/t/how-to-understand-atom-updates-in-real-time/8043