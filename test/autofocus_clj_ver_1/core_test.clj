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