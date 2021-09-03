(ns autofocus-clj-ver-1.public-test
  (:require [clojure.test :refer [deftest is testing]]
            [autofocus-clj-ver-1.core :as af]
            [autofocus-clj-ver-1.examples :as eg]
            [autofocus-clj-ver-1.public :as pub]))

;; EXTERNAL API

(deftest unit-tests
  (testing "Focusing on a list"
    (is (= eg/example-list-three-items-first-marked-last-completed
           (pub/focus-on-list
            eg/example-list-three-items-first-and-last-marked))
        "Correctly marks the last item as done when list IS focusable")
    (is (= eg/example-list-three-items-all-completed
           (pub/focus-on-list
            eg/example-list-three-items-all-completed))
        "Correctly does nothing when list is NOT focusable")
    (is (= eg/example-list-all-clean
           (pub/focus-on-list
            eg/example-list-all-clean))
        "Correctly does nothing when list is NOT focusable")))

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
         eg/example-list-one-item)
        "works as expected")))

;; TODO: replace this with actual API once it is built
(defn scaffold-e2e-test-simple
  "temporary setup function to scaffold e2e tests"
  []
  (let [item-list
        (map af/create-new-item-from-text eg/quick-three)]
    (vec (flatten (into []
                        (map #(af/add-item-to-list [] %)
                             item-list))))))

;; TODO: use scaffold-e2e-test-simple to set up first e2e test
(deftest end-to-end-tests
  (testing "first simple e2e test"
    (is (=
         "[o] [o] [o]"
         (af/stringify-list-compact
          ;; TODO: replace `mark-first-markable` with `review-list` 
          (pub/review-list
           (scaffold-e2e-test-simple) ["y" "y"]))))))
