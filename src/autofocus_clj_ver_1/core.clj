(ns autofocus-clj-ver-1.core
  (:gen-class))

(def example-item
  {:id "1234567890"
   :status :clean
   :text "Wash the dishes"
   :is-hidden false})

(def my-list (atom []))

(defn reset-list []
  (reset! my-list []))

(defn add-item-to-list [new-item]
  (swap! my-list conj new-item))

(defn stringify-list []
  (if (zero? (count @my-list))
    "list is empty"
    (pr-str @my-list)))

(defn -main
  "I don't do a whole lot ... yet."
  []
  (reset-list) ;; note: atom state is preserved between calls
  (println (stringify-list))
  (add-item-to-list example-item)
  (println (stringify-list))
  (println "Hello, World!"))

(-main)

;; questions
;; - [x] Why does the first printout of the to-do list appear to be incorrect (not empty) without a reset? (see first comment in -main)
;;       - answer: https://clojureverse.org/t/how-to-understand-atom-updates-in-real-time/8043