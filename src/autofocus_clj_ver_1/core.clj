(ns autofocus-clj-ver-1.core
  (:gen-class)
  (:require [clojure.string :as string]))
;; [clojure.pprint :as p]

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

;; question: is this effectful, or side-effecting?
;; (defn print-tight [stuff-to-print]
;;   (binding [p/*print-right-margin* 30]
;;     (p/pprint stuff-to-print)))

;; (defn print-vertically [stuff-to-print]
;;   (concat "" (map #(str % "\n") stuff-to-print)))

;; pure
(defn stringify-list
  "enables convenient printing of our to-do list, `list-to-string`"
  [list-input]
  (if (zero? (count list-input))
    "list is empty"
    (string/join "\n" (render-list-with-marks list-input))))

;; (defn print-demo [number before-state after-state]
;;   (binding [p/*print-right-margin* 30]
;;     (println (str "start of demo" number))
;;     (println "before:")
;;     (p/pprint (stringify-list (before-state :current-list)))
;;     (println "after:")
;;     (p/pprint (stringify-list (after-state :current-list)))
;;     (println (str "end of demo" number))))

;; ;; TODO: implement stub
;; (defn is-auto-markable-list?
;;   "Determines whether a list is ready to be 'auto-marked'"
;;   [input-list]
;;   (condp
;;    (zero? (count input-list)) true
;;     (and
;;      (some :clean input-list)
;;      (not-any? :marked input-list)) true
;;     false ;; default else
;;     ))

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

;; "dot item"
(defn mark-item
  "Changes the status attribute of an item, 
   indicating that it is ready to do.
   This is also called 'dotting an item'."
  [input-item]
  (assoc input-item :status :marked))

;; TODO: uncomment when this function is needed
;; (defn complete-item
;;   [input-item]
;;   (assoc input-item :status :completed))

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