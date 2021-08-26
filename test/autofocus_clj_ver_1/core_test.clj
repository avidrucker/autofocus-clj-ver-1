(ns autofocus-clj-ver-1.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [autofocus-clj-ver-1.core :as af]
            [clojure.string :as string]))

(def example-item
  {:id "1234567890"
   :status :clean
   :text "Wash the dishes"
   :is-hidden false})

(def example-empty-list [])

(def example-list-all-clean
  [{:id "1234567890"
    :status :clean
    :text "apple"
    :is-hidden false}
   {:id "1234567890"
    :status :clean
    :text "banana"
    :is-hidden false}
   {:id "1234567890"
    :status :clean
    :text "cherry"
    :is-hidden false}])

;; TODO: use this for the auto-mark function
;;       (the result should be [x] [o] [ ])
(def example-list-first-completed
  [{:id "1234567890"
    :status :completed
    :text "apple"
    :is-hidden false}
   {:id "1234567890"
    :status :clean
    :text "banana"
    :is-hidden false}
   {:id "1234567890"
    :status :clean
    :text "cherry"
    :is-hidden false}])

;; TODO: use this for the auto-markable test
;;       (it should return false)
(def example-list-first-completed-second-marked
  [{:id "1234567890"
    :status :completed
    :text "apple"
    :is-hidden false}
   {:id "1234567890"
    :status :marked
    :text "banana"
    :is-hidden false}
   {:id "1234567890"
    :status :clean
    :text "cherry"
    :is-hidden false}])

(def fruit
  '("apple" "banana" "cherry"))

;; pure
(defn stringify-list-compact
  "Renders a list to a single-line string of only its marks"
  [input-list]
  (string/join " " (mapv #(af/status-to-mark (:status %)) input-list)))

;; (some? (some #(af/contains-status? % :clean) example-list-all-clean))
;; (some? (some #(af/contains-status? % :done) example-list-all-clean))

(deftest a-test
  (testing "Rendering lists"
    (is
     (= (af/stringify-list []) "list is empty")
     "Rendering an empty list works as expected")
    (is
     (= (af/stringify-list [example-item]) " - [ ] Wash the dishes")
     "Rendering a list with one item works as expected")
    (is
     (= (af/stringify-list example-list-all-clean)
        " - [ ] apple\n - [ ] banana\n - [ ] cherry")
     "Rendering a list that has only clean items works as expected")
    (is
     (= (stringify-list-compact example-list-all-clean)
        "[ ] [ ] [ ]")
     "Compact-rendering a list with three clean items works as expected"))

  (testing "Marking lists"
    (is
     (= (af/stringify-list (af/mark-first-item example-list-all-clean))
        " - [o] apple\n - [ ] banana\n - [ ] cherry")
     "Marking a list that has only clean items works as expected"))

  (testing "Determining if a list is auto-markable"
    (is (= false (af/is-auto-markable-list?
                  example-empty-list)))
    (is (= true (af/is-auto-markable-list?
                 example-list-all-clean)))
    (is (= true (af/is-auto-markable-list?
                 example-list-first-completed)))
    (is (= false (af/is-auto-markable-list?
                  example-list-first-completed-second-marked)))
    ))

;; TODO: convert to a test
;; (defn demo1
;;   "prints out empty list, adds a new item, and then prints again"
;;   []
;;   (let [app-state1 {:current-list []}
;;         app-state2 (assoc app-state1
;;                           :current-list
;;                           (add-item-to-list
;;                            (app-state1 :current-list)
;;                            example-item))]

;;     (print-demo 1 app-state1 app-state2)))

;; (defn text-to-todo-item
;;   "converts text input to new to-do list item"
;;   [text-input]
;;   {:id "1234567890" ;; TODO: replace hardcoded non-unique id w/ incremental, unique
;;    :status :clean
;;    :text text-input
;;    :is-hidden false})

;; TODO: Convert to a test
;; TODO: implement automark of first markable item
;; (defn demo2
;;   "adds three items to list, prints, 
;;    automarks the first markable item, 
;;    and then prints again"
;;   []
;;   (let [app-state1 [] ;; {:current-list []}
;;         ;; Build fruit to-do items
;;         items-to-add (map text-to-todo-item fruit)
;;         ;; Note: Mapping here converts vector to list
;;         ;;       ... which makes vector/sequence only
;;         ;;       functions not behave (at all or as expected)
;;         ;; question: Is there a cleaner way to map over
;;         ;;     a data source (in this case the fruit list)
;;         ;;     without needing to flatten and vec back to
;;         ;;     the desired shape? (flat vector of hashmaps)  
;;         ;; Add fruit to-do items to to-do list
;;         app-state2 (vec (flatten (map
;;                                   #(add-item-to-list
;;                                     app-state1 %)
;;                                   items-to-add)))
;;         app-state3 (mark-first-item app-state2)]

;;     ;; TODO: replace printout with separate function
;;     (binding [p/*print-right-margin* 30]
;;       (println "start of demo2")
;;       (println "before (1):")
;;       (p/pprint (stringify-list app-state1))
;;       (println "after (2):")
;;       (p/pprint (stringify-list app-state2))
;;       (println "final (3):")
;;       (p/pprint (stringify-list app-state3))
;;       (println "end of demo2"))))

;; (defn print-demo [number before-state after-state]
;;   (binding [p/*print-right-margin* 30]
;;     (println (str "start of demo" number))
;;     (println "before:")
;;     (p/pprint (stringify-list (before-state :current-list)))
;;     (println "after:")
;;     (p/pprint (stringify-list (after-state :current-list)))
;;     (println (str "end of demo" number))))

;; question: is this effectful, or side-effecting?
;; (defn print-tight [stuff-to-print]
;;   (binding [p/*print-right-margin* 30]
;;     (p/pprint stuff-to-print)))

;; (defn print-vertically [stuff-to-print]
;;   (concat "" (map #(str % "\n") stuff-to-print)))