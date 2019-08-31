# Tutorial 5

## Interfaces, abstract classes and inheritance

Compare interfaces, abstract classes and concrete classes.

* Which of them can be constructed?

  > * Interfaces and abstract classes - Can't be constructed. To use the blueprint analogy, both are like incomplete blueprints, so they don't contain enough information to build anything.
  > * Concrete classes - Can be constructed. A concrete class, or just a class as it would usually be described, is analogous to a complete blueprint, so can be used to build something.

* Which can have abstract methods?

  > * Abstract classes - Can have abstract methods. They are marked with the `abstract` qualifier and do not have method bodies. Classes can still be abstract without having abstract methods.
  > * Interfaces - Can have abstract methods. In an interface, the abstract qualifier is not necessary, so is usually not included.
  > * Concrete classes - Can't have abstract methods.

* Which can have concrete methods?

  > * Abstract and concrete classes - Yes.
  > * Interfaces - Interfaces can have methods annotated with `default`. These methods do have bodies and become members of classes that implement the interface. Note that the keyword `default` is different to the "default" access modifier.

* Which can have fields?

  > * Abstract and concrete classes - Can have fields.
  > * Interfaces - Can't have fields. Note that this makes `default` methods less useful than methods in classes.

* How do they relate to each other in terms of inheritance and realisation?

  > * Abstract and concrete classes - Inherit from exactly one other class (either abstract or concrete) and realize (or implement) 0 or more interfaces. Inheritance is specified via the `extends` keyword, realisation by the `implements` keyword.

Consider a game where different types of characters move around on a grid fighting each other. When one character moves into the square occupied by another they attack that character and inflict damage based on random chance (e.g. a dice roll).

* A king can move one square in any direction (including diagonally), and always causes 8 points of damage when attacking.
* A knight can move like a knight in chess (in an L shape), and has a 1 in 2 chance of inflicting 10 points of damage when attacking.
* A queen can move to any square in the same column, row or diagonal as she is currently on, and has a 1 in 3 chance of inflicting 12 points of damage or a 2 out of 3 chance of inflicting 6 points of damage.
* A troll can only move up, down, left or right, and has a 1 in 6 chance of inflicting 20 points of damage.

The classes in the packages `unsw.characters.inheritance` and `unsw.characters.strategy` show two different approaches to representing the characters in such a game.

* What are the key differences between the two?

    > While both approaches use polymorphism, the one in the `inheritance` package uses inheritance while the one in `strategy` uses the strategy pattern. In the former, the `Character` class is abstract, whereas in the latter it is concrete. To construct a queen in the inheritance approach you would use `new Queen(x, y)`. In the strategy pattern approach, a queen would be constructed with `new Character(x, y, new QueenAttack(), new QueenMovement())`.

* How does this example differ from the movie rental example from last week?

    > In the movie rental example, movies had a property that wasn't fixed (e.g. a movie could change from a new release to regular). This made an inheritance based approach inappropriate. For this example, there is no requirement that characters change their type, so the inheritance based solution doesn't have that issue.

* What are the strengths and weaknesses of each approach?

    > The inheritance based solution is noticeably simpler, with fewer classes and the various different behaviours relating to characters in their respective classes. The strategy pattern based solution is, however, more flexible. For example, supporting dynamic changes in either movement or attack behaviour could be trivially achieved by adding `setAttack(...)` and `setMovement(...)` methods to `Character`. Similarly, see the question below about adding a new character that shares some behaviours with other characters.
    >
    > One might argue that the strategy pattern approach is an example of applying the design principle "encapsulate what varies" needlessly. In *Refactoring: Improving the design of existing code*, Martin Fowler talks about the code smell Speculative Generality, where code is written to handle things that aren't actually required. Given the, somewhat vague, requirements for this example, what is gained from using the strategy pattern isn't needed. However, this assumes the requirements are fixed and won't change, which is rarely the case.

* Suppose you wanted to create a new elf character that moves like a king, but attacks like a queen. How would you implement such a character with both approaches?

    > In the strategy pattern approach, this would just be `new Character(x, y, new QueenAttack(), new KingMovement())`. In the inheritance based version, this is unfortunately not possible without repeating code. An `Elf` class could extend `Queen` to get its attack behaviour, or `King` to get its movement behaviour, but it can't extend both.

* Thinking more generally, is one of them always preferable to the other?

    > This can depend on a number of factors. There are certain things that the inheritance based approach cannot do, as detailed above, but if the requirements do not change the simplicity be preferred. It is also worth considering the fact that refactoring from inheritance to the strategy pattern is a straightforward mechanical process, so starting with an inheritance based approach and only changing it when necessary may be appropriate, even in a context where requirements can change.

## Code review

Your tutor will pick two students to do code review of last week's lab exercise. Things to consider when reviewing the code:

* What state transitions are available?
* How does the solution prevent transitions that shouldn't occur?

> See the lab solution from last week for one example. It uses a runtime error to prevent some of transitions that shouldn't occur, but this has disadvantages, as detailed in the answer to the lab questions. Another approach is to have transitions that shouldn't occur silently ignored. For example, trying to change a movie from classic to regular just wouldn't change the state at all. While this prevents runtime errors, it can give surprising results to clients of the movie class. Overall, the state pattern is an imperfect solution for ensuring that a movie's classification only changes in expected ways, but it can at least offer a degree of control when compared to the strategy pattern.
