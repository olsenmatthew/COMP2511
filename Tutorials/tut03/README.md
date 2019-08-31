# Tutorial 3

## Project Partners

Under the guidance of your tutor, arrange yourselves into pairs for the project. Your tutor can help you find a partner if you don't already have one.

You will need to register a group on WebCMS with both you and your partner in it by the end of this week, so best do it now. Pay careful attention to the restrictions on the name for your group.

## Access Modifiers

1. What are access modifiers? Why do we use them?
    > Access modifiers let us limit access to members of a class. This can give us greater safety guarantees of our programs, by limiting how state can be modified or observed.

2. How does access control in Java differ from access control in Python?
    > In Java, access control is built into the language and enforced by the compiler. Code won't compile if access limitations are violated. In Python, access control is handled via a naming convention (e.g. `__var` for a private field). It is not checked by a compiler, but the name will be mangled into `_ClassName_var`.

3. Look at the code in the `access` package and try to answer the questions marked `TODO`
    > See code.

## Lists

Refactor the code in `lists.ListExample` to make better use of Java `List`s

> See code.

## Code Review

Your tutor will pick two students to do code review of last week's lab exercise. Things to consider when reviewing the code:

* Is the style consisten with Java style conventions?
* What about the Javadoc?
* Do the methods and constructors take appropriate arguments?
* Are the implementations of `equals()` "correct"?

> Watch out for implementations of `equals(...)` using `instanceof`. They usually violate reflexivity.
