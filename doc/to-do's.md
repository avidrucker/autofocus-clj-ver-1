# To-Do's

- [x] Create one example item manually

- [x] Create a mutable list

- [x] Enable adding items to list

- [x] Enable conversion of list to string where an empty list prints out as `list is empty`

- [x] Enable clearing of list to deal with persistent state of atoms

- [x] Extract demo code to isolated demo fuction 

- [x] Create demo where three items are added to the list, and the first markable item is marked [functionality] [temporary] [visible]
    - [x] Squash bug where it appears that state is not being correct mutated
        - [x] Convert as many functions to pure functions as possible to reduce bug-friendly habitat [bugfix]
            - [x] Remove global state from demo1
            - [x] Remove global state from demo2
        - [x] Remove mutable state to reduce bug-friendly habitat [bugfix]
    - [x] Clean up render output to make individual items easier to read
    
- [x] Render the list items with their marks "Render the list as unadorned, dotted (open circle), or checked (filled circle)"

- [x] Write my first Clojure test

- [x] Move example code to core_test

- [ ] Do some TDD to develop the next few features
    - [ ] Rewrite the E2E tests in Clojure to use minimal setup & scaffolding, and only surface API (ie. no wrapping of functions except for string outputting purposes)
        - [ ] List two to three features that you can infer from the E2E tests
            - [ ] Import in the smallest two or three E2E tests from fp-autofocus (written in TypeScript)

- [ ] Enable scenario 1:
    ```
    1. User starts program for the first time w/ an empty list
    2. User adds three items to their list
    3. User starts to review their list
        4. The first item gets marked (adding a dot to the item, to indicate that it is "ready to be done now") b/c it is the first markable item and no other items have been marked yet
        5. The user is given a choice, "do they want to do item 2 more than item 1 (the marked item closest to the end of the list)"? They may choose "yes", "no", or to "quit" (and stop the review process early)
        6. Every time the user answers "yes", the current item is marked and the app proceeds to review the next item (always asking, "do you want to do the current item more than the "closest to the end" marked item?"). Every time the user answers "no", the current item is left unmarked. Every time the user quits, the marks made on this session persist.
    7. After reviewing, there is at least one marked item. The user then goes into "focus mode" to work on the marked item "closest to the end" of the list. Once they are done working on it, the item is marked complete.
        8. Optionally, if there is still work left to be done on this item, a duplicate of this item with a clean status is created and added to the end of the list.
    
    ```

- [x] Enable the adding of items to a list

- [x] Test the adding of items to a list

- [x] Write your first unit test which uses "auto-marking" function `mark-first-markable!` [functionality] [visible]

    - [x] Implement `is-auto-markable-list?`
    - [x] Implement `get-first-markable-index`
    - [x] Implement `mark-first-markable!`

- [x] Enable "focusing" on items which marks them "complete"

- [x] Create review strings from current list state

- [x] Determine whether a list is reviewable

    - [x] Implement `is-reviewable-list?`

- [x] Implement reviewing of a list by entering a list of yes/no/quit strings ("y", "n", or "q")

- [x] Question: How can reviewing be made interruptible / cancellable while also being functionally pure with separation of concerns? (context: in the past, this seems to require both IO and while loops)

    - [x] Answer: Generate the full list of questions up front when starting a review session. Next, once you are done reviewing, submit the list of inputted yes/no/quit choices to update the to-do list.

- [ ] Enable user to focus on and complete "priority" items (marked items closest to the end of the list)

    - [ ] Enable user to review their items in the command line application
        - [ ] Enable user to input text for a new to-do item manually in the console [IO]
            - [ ] Prevent user from entering invalid text to-do's (only whitespace or empty string)
            - [ ] Enable transition between app "sub-states" (eg. `menu` to `adding`, `adding` to `menu`, etc.)
            - [x] Sanitize user input to remove leading and trailing whitespace
            - [x] Enable user to quit the application by using a menu choice
                - [x] Implement a user menu interface, which offers the user the choice to: enter a new to-do item, review their list, focus on the prioritized item (marked item closest to the end of the list), or quit

- [ ] Question: Can state be removed/reduced using a threading macro? What other effective strategies are there to reduce/remove the usage of `let`? See `scaffold-e2e-test-simple`

- [x] Break code into three namespaces:

    - [x] IO (printing, getting user input)
    - [x] demos
    - [x] domain logic
      - [ ] Internal API
      - [ ] External API

- [x] Brainstorm clearer naming conventions for to-do item statuses: "unmarked", "dotted", and "completed" [readability]

    > unmarked, **clean**, untouched, ~~blank~~, 
    >
    > dotted, **marked**, ~~ready~~, circled
    >
    > complete, completed, **done**

- [x] Update statuses to reflect clearer naming conventions

- [x] Enable the user to see what their "bad input" was when answering a question with input validation / constraints

- [x] Tell the user when they haven't answered a question (by entering in nothing)

- [ ] Implement auto-marking after each `focus-on-list`

- [ ] Implement the following e2e test
    ```clojure
    ;; attempt to "sort" (do in order) item list by number priority (1,2,3...N)
    "E2E test to 'sort' a list of number items from lowest to highest (1,2,3...)"
    ;; the list:
    ["25","16","104","39","5","86","23","1","105","94","34"]
    ;; (11 items in total)
    ;; first review:
    ["y","n","n","y","n","n","y","n","n","n"]
    ;; => "[o] [o] [ ] [ ] [o] [ ] [ ] [o] [ ] [ ] [ ]"
    ;; NOTE: the review algorithm needs to be redone after each
	;;   focus in order to reassess, 'Are there "higher priority" items after
	;;   the "last marked item" / "current priority item"?'
    ;; question: Is it a hard rule that the review algorithm must 
    ;;           be run after each focus session? 
    ;; three focuses:
    ;; => "[o] [x] [ ] [ ] [x] [ ] [ ] [x] [ ] [ ] [ ]"
    ;; second review:
    ["n","n","n","y","n","n","n"]
    ;; => "[o] [x] [ ] [ ] [x] [ ] [o] [x] [ ] [ ] [ ]"
    ;; Note: "since the remainder of marked items now have no higher priority items
    ;; in the unmarked part of the list, these marked items can be
	;; completed in order all in one swoop"
    ;; question: can the above be determined programmatically?
    ;; fourth and fifth focuses:
    ;; TODO: confirm that auto-marking after `focus-on-list` results in this...
    ;; => "[x] [x] [o] [ ] [x] [ ] [x] [x] [ ] [ ] [ ]"
    ;; ... and NOT this: "[x] [x] [ ] [ ] [x] [ ] [x] [x] [ ] [ ] [ ]"
    ;; third review: 
    ["y","n","n","n","y"]
    ;; => "[x] [x] [o] [o] [x] [ ] [x] [x] [ ] [ ] [o]"
    ;; sixth and seventh focuses:
    ;; => "[x] [x] [o] [x] [x] [ ] [x] [x] [ ] [ ] [x]"
    ;; fourth review:
    ["y","n","n"]
    ;; => "[x] [x] [o] [x] [x] [o] [x] [x] [ ] [ ] [x]"
    ;; 8th focus:
    ;; => "[x] [x] [o] [x] [x] [x] [x] [x] [ ] [ ] [x]"
    ;; 5th review:
    ['n','y']
    ;; => "[x] [x] [o] [x] [x] [x] [x] [x] [ ] [o] [x]"
    ;; again, as before:
    "since the remainder of marked items now have no higher priority items
	in the unmarked part of the list, these marked items can be
	completed in order all in one swoop"
    ;; two more focuses, with an auto-marking after the final marked item is marked done:
    ;; => "[x] [x] [x] [x] [x] [x] [x] [x] [o] [x] [x]"
    ```

## Won't Do ... Yet

- ~~Update code to enable automatic assignment *and* incrementing of to-do list item IDs~~ [temporary] [invisible]

- Item visibility toggling (hiding / showing)

- Enable the user to say that there is more work to do on a task after ending a focus session