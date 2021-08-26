(ns autofocus-clj-ver-1.core
  (:gen-class)
  (:require [clojure.string :as string]))
;; [clojure.pprint :as p]

;; TODO: denote items that "change" the program's state, ie. mark-item --> mark-item!
;; pure
(defn add-item-to-list [input-list new-item]
  (conj input-list new-item))

;; pure
(defn status-to-mark [status]
  (cond
   (= status :clean) "[ ]"
    (= status :marked) "[o]"
    (= status :completed) "[x]"
    :default "?"))

;; pure
(defn render-item-with-mark [item]
  (str " - " (status-to-mark (:status item)) " " (:text item)))

;; pure
(defn render-list-with-marks [input-list]
  (mapv render-item-with-mark input-list))

;; pure
(defn stringify-list
  "Enables convenient printing of a to-do list
   where multiple lines are used for multiple items.
   Fromerly called `list-to-string`"
  [list-input]
  (if (zero? (count list-input))
    "list is empty"
    (string/join "\n" (render-list-with-marks list-input))))

(defn contains-status?
  "checks to see if an item is of a given status"
  [item-input status]
  (= (item-input :status) status))

;; (defn get-status [item-input]
;;   (item-input :status))

(defn is-auto-markable-list?
  "Determines whether a list is ready to be 'auto-marked'"
  [input-list]
  (cond
   ;; may not be empty
   (zero? (count input-list)) false
    (and
     ;; must contain clean items
     ;; TIL: You can check for key value pairs in a list of hashmaps by using
     ;;      some?, some, a key look up, and a value comparison
     (some? (some #(contains-status? % :clean) input-list))
     ;; must not contain any marked items
     (not-any? #(contains-status? % :marked) input-list)) true
    :else false ;; default else
    ))

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

;; TODO: denote items that "change" the program's state, ie. mark-item --> mark-item!
;; "dot item"
(defn mark-item
  "Changes the status attribute of an item, 
   indicating that it is ready to do.
   This is also called 'dotting an item'."
  [input-item]
  (assoc input-item :status :marked))

;; TODO: denote items that "change" the program's state, ie. mark-item --> mark-item!
;; TODO: uncomment when this function is needed
;; (defn complete-item
;;   [input-item]
;;   (assoc input-item :status :completed))

;; TODO: denote items that "change" the program's state, ie. mark-item --> mark-item!
;; temporary function to enable demo2
;; note: this function will be replaced
;;    by `mark-first-markable-item`
(defn mark-first-item [input-list]
  (assoc
   input-list
   0
   (mark-item (first input-list))))

(defn -main
  "runs the entire AutoFocus program"
  []
  (println "============")
  ;; (demo1)
  (println "------------")
  ;; (demo2)
  (println "============"))

;; (-main)

;; questions
;; - [x] Why does the first printout of the to-do list appear to be incorrect (not empty) without a reset? (see first comment in -main)
;;       - answer: https://clojureverse.org/t/how-to-understand-atom-updates-in-real-time/8043