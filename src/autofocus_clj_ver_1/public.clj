(ns autofocus-clj-ver-1.public
  (:require [autofocus-clj-ver-1.core :as af]))

;; EXTERNAL DOMAIN LOGIC / "PUBLIC FACING / EXPOSED API"

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
        auto-marked-list (af/mark-first-markable input-list)]
    ;; step 2: assess whether list is reviewable
    (if (af/is-reviewable-list? auto-marked-list)
      (do
        ;; TODO: remove println debugging
        ;; (println "reviewing...")
        (af/apply-answers auto-marked-list answers)) ;; conduct reviews
      (do
        ;; TODO: remove println debugging
        ;; (println "not revewing...")
        (println (af/stringify-list-compact auto-marked-list))
        auto-marked-list ;; do not conduct reviews
        ))))

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
  (let [can-focus (af/is-focusable-list? input-list)]
    (if can-focus
      (af/mark-first-markable
       (af/mark-closest-to-end-marked-item-done input-list))
      input-list)))

;; TODO: implement this stub
;; TODO: promote this function to the "app" namespace
(defn focus-on-app
  "1. Takes in the entire app state containing:
   - The list itself
   - The index of the last-done item
   2. Determines if the list is focusable
   - If yes: It updates the last-done and completes the marked item closest to the end of the list
   - If no: It returns the app-state as-is
   Note: last-done is 'the most recently completed item index'"
  [{:keys [list last-done]} input-app]
  (println "The list is: " (af/stringify-list-compact list))
  (println "Last done is: " last-done)
  (println "The type of 'input-app' is " (type input-app))
  )

