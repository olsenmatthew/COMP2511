# Tutorial 4

## Design principles

In the `unsw.training` package there is some skeleton code for a training system. In an organisation, every employee must attend a whole day training seminar run by a qualified trainer. Each trainer is running multiple seminars with no more than 10 attendees per seminar. In the `TrainingSystem` class there is a method to book a seminar for an employee given the dates on which they are available. This method violates the principle of least knowledge (Law of Demeter).

* How and why does it violate this principle?

    > The `TrainingSystem` class extracts instances of `Seminar` from instances of `Trainer` and calls the methods of `Seminar`. Furthermore it extracts the start date from instances of `Seminar` and calls its methods. More informally, the `TrainingSystem` class is interacting with classes other than its "friends".

* In violating this principle, what other properties of this design are not desirable?

    > * The design is needlessly tightly coupled as `TrainingSystem` is dependent on both `Trainer` and `Seminar`.
    > * `TrainingSystem` suffers from low cohesion as any change to the system requires a change to this class.
    > * The `Seminar` class has no control over the number attendees. It relies on `TrainingSystem` to ensure there are never more than 10. This makes `Seminar` hard to re-use as any future client has to ensure they don't exceed the maximum of 10 attendees. This is an example of poor encapsulation.

* Refactor the code so that the principle is no longer violated. How has this affected other properties of the design?

    > See code.
    >
    > * The design is no longer tightly coupled. `TrainingSystem` no longer has any knowledge of `Seminar`.
    > * Each of the classes now has a clear purpose in booking a training seminar, thus improving cohesion.
    > * The `Seminar` class itself is now responsible for ensuring that the number of attendees does not exceed 10. This is an example of a class maintaining its invariant. We'll come back to that when discussing programming by contract.

* More generally, are getters essentially a means of violating the principle of least knowledge? Does this make using getters bad design?

    > Getters that return an object (as opposed to a primitive) likely only serve the purpose of letting clients invoke methods on that object, so a valid point can be made that getters can only be used as a means for violating the principle of least knowledge. A counter argument is that getters make classes more reusable. A client may need to do something with an object for which it has no method. In that case, getters can allow the client use the class in a way that was not originally foreseen, even if it does violate the principle of least knowledge.
    >
    > Another way in which a getter can be an example of bad design is in `Seminar` above. By having a `getAttendees()` method implemented as a simple getter, any client is able to add additional attendees to the list of attendees, potentially exceeding the maximum of 10. Unfortunately, Java does not offer any good solutions to this problem. Either `getAttendees()` has to create a copy of the list, or it can use `Collections.unmodifiableList(...)` to wrap the list up in a class that prevents any modification to the list. The former solution is inefficient as it needlessly copies data. The latter can be surprising to the client as the returned list still has an `add(...)` method, but it causes a runtime error every time it is used. Other languages resolve this problem by having proper immutable or read-only lists.

Look at the `OnlineSeminar` class.

* What design principle does it violate? Give an informal justification why.

    > This class violates the Liskov Substitution Principle. Specifically a `Seminar` is defined as having a list of attendees, but `OnlineSeminar` does not require this. A client interacting with a `Seminar` would expect the seminar to be booked like any other. This is an example of classes having an IS-A relationship informally, but not a valid inheritance relationship when taking into account what the classes actually do and represent.

## Refactoring

Consider the movie rental example from lectures (in the `unsw.movies` package).

* What issues are present in the `statement()` method of the `Customer` class?

    > * The `statement()` method is overly long. It does far too much. Many of the things it does should be done by other classes.
    > * The principle of least knowledge is being violated as instance of `Movie` are being extracted from instances of `Rental`.
    > * The design is not easily extended. For example, to add an additional classification involves changes to both the `Movie` class and the `Customer` class.

* Refactor the code to resolve these issues. Try to do the refactoring by small steps, each one preserving the behaviour of the example.

    > See code. The git history shows each small refactoring step.

## Code review

Your tutor will pick two students to do code review of last week's lab exercise. Things to consider when reviewing the code:

* Does the solution violate the principle of least knowledge?
* If it does violate the principle, can you justify why?
* How does the solution ensure students enrol in a session for a course?

> See the solution to last week's lab. In that solution, note that the principle of least knowledge is still violated in a call to `assignMark()` on an `Enrolment` instance. However, this violation is more easily justified as any alternative is going to be more complicated and less easy to use. For example, having an `assignMark(Student)` method in `CourseOffering` would work, and no longer violate the principle, but that assumes that the student is enrolled in that offering, which might not be the case. Invoking methods directly on the `Enrolment` is not only simpler to implement, but requiring an `Enrolment` instance to assign a mark statically guarantees that marks can't be assigned for students who are not enrolled in a course. This is an example where unconditionally conforming to a principle can lead to a bad design.
