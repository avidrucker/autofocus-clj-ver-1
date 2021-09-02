
(ns autofocus-clj-ver-1.core
  (:gen-class)
  (:require
   [autofocus-clj-ver-1.io :as io]
   [clojure.string :as string]))

;; domain logic file

(do
  ;; question: What is an effective way to deal with duplicates in a list?
  ;; answer with a question: How will dealing with duplicates be relevant in this program?
  (defn create-new-item-from-text
    [text-input]
    {;;:id "1234567890" ;; TODO: replace hardcoded non-unique id w/ incremental, unique
     :status :clean
     :text text-input
   ;; :is-hidden false
     })

  (defn add-item-to-list [input-list new-item]
    (conj input-list new-item))

  ;; question: Is this best replaced by a hashmap?
  (defn status-to-mark [status]
    (cond
      (= status :clean) "[ ]"
      (= status :marked) "[o]"
      (= status :done) "[x]"
      :default "?"))

  (defn render-item-with-mark [item]
    (str " - " (status-to-mark (get item :status)) " " (get item :text)))

  (defn render-list-with-marks [input-list]
    (mapv render-item-with-mark input-list))

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

  (defn list-has-items-of-status
    [in-list in-status]
    (some? (some #(contains-status? % in-status) in-list)))

  ;; TEMP FUNC to test logc in `is-auto-markable-list?`
  (defn list-has-none-of-status
    [in-list in-status]
    (not-any? #(contains-status? % in-status) in-list))

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
       (list-has-items-of-status input-list :clean)
       ;; must not contain any marked items
       (list-has-none-of-status input-list :marked)) true
      :else false ;; default else
      ))

  ;; note: see design decision 2
  ;; note: if/when "lastDone" is implemented, new logic may need to be added
  ;;       (first markable starts looking after "lastDone", instead of index 0)
  (defn index-of-first-markable
    "finds the first clean item in the list and returns its index"
    [input-list]
    (first
     ;; note: if you were to use maps here, you could just use `keep`
     (keep-indexed ;; item is %2, index is %1
      #(when (contains-status? %2 :clean) %1)
      input-list)))

  (defn index-of-last-marked
    "finds the marked item closest to the end of the list and returns its index"
    [input-list]
    (last
     (keep-indexed
      #(when (contains-status? %2 :marked) %1) input-list)))

  (defn index-of-last-clean
    "finds the clean item closest to the end of the list and returns its index"
    [input-list]
    (last
     (keep-indexed
      #(when (contains-status? %2 :clean) %1) input-list)))

  ;; note: this replaces & supercedes "mark-first-item",
  ;;  "mark-item", "complete-item", and "new-item-status"
  (defn mod-item-status-at-index-in-list
    "returns new list with the status of an item
   updated at the given index in list"
    [in-list in-index in-status]
    (assoc-in in-list [in-index :status] in-status))

  (defn mark-first-markable
    "Marks the first markable item of a list.
   If no markable items are found, the list is returned as-is.
   I am calling this internally 'auto-marking'."
    [input-list]
    (if (is-auto-markable-list? input-list)
    ;; note: lets tend to be constant time to create & store (variable allocation)
      (let [index (index-of-first-markable input-list)]
        (mod-item-status-at-index-in-list input-list index :marked))
      input-list ;; if list ISN'T auto-markable
      ))

  (defn mark-closest-to-end-marked-item-done
    "Changes the status of the marked item closest to
     the end of the list as done. If no marked items
     are found, the list is returned as-is.
     I am calling this internally 'focusing'."
    [input-list]
    (if (list-has-items-of-status input-list :marked)
      ;; mark last marked item as done
      (let [index (index-of-last-marked input-list)]
        (mod-item-status-at-index-in-list input-list index :done))
      ;; else, return list as is
      input-list)))


(defn generate-review-msg
  [input-list current-index]
  (let [last-marked-index (index-of-last-marked input-list)
        last-marked-text (get (get input-list last-marked-index) :text)
        current-text (get (get input-list current-index) :text)]
    (str "Do you want to do '" current-text "' more than '" last-marked-text "'?"))
  )

;; TODO: implement stub
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
  [input-list]
  ;; CODE GOES HERE
  )

(defn stringify-list-compact
    "Renders a list to a single-line string of only its marks"
    [input-list]
    (string/join 
     " " 
     (mapv #(status-to-mark (:status %)) input-list)))

;; TODO: implement stub
(defn count-items-of-status [input-list input-status]
  (count (keep-indexed
   #(when (contains-status? %2 input-status) %1) input-list)))

;; TODO: implement stub
(defn is-reviewable-list?
  "A list is reviewable if:
   - it has at least one marked item
   - it has at least one clean item below the last marked item
   By this definition, lists of size 0 or 1 are not reviewable,
   and the only reviewable list of size 2 is where the first
   item is marked and the second item is clean."
  [input-list]
  ;; CODE GOES HERE
  (cond
    (< 2 (count input-list)) false
    (and
     (= 2 (count input-list))
     (= "[o] [ ]" (stringify-list-compact input-list))) true
    (and
     (> 2 (count input-list))
     ;; - 1 or more clean items AND 1 or more marked items exist 
     (and
      (pos-int? (count-items-of-status input-list :clean))
      (pos-int? (count-items-of-status input-list :marked))
      ;; - the index of the last clean item is bigger than
      ;;   the index of the last marked item
      (> (index-of-last-clean input-list)
         (index-of-last-marked input-list)))
     ) true
    :else false
    )
  )

;; TODO: implement stub
(defn get-first-reviewable-index
  "returns (first current) index at which reviews will start"
  [input-list]
  ;; CODE GOES HERE
  )

(defn -main
  "runs the entire AutoFocus program"
  []
  ;; MAIN PROGRAM GOES HERE
  (io/-main))

;; (-main)