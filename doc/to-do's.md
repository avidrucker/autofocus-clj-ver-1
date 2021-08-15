# To-Do's

- [x] Create one example item manually
- [x] Create a mutable list
- [x] Enable adding items to list
- [x] Enable conversion of list to string where an empty list prints out as `list is empty`
- [x] Enable clearing of list to deal with persistent state of atoms
- [x] Extract demo code to isolated demo fuction 

- [ ] Create demo where three items are added to the list, and the first markable item is marked [functionality] [temporary] [visible]
    - [x] Squash bug where it appears that state is not being correct mutated
        - [x] Convert as many functions to pure functions as possible to reduce bug-friendly habitat [bugfix]
            - [x] Remove global state from demo1
            - [x] Remove global state from demo2
        - [x] Remove mutable state to reduce bug-friendly habitat [bugfix]
    - [x] Clean up render output to make individual items easier to read
- [ ] Enable user to input text for a new to-do item manually in the console [IO]
- [ ] Brainstorm clearer naming conventions for to-do item statuses: "unmarked", "dotted", and "completed" [readability]
- [ ] Update code to enable automatic assignment *and* incrementing of to-do list item IDs [temporary] [invisible]