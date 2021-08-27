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