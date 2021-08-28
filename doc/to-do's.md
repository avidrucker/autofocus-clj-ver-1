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

- [x] Enable the adding of items to a list

- [x] Test the adding of items to a list

- [x] Write your first unit test which uses "auto-marking" function `mark-first-markable!` [functionality] [visible]

    - [x] Implement `is-auto-markable-list?`
    - [x] Implement `get-first-markable-index`
    - [x] Implement `mark-first-markable!`

- [ ] Enable "focusing" on items which marks them "complete"

- [ ] Create review strings from current list state

    - [ ] Ask: How can this be made interruptable?

- [x] Break code into three namespaces:

    - [x] IO (printing, getting user input)
    - [x] demos
    - [x] domain logic

- [ ] Enable user to input text for a new to-do item manually in the console [IO]

- [x] Brainstorm clearer naming conventions for to-do item statuses: "unmarked", "dotted", and "completed" [readability]

    > unmarked, **clean**, untouched, ~~blank~~, 
    >
    > dotted, **marked**, ~~ready~~, circled
    >
    > complete, completed, **done**

- [x] Update statuses to reflect clearer naming conventions

## Won't Do ... Yet

- ~~Update code to enable automatic assignment *and* incrementing of to-do list item IDs~~ [temporary] [invisible]