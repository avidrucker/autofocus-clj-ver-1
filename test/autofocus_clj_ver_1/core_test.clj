(ns autofocus-clj-ver-1.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [autofocus-clj-ver-1.core :as af]))

(do
  (def example-item
    {:status :clean
     :text "Wash the dishes"})

  (def example-empty-list [])

  (def example-list-one-item
    [{:status :clean
      :text "a"}])

  (def example-list-all-clean
    [{:status :clean
      :text "a"}
     {:status :clean
      :text "b"}
     {:status :clean
      :text "c"}])

  (def example-list-first-marked
    [{:status :marked
      :text "a"}
     {:status :clean
      :text "b"}
     {:status :clean
      :text "c"}])

  (def example-list-first-completed
    [{:status :done
      :text "a"}
     {:status :clean
      :text "b"}
     {:status :clean
      :text "c"}])

  (def example-list-first-completed-second-marked
    [{:status :done
      :text "a"}
     {:status :marked
      :text "b"}
     {:status :clean
      :text "c"}])
  
  (def example-list-three-items-all-completed
    [{:status :done
      :text "a"}
     {:status :done
      :text "b"}
     {:status :done
      :text "c"}])
  
  (def example-list-three-items-first-and-last-marked
    [{:status :marked
      :text "a"}
     {:status :clean
      :text "b"}
     {:status :marked
      :text "c"}])
  
  (def example-list-three-items-first-marked-last-completed
    [{:status :marked
      :text "a"}
     {:status :clean
      :text "b"}
     {:status :done
      :text "c"}])

  (def example-list-two-items-first-completed
    [{:status :done
      :text "a"}
     {:status :clean
      :text "b"}])

  (def example-list-two-items-first-marked
    [{:status :marked
      :text "a"}
     {:status :clean
      :text "b"}])
  
  (def example-list-two-items-first-completed-second-marked
    [{:status :done
      :text "a"}
     {:status :marked
      :text "b"}])

  (def quick-three
    ["a" "b" "c"])

  ;; (def regular-three
  ;;   ["Write report" "Check email" "Tidy desk"])

  ;; TODO: use this for small E2E tests [micro] [mini] [tiny]
  ;; (def fruit
  ;;   '("apple" "banana" "cherry"))
  )

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
     (= " - [ ] a\n - [ ] b\n - [ ] c"
        (af/stringify-list example-list-all-clean))
     "Rendering a list that has only 
      clean items works as expected")
    (is
     (= "[ ] [ ] [ ]"
        (af/stringify-list-compact example-list-all-clean))
     "Compact-rendering a list with 
      three clean items works as expected"))

  (testing "Determine if certain status exists in a list"
    (is (= true (af/list-has-items-of-status
                 example-list-all-clean 
                 :clean)))
    (is (= false (af/list-has-items-of-status
                  example-list-all-clean 
                  :done)))
    (is (= false (af/list-has-items-of-status
                  example-list-first-completed 
                  :marked)))
    (is (= true (af/list-has-items-of-status
                 example-list-first-completed-second-marked 
                 :clean)))
    (is (= true (af/list-has-items-of-status
                 example-list-first-completed-second-marked 
                 :marked)))
    (is (= true (af/list-has-items-of-status
                 example-list-first-completed-second-marked 
                 :done))))

  (testing "Determine if certain status DOES NOT EXIST in a list"
    (is (= false (af/list-has-none-of-status 
                  example-list-all-clean 
                  :clean)))
    (is (= true (af/list-has-none-of-status 
                 example-list-all-clean 
                 :done)))
    (is (= true (af/list-has-none-of-status 
                 example-list-first-completed 
                 :marked)))
    (is (= false (af/list-has-none-of-status 
                  example-list-first-completed-second-marked 
                  :clean)))
    (is (= false (af/list-has-none-of-status 
                  example-list-first-completed-second-marked 
                  :marked)))
    (is (= false (af/list-has-none-of-status 
                  example-list-first-completed-second-marked 
                  :done))))

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
           (af/stringify-list-compact
            (af/mark-first-markable
             example-list-all-clean)))
        "Marking a list that has only clean 
         items works as expected")
    (is (= "[x] [o] [ ]"
           (af/stringify-list-compact
            (af/mark-first-markable
             example-list-first-completed)))
        "Marking a list w/ one completed & two 
         clean items works as expected"))

  (testing "Generating review questions"
    (is (= "Do you want to do 'b' more than 'a'?"
           (af/generate-review-msg example-list-first-marked 1))
        "Generates the correct review message."))

  ;; TODO: implement is-reviewable-list? with TDD
  (testing "Determining if a list is reviewable"
    ;; list of size 0
    (is (= false (af/is-reviewable-list? example-empty-list))
        "Correctly determines list NOT reviewable")
    ;; lists of size 1
    (is (= false (af/is-reviewable-list? example-list-one-item))
        "Correctly determines list NOT reviewable")
    ;; lists of size 2
    (is (= false (af/is-reviewable-list?
                  example-list-two-items-first-completed))
        "Correctly determines list NOT reviewable")
    (is (= true (af/is-reviewable-list?
                 example-list-two-items-first-marked))
        "Correctly determines that list IS reviewable")

    ;; lists of size 3
    ;; yes: example-list-first-marked
    ;; yes: example-list-first-completed-second-marked
    ;; no: example-list-three-items-all-completed
    ;; no: example-list-three-items-first-and-last-marked
    (is (= true (af/is-reviewable-list?
                 example-list-first-marked))
        "Correctly determines that list IS reviewable")
    (is (= true (af/is-reviewable-list?
                 example-list-first-completed-second-marked))
        "Correctly determines that list IS reviewable")
    (is (= false (af/is-reviewable-list?
                  example-list-three-items-all-completed))
        "Correctly determines list NOT reviewable")
    (is (= false (af/is-reviewable-list?
                  example-list-three-items-first-and-last-marked))
        "Correctly determines list NOT reviewable"))
  
  (testing "Marking the last marked item as done"
    (is (= example-list-three-items-first-marked-last-completed
           (af/mark-closest-to-end-marked-item-done
            example-list-three-items-first-and-last-marked))
        "Correctly marked the last item as done"))
  
  ;;;;
  (testing "Selecting the first index to start reviewing"
    (is (= 1 (af/get-first-reviewable-index 
              example-list-first-marked)))
    (is (= 2 (af/get-first-reviewable-index
              example-list-first-completed-second-marked)))
    )
  )

(defn scaffold-integration-test-1
  "makes an empty list, adds one item to it,
   and returns the new list of size 1"
  []
  (af/add-item-to-list
   [] ;; blank new empty list
   (af/create-new-item-from-text "a") ;; newly created to-do item
   ))

(deftest integration-tests
  ;; question: How are integration/E2E tests set up in
  ;;  an effective manner in Clojure?
  (testing "adding items to a list"
    (is (=
         (scaffold-integration-test-1)
         example-list-one-item)
        "works as expected")))

;; TODO: replace this with actual API once it is built
(defn scaffold-e2e-test-simple
  "temporary setup function to scaffold e2e tests"
  []
  (let [item-list 
        (map af/create-new-item-from-text quick-three)]
    (vec (flatten (into []
          (map #(af/add-item-to-list [] %)
               item-list))))))

;; TODO: use scaffold-e2e-test-simple to set up first e2e test
(deftest end-to-end-tests
  (testing "first simple e2e test"
    (is (= 
         " - [o] a\n - [ ] b\n - [ ] c"
         (af/stringify-list
          ;; TODO: replace `mark-first-markable` with `review-list` 
          (af/mark-first-markable 
           (scaffold-e2e-test-simple)))))))