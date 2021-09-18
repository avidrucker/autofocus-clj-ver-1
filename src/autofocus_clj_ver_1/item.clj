(ns autofocus-clj-ver-1.item)

;; question: What is an effective way to deal with duplicates in a list?
  ;; answer with a question: How will dealing with duplicates be relevant in this program?
  (defn create-new-item-from-text
    [text-input]
    {;;:id "1234567890" ;; TODO: replace hardcoded non-unique id w/ incremental, unique
     :status :clean
     :text text-input
   ;; :is-hidden false
     })

  ;; question: Is this best replaced by a hashmap?
(defn status-to-mark [status]
  (cond
    (= status :clean) "[ ]"
    (= status :marked) "[o]"
    (= status :done) "[x]"
    :else "?"))

(defn render-item-with-mark [item]
  (str " - " (status-to-mark (get item :status)) " " (get item :text)))

(defn contains-status?
  "checks to see if an item is of a given status"
  [item-input status]
  (= (item-input :status) status))

  ;; (defn get-status [item-input]
  ;;   (item-input :status))

(defn change-status-of-item
  [input-item input-status]
  (assoc input-item :status input-status))