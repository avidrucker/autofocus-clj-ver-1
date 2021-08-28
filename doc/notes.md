## Notes from Study Session

- Most of the time, for small scale projects, you do not need to worry about performance

- Compiler driven development vs REPL driven development

  - Spending time on the data model is going to give you high ROI

  - Pipeline: I/O at Entry point --> <kbd>Request</kbd> --> Data

    > Eg. Actor model, message model, queue model

  - I/O examples: Databases, web service calls, user input

  - Functions should look like this:

    ```clojure
    (fn [req current-system-state]
        new-system-state)
    ```

  - Also called "Data Oriented Programming"

  - System state will probably be a map in an atom

    - Example: Each user has a to-do list

      > Avi: I'd love to have a personal to-do list and a work to-do list

    - Note: Getting the time is **not** pure

    - If you use atoms, you would swap it.

    - If you used a database, you would transact it

      - Datomic transactions can be written as if they were pure, because Datomic databases are time-stamped & "immutable"

    - Using list/vector indexes to access collection/sequence items is OK, you may need to re-index as needed

    - Reading is relatively straightforward

    - Writing/Updating can be inefficient/slow

    - Map storage may be cleaner as well as faster than list/vector, especially when ID keys are the main look-up values ("to index by ID")

      - "partition by index" to keep data index
      - "get" or "get-in" are the main look-up functions to access map data

## Design Decision 0 (2021/08/14)

- Try using an atom to hold list state b/c I heard that holding app state is one of the use-cases for atoms

## Design Decision 1 (2021/08/15)
- No mutable state (no atoms) 😅
- Only pure functions ✨

## Design Decision 2 (2021/08/27)

UPDATE: Wait... nevermind! I can simply use `map-indexed`
UPDATE 2: Will use `keep-indexed` instead b/c (for now) I don't need the nils.

Initial Thoughts:
~~The "index-of-first-markable" function will be much easier to implement I believe if items store their own index in the list. That said, adding indecies to list items can also create more issues down the line, if they ever need to be updated, for example... Updating list item indecies may be beneficial, for example, in circumstances where the entire list is cleared out, lists are saved in and out of memory, or other "global" list modifications/(de)serializations. I believe I can avoid most of the mutations & place-driven development ("slots") this time, because Clojure makes it harder to mutate, and I have the intention to avoid the "slots".~~