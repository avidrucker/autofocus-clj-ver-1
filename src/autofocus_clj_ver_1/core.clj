
(ns autofocus-clj-ver-1.core
  (:gen-class)
  (:require [clojure.string :as string]))

;; domain logic file

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

;; note: see design decision 2
;; note: if/when "lastDone" is implemented, new logic may need to be added
;;       (first markable starts looking after "lastDone", instead of index 0)
(defn index-of-first-markable
  "finds the first clean item in the list, and marks it"
  [input-list]
  (first
   (keep-indexed ;; item is %2, index is %1
    #(when (contains-status? %2 :clean) %1)
    input-list)))

;; note: this replaces "mark-item" and "complete-item"
(defn new-item-status!
  "Changes the status attribute of an item, 
   indicating that it is either 'marked'
   (also called 'dotting' or 'dotted' in
   the original AutoFocus documentation,
   this indicates an item is ready to do)
   or 'completed'."
  [input-item new-status]
  (assoc input-item :status new-status))

;; note: this replaced & superceded "mark-first-item"
(defn mod-item-status-at-index-in-list!
  "returns new list with the status of an item
   updated at the given index in list"
  [in-list in-index in-status]
  (assoc in-list in-index
   (new-item-status! (get in-list in-index) in-status)))

(defn mark-first-markable!
  "Marks the first markable item of a list.
   If no markable items are found, the list is returned as-is.
   I am calling this internally 'auto-marking'."
  [input-list]
  (if (is-auto-markable-list? input-list)
    (let [index (index-of-first-markable input-list)]
      (mod-item-status-at-index-in-list! input-list index :marked))
    input-list ;; if list ISN'T auto-markable
    ))

(defn -main
  "runs the entire AutoFocus program"
  []
  ;; MAIN PROGRAM GOES HERE
  )

;; (-main)