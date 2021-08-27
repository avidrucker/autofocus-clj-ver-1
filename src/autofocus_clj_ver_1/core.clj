
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

;; design decision:
;; UPDATE: Wait... nevermind! I can simply use `map-indexed`
;; UPDATE 2: Will use `keep-indexed` instead b/c (for now) I don't need the nils.
;; The "index-of-first-markable" function will be much easier to implement I believe
;; if items store their own index in the list. That said, adding indecies to list
;; items can also create more issues down the line, if they ever need to be updated,
;; for example... Updating list item indecies may be beneficial, for example, in
;; circumstances where the entire list is cleared out, lists are saved in and out of
;; memory, or other "global" list modifications/(de)serializations. I believe I can
;; avoid most of the mutations & place-driven development ("slots") this time, because
;; Clojure makes it harder to mutate, and I have the intention to avoid the "slots".


;; note: if/when "lastDone" is implemented, new logic may need to be added
;;       (first markable starts looking after "lastDone", instead of index 0)
(defn index-of-first-markable
  "finds the first clean item in the list, and marks it"
  [input-list]
  (first
   (keep-indexed ;; item is %2, index is %1
    #(when (contains-status? %2 :clean) %1)
    input-list)))

;; MARKED FOR DELETION
;; "dot item"
;; (defn mark-item
;;   "Changes the status attribute of an item, 
;;    indicating that it is ready to do.
;;    This is also called 'dotting an item'."
;;   [input-item]
;;   (assoc input-item :status :marked))

;; note: this replaces "mark-item" and "complete-item"
(defn new-item-status!
  "Changes the status attribute of an item, 
   indicating that it is either 'marked'
   (also called 'dotting' or 'dotted' in
   the original AutoFocus documentation)
   or 'completed'."
  [input-item new-status]
  (assoc input-item :status new-status))

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

;; TODO: denote items that "change" the program's state, ie. mark-item --> mark-item!
;; temporary function to enable demo2
;; note: this function will be replaced
;;    by `mark-first-markable-item`
(defn mark-first-item [input-list]
  (assoc
   input-list
   0
   (new-item-status! (first input-list) :marked)))

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