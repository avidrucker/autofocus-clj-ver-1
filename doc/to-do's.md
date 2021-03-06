# To-Do's

## Roadmap

- command line app
- web app w/ re-frame

---

## Initial Rough Draft

- [x] Create one example item manually

- [x] Create a mutable list

- [x] Enable adding items to list

- [x] Enable conversion of list to string where an empty list prints out as `list is empty`

- [x] Enable clearing of list to deal with persistent state of atoms

- [x] Extract demo code to isolated demo function 

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

- [ ] Clarify the following to make them concrete & specific

  - [ ] Do some TDD to develop the next few features
    - [ ] Rewrite the integration tests in Clojure to use minimal setup & scaffolding, and only public API (ie. no wrapping of functions except for string outputting purposes, or to pass data as absolutely necessary)
      - [ ] List two to three features that you can infer from the integration (formerly called "e2e") tests
      - [ ] Import in the smallest two or three integration tests from fp-autofocus (written in TypeScript)
        - [x] "add items, review items, and focus on an item"
        - [x] "long flow integration test"
        - [ ] "number sorting flow integration test"

- [x] Enable integration test "scenario X" (see "user flow scenario x")

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

- [ ] Enable user to focus on and complete "priority" items (marked items closest to the end of the list)

  - [ ] Enable user to review their items in the command line application
    - [ ] Enable user to input text for a new to-do item manually in the console [IO]
      - [ ] Prevent user from entering invalid text to-do's (only whitespace or empty string)
      - [ ] **Enable transition between app "sub-states" (eg. `menu` to `adding`, `adding` to `menu`, etc.)**
      - [x] Sanitize user input to remove leading and trailing whitespace
      - [x] Enable user to quit the application by using a menu choice
        - [x] Implement a user menu interface, which offers the user the choice to: enter a new to-do item, review their list, focus on the prioritized item (marked item closest to the end of the list), or quit

- [x] Break code into five namespaces:

  - [x] demos
  - [x] IO (printing, getting user input)
  - [x] tests
    - [x] unit
    - [x] integration and unit
  - [x] External API domain logic
  - [x] Internal API domain logic

- [x] Brainstorm clearer naming conventions for to-do item statuses: "unmarked", "dotted", and "completed" [readability]

  > unmarked, **clean**, untouched, ~~blank~~, 
  >
  > dotted, **marked**, ~~ready~~, circled
  >
  > complete, completed, **done**

- [x] Update statuses to reflect clearer naming conventions

- [x] Enable the user to see what their "bad input" was when answering a question with input validation / constraints

- [x] Tell the user when they haven't answered a question (by entering in nothing)

- [x] Implement auto-marking after each `focus-on-list`

- [ ] Tidy up codebase

  - [ ] Delete entirely or extract & relocate debugging statements
  - [ ] Extract "comment TODO's" to this document
  - [ ] Extract questions w/ relevant code snippets to this document

- [x] Test to confirm auto-marking after focusing works as desired. (see "long flow integration test" which confirms)

- [ ] **Implement "last-done functionality" to enable the app state to remember what the most recently completed item index was**

  - [ ] Implement app-t (perhaps an atom with an integer in it) to keep a "current index" of internal application time
  - [ ] Add t-completed, t-marked, t-created w/o changing/removing the `clean/marked/done` statuses flag (add schema via "accretion" to gracefully transition the to-do list state architecture)

- [ ] Implement "long flow integration test" (formerly called "long-e2e-test")

  - [x] 1. Create a blank list
  - [x] 2. Add the items to the list
  - [x] 3. Do the first review
  - [ ] 4. Do the second review which requires `last-done`

---

## Notes

- [ ] Implement FSM as data

  reference: https://www.cognitect.com/blog/2017/5/22/restate-your-ui-using-state-machines-to-simplify-user-interface-development

  ```clojure
  ;;                           persistant data storage
  ;;                                  ^^^^^
  ;;                      adaptor at the persistence layer
  ;;              (This is responsible for saving & loading, EDN and/or CSV)
  ;;              note: all you need for EDN is (split) and (slurp)
  ;;              note: Wrapping in a protocol can allow for extending later
  ;;              (this can also handle text output to a format that
  ;;                 is easily printed out for human consumption)
  ;;                       _____________________________
  ;;                       |       pure functions       |
  ;; |    input / output   | menu/adding/reviewing/etc. | 
  ;; human > program > cli >    application state (which part of the program I am in)       
  ;;                              to-do list state (items in the list)
  ```

---

## AutoFocus 2021_09_19 To-Do's

- [ ] Enable display of add menu in the CLI
  - [x] Enable changing of app state via CLI (this was already done)
- [ ] Programatically derive last-done using a function of sorting list items on their t-done key value
  - [ ] Add `t-created`, `t-marked`, and `t-done` as map keys to to-do items
    - [ ] Add `t-time` to app-state

---

## Investigate

- `read` for ~~evaluating~~ accepting / taking in Clojure code from the console, for example, keywords

## Won't Do ... Yet

- ~~Update code to enable automatic assignment *and* incrementing of to-do list item IDs~~ [temporary] [invisible]
- Item visibility toggling (hiding / showing) [not critical]
- Enable the user to say that there is more work to do on a task after ending a focus session
- Splash screen
- Persisting items outside of the application state (saving, loading, exporting, importing, databases, etc.)
  - things to try: protocols & reified Java objects ~ DF
- Users, Accounts, Login, Authentication, Registering
- Multiple lists ("work", "personal", "etc.")
- Settings to modify look, feel, display, etc.
- Accessibility options (for those with cognitive, physical, sensory, or other impairments)
  - Voice control (no hands, keyboard, or mouse)
  - "Zen mode" (only see one thing at a time)
  - Mouse only mode
- Integrations with other services, such as email, calendar, virtual assistant, emacs
- Workplace and/or teamwork integrations
- Packaging as a plug-in for a web browser
- Auto-date-and-time stamping of lists
- Auto-date-and-time-stamping of items
- Ability to re-wind or undo what you have done in the application ("time-travel") [advised to try by DF]
  - undo stack
  - last-app-state
  - note: trivial to implement in re-frame

---

## Questions

- [x] How can reviewing be made interruptible / cancellable while also being functionally pure with separation of concerns? (context: in the past, this seems to require both IO and while loops)

  - [x] Answer: Generate the full list of questions up front when starting a review session. Next, once you are done reviewing, submit the list of inputted yes/no/quit choices to update the to-do list.

- [ ] Can state be removed/reduced using a threading macro? What other effective strategies are there to reduce/remove the usage of `let`? See `scaffold-integration-test-1`

- [ ] Is there a cleaner way to map over a data source (in this case the fruit list) without needing to flatten and vec back to the desired shape? (flat vector of hashmaps)

  ```clojure
  items-to-add (map create-new-item-from-text fruit)
  ;; Note: Mapping here converts vector to list
  ;;       ... which makes vector/sequence only
  ;;       functions not behave (at all or as expected)
  ;; Add fruit to-do items to to-do list
  list-1 (vec (flatten (map
                            #(add-item-to-list app-state1 %)
                             items-to-add)))
  ```
  
- [x] Does `last-done` require its own state memory to maintain? Could it perhaps be simpler and algorithmically calculatable (eg. the completed item closest to the beginning of the list)? **TLDR Answer: Yes, it appears to require its own memory to maintain.**

  - [x] Investigate on paper w/ all 3 item list permutations
  - [x] Results: As far as I can tell, one cannot determine which was the last done item simply by looking at the list itself
  
- [ ] What does my data look like?

- [ ] question: What needs to happen in order to allow "back button" functionality in the command line app? For example: State history stack, state history buffer (remembering X states & changes)

  - [ ] sub-question: What about limited back/interrupt functionality simply for a few states? For example:
    - [ ] Backing out of creating a to-do list item
    - [ ] Backing out of a review session
    - [ ] Backing out of a focus session

---

## User Flows

### User Flow Scenario X

1. User starts program for the first time w/ an empty list
2. User adds three items to their list
3. User starts to review their list
    4. The first item gets marked (adding a dot to the item, to indicate that it is "ready to be done now") b/c it is the first markable item and no other items have been marked yet
    5. The user is given a choice, "do they want to do item 2 more than item 1 (the marked item closest to the end of the list)"? They may choose "yes", "no", or to "quit" (and stop the review process early)
    6. Every time the user answers "yes", the current item is marked and the app proceeds to review the next item (always asking, "do you want to do the current item more than the "closest to the end" marked item?"). Every time the user answers "no", the current item is left unmarked. Every time the user quits, the marks made on this session persist.
7. After reviewing, there is at least one marked item. The user then goes into "focus mode" to work on the marked item "closest to the end" of the list. Once they are done working on it, the item is marked complete.
    8. Optionally, if there is still work left to be done on this item, a duplicate of this item with a clean status is created and added to the end of the list.

### Number Sorting Flow Integration Test

```clojure
;; "number sorting flow integration test"
;; attempt to "sort" (do in order) item list by number priority (1,2,3...N)
"Integration test to 'sort' a list of number items from lowest to highest (1,2,3...)"
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

---

### Learning Magit

- [ ] Learn how to see current changes & recent commits in a Git enabled project directory

  - [ ] Answer: Using the magit command `magit-status`

  - [ ] Enable a keybinding shortcut to run `magit-status`

    - [ ]  Try adding this to the `init.el`

      ```lisp
      (use-package magit
        :ensure t
        :bind ((("C-c g" . magit-file-dispatch))
      ;; ref: https://emacsredux.com/blog/2020/12/10/essential-magit-file-commands/
      ```

      

  - [ ] Read: https://emacsredux.com/blog/2020/12/10/essential-magit-file-commands/

---

## Learning Emacs

- **`C-x C-e`** runs the command `cider-eval-last-expression`
- **`C-c C-k`** 	Compile current buffer. 
- **`M-x cider-repl-set-ns`** Switch to current CLJ file's namespace