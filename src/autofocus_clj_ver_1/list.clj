(ns autofocus-clj-ver-1.list
  (:require
   ;; [autofocus-clj-ver-1.io :as io]
   [autofocus-clj-ver-1.item :as item]
   [autofocus-clj-ver-1.util :as util]
   [clojure.string :as string])
  )

;; current ;; INTERNAL DOMAIN LOGIC / API

(defn render-list-with-marks [input-list]
  (mapv item/render-item-with-mark input-list))

(defn stringify-list
  "Enables convenient printing of a to-do list
   where multiple lines are used for multiple items.
   Fromerly called `list-to-string`"
  [list-input]
  (if (zero? (count list-input))
    "list is empty"
    (string/join "\n" (render-list-with-marks list-input))))

(defn list-has-items-of-status
  [in-list in-status]
  (some? (some #(item/contains-status? % in-status) in-list)))

  ;; TEMP FUNC to test logc in `is-auto-markable-list?`
(defn list-has-none-of-status
  [in-list in-status]
  (not-any? #(item/contains-status? % in-status) in-list))

(defn is-auto-markable-list?
  "Determines whether a list is ready to be 'auto-marked'"
  [input-list]
  (cond
      ;; may not be empty
    (zero? (count input-list)) false
    (and
       ;; must contain clean items
       ;; TIL: You can check for key value pairs in a list of 
       ;;  hashmaps by using some?, some, a key look up, 
       ;;  and a value comparison
     (list-has-items-of-status input-list :clean)
       ;; must not contain any marked items
     (list-has-none-of-status input-list :marked)) true
    :else false ;; default else
    ))

(defn keep-indexed-status [input-list input-status]
  (keep-indexed
     ;; note: if you were to use maps here, 
     ;;  you could just use `keep`
     ;; item is %2, index is %1
   #(when (item/contains-status? %2 input-status) %1)
   input-list))

  ;; note: see design decision 2
  ;; note: if/when "last-done" is implemented, new logic may need 
  ;;       to be added (first markable starts looking after 
  ;;       "last-done", instead of index 0)
  ;; TODO: create modular function to replace "index-of-first" and "index-of-last"
(defn index-of-first-markable
  "finds the first clean item in the list 
     and returns its index"
  [input-list]
  (first
   (keep-indexed-status input-list :clean)))

(defn index-of-last-marked
  "Finds the marked item closest to the end 
     of the list and returns its index.
     This item is the 'priority' item that
     will be queued up first when entering
     'focus mode'."
  [input-list]
  (last
   (keep-indexed-status input-list :marked)))

(defn index-of-last-clean
  "finds the clean item closest to the end 
     of the list and returns its index"
  [input-list]
  (last
   (keep-indexed-status input-list :clean)))

  ;; question: Do I need/want both `mod-item-status-at-index-in-list`
  ;;           *and* change-status-of-item ? Would it be better to have
  ;;           only one and reuse effectively?
  ;; note: this replaces & supercedes "mark-first-item",
  ;;  "mark-item", "complete-item", and "new-item-status"
(defn mod-item-status-at-index-in-list
  "returns new list with the status of an item
     updated at the given index in list"
  [in-list in-index in-status]
  (assoc-in in-list [in-index :status] in-status))

;; Note: Implementation of 'last-done' data may affect
;; where auto-marking can or cannot occur.
;; TODO: investigate whether last-done trade-offs, need
(defn mark-first-markable
    "Marks the first markable item of a list.
   If no markable items are found, the list is returned as-is.
   I am calling this internally 'auto-marking'."
    [input-list]
    (if (is-auto-markable-list? input-list)
    ;; note: lets tend to be constant time to create & store (variable allocation)
        ;; (println "is auto-markable list...")
      (let [index (index-of-first-markable input-list)]
        (mod-item-status-at-index-in-list input-list index :marked))
        ;; (println "is NOT auto-markable list...")
      input-list ;; if list ISN'T auto-markable
      ))

(defn mark-closest-to-end-marked-item-done
    "Changes the status of the marked item closest to
     the end of the list as done."
    [input-list]
    ;; mark last marked item as done
      (let [index (index-of-last-marked input-list)]
        (mod-item-status-at-index-in-list input-list index :done)))

(defn generate-review-msg
  [input-list current-index]
  (let [last-marked-index (index-of-last-marked input-list)
        last-marked-text (get (get input-list last-marked-index) :text)
        current-text (get (get input-list current-index) :text)]
    (str "Do you want to do '" current-text "' more than '"
         last-marked-text "'?")))

(defn stringify-list-compact
    "Renders a list to a single-line string of only its marks"
    [input-list]
    (string/join 
     " " 
     (mapv #(item/status-to-mark (:status %)) input-list)))

(defn count-items-of-status [input-list input-status]
  (count (keep-indexed-status input-list input-status)))

(defn is-focusable-list?
  [input-list]
  (< 0 (count-items-of-status input-list :marked)))

(defn is-reviewable-list?
  "A list is reviewable if:
   - it has at least one marked item
   - it has at least one clean item below the last marked item
   By this definition, lists of size 0 or 1 are not reviewable,
   and the only reviewable list of size 2 is where the first
   item is marked and the second item is clean."
  [input-list]
  (cond
    (> 2 (count input-list)) false
    (and
     (= 2 (count input-list))
     (= "[o] [ ]" (stringify-list-compact input-list))) true
    (and
     (< 2 (count input-list))
     ;; - 1 or more clean items AND 1 or more marked items exist 
      (< 0 (count-items-of-status input-list :clean))
      (< 0 (count-items-of-status input-list :marked))
      ;; - the index of the last clean item is bigger than
      ;;   the index of the last marked item
      (> (index-of-last-clean input-list)
         (index-of-last-marked input-list))) true
    :else false
    )
  )

;; question: Is this necessary? Could be slots...
;; TODO: use this to set "cursor" for generating questions
(defn get-first-reviewable-index
  "Returns (first current) index at which reviews will start.
   The first reviewable index is always the first clean item
   after the last marked item in a list."
  [input-list]
  (let [list-of-clean-indecies (keep-indexed-status input-list :clean)
        last-marked (index-of-last-marked input-list)]
    (util/get-next-biggest-number list-of-clean-indecies last-marked)))

(defn apply-answers
  "Takes in an auto-marked to-do list and a list of answers.
   If the answer list is empty, the list is returned as is.
   For each answer in answers, clean items in the to-do list
   will either be marked ('y' or 'yes') or ignored and left
   as-is ('n' or 'no'). If an answer of 'q' or 'quit' is
   encountered, the to-do list with it's current modifications
   will be returned, even if there are still more clean items.
   Answers that exceed the count of clean items will be ignored."
  [input-list answers]
  (if (zero? (count answers))
    input-list
    (let [cleans (keep-indexed-status input-list :clean)
          modifications (zipmap cleans answers)
          new-list (vec (map-indexed
            ;; TODO: modify this logic so that way
            ;;       'n' answers lead to no marking
            ;;       and 'q' answers lead to short-circuiting
                         #(if (and (contains? modifications %)
                                   (= "y" (get modifications %)))
                            (item/change-status-of-item (get input-list %) :marked)
                            %2)
                         input-list))]
      new-list)))





;; current EXTERNAL DOMAIN LOGIC / "PUBLIC FACING / EXPOSED API"

(defn add-item-to-list [input-list new-item]
  (conj input-list new-item))

(defn review-list
  "takes the to-do items list to:
   1. auto-marks the first markable index if it can
   2. assesses whether or not the list is reviewable, and
   3. if the list is reviewable, initiates the request to the
       user to give user input on each reviewable item,
       which *may* return as list with more :marked items
       note: optional input may allow for a list of yes/no answers
             to test functionality
   4. if the list is not reviewable, returns back the list as-is"
  [input-list answers]
  (let [;; step 1: auto-mark if possible
        auto-marked-list (mark-first-markable input-list)]
    ;; step 2: assess whether list is reviewable
    (if (is-reviewable-list? auto-marked-list)

        ;; (println "reviewing...")
        (apply-answers auto-marked-list answers) ;; conduct reviews

        ;; (println "not revewing...")
        ;; (println (stringify-list-compact auto-marked-list))
        auto-marked-list ;; do not conduct reviews
        )))

;; TODO: demote this function to the "list" namespace
(defn focus-on-list
  "Changes the status of the marked item closest to
   the end of the list as done. If, after focusing,
   no marked items are left and there are one or more
   clean items remaining, the clean item closest to
   the beginning of the list is auto-marked*. If no
   marked items are found, the list is returned as-is.
   *Note: Implementation of 'last-done' data may affect
   where auto-marking can or cannot occur."
  [input-list]
  (let [can-focus (is-focusable-list? input-list)]
    (if can-focus
      (mark-first-markable
       (mark-closest-to-end-marked-item-done input-list))
      input-list)))

