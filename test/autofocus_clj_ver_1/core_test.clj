(ns autofocus-clj-ver-1.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [autofocus-clj-ver-1.core :as af]
            [clojure.string :as string]))

(do
  (def example-item
    {;; :id "1234567890"
     :status :clean
     :text "Wash the dishes"
   ;; :is-hidden false
     })

  (def example-empty-list [])

  (def example-list-one-item
    [{:status :clean
      :text "a"}])

  (def example-list-all-clean
    [{;; :id "1234567890"
      :status :clean
      :text "apple"
    ;; :is-hidden false
      }
     {;; :id "1234567890"
      :status :clean
      :text "banana"
    ;; :is-hidden false
      }
     {;; :id "1234567890"
      :status :clean
      :text "cherry"
    ;; :is-hidden false
      }])

  (def example-list-first-completed
    [{;; :id "1234567890"
      :status :done
      :text "apple"
    ;; :is-hidden false
      }
     {;; :id "1234567890"
      :status :clean
      :text "banana"
    ;; :is-hidden false
      }
     {;; :id "1234567890"
      :status :clean
      :text "cherry"
    ;; :is-hidden false
      }])

  (def example-list-first-completed-second-marked
    [{;; :id "1234567890"
      :status :done
      :text "apple"
    ;; :is-hidden false
      }
     {;; :id "1234567890"
      :status :marked
      :text "banana"
    ;; :is-hidden false
      }
     {;; :id "1234567890"
      :status :clean
      :text "cherry"
    ;; :is-hidden false
      }])

  ;; TODO: use this for small E2E tests [micro] [mini] [tiny]
  ;; (def fruit
  ;;   '("apple" "banana" "cherry"))
  
  ;; pure
  (defn stringify-list-compact
    "Renders a list to a single-line string of only its marks"
    [input-list]
    (string/join " " (mapv #(af/status-to-mark (:status %)) input-list))))

(deftest unit-tests
  (testing "Rendering lists"
    (is
     (= "list is empty"
        (af/stringify-list []))
     "Rendering an empty list works as expected")
    (is
     (= " - [ ] Wash the dishes"
        (af/stringify-list [example-item]))
     "Rendering a list with one item works as expected")
    (is
     (= " - [ ] apple\n - [ ] banana\n - [ ] cherry"
        (af/stringify-list example-list-all-clean))
     "Rendering a list that has only clean items works as expected")
    (is
     (= "[ ] [ ] [ ]"
        (stringify-list-compact example-list-all-clean))
     "Compact-rendering a list with three clean items works as expected"))

  (testing "Determine if certain status exists in a list"
    (is (= true (af/list-has-items-of-status example-list-all-clean :clean)))
    (is (= false (af/list-has-items-of-status example-list-all-clean :done)))
    (is (= false (af/list-has-items-of-status example-list-first-completed :marked)))
    (is (= true (af/list-has-items-of-status example-list-first-completed-second-marked :clean)))
    (is (= true (af/list-has-items-of-status example-list-first-completed-second-marked :marked)))
    (is (= true (af/list-has-items-of-status example-list-first-completed-second-marked :done))))

  (testing "Determine if certain status DOES NOT EXIST in a list"
    ;; list-has-none-of-status
    (is (= false (af/list-has-none-of-status example-list-all-clean :clean)))
    (is (= true (af/list-has-none-of-status example-list-all-clean :done)))
    (is (= true (af/list-has-none-of-status example-list-first-completed :marked)))
    (is (= false (af/list-has-none-of-status example-list-first-completed-second-marked :clean)))
    (is (= false (af/list-has-none-of-status example-list-first-completed-second-marked :marked)))
    (is (= false (af/list-has-none-of-status example-list-first-completed-second-marked :done))))

  (testing "Determining if a list is auto-markable"
    (is (= false
           (af/is-auto-markable-list?
            example-empty-list)))
    (is (= true
           (af/is-auto-markable-list?
            example-list-all-clean)))
    (is (= true
           (af/is-auto-markable-list?
            example-list-first-completed)))
    (is (= false
           (af/is-auto-markable-list?
            example-list-first-completed-second-marked))))

  (testing "Finding first markable item in a list"
    (is (= 0 (af/index-of-first-markable
              example-list-all-clean)))
    (is (= 1 (af/index-of-first-markable
              example-list-first-completed))))

  (testing "Marking first markable item in a list"
    (is (= "[o] [ ] [ ]"
           (stringify-list-compact
            (af/mark-first-markable
             example-list-all-clean)))
        "Marking a list that has only clean items works as expected")
    (is (= "[x] [o] [ ]"
           (stringify-list-compact
            (af/mark-first-markable
             example-list-first-completed)))
        "Marking a list w/ one completed & two clean items works as expected")))

;; question: Could the state be removed/reduced here by
;; using a threading macro? What other effective strategies
;; are there to reduce/remove the usage of `let`?
(defn scaffold-integration-test-1
  "for adding items to a list"
  []
  ;; create new list
  (let [my-list []]
    ;; add a new item
    (af/add-item-to-list
     my-list
     (af/create-new-item-from-text
      "a"))))

(deftest integration-tests
  ;; question: How are integration/E2E tests set up in an effective manner?
  (testing "adding items to a list"
    (is (=
         (scaffold-integration-test-1)
         example-list-one-item))))

(def firstThree
  ["Write report" "Check email" "Tidy desk"])

(defn scaffold-e2e-test-simple
  [])

;; (deftest end-to-end-tests
;;   (testing "something"
;;     (is (= 1 0))))