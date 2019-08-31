# Tutorial 2

## Quick questions

Use the `Shape` and `Rectangle` classes to help answer the following question.

1. What is the difference between `super` and `this`?
    > `super` is used in Java to access methods and fields of the base class, while `this` is used to access methods and fields in the current class.
2. What about `super(...)` and `this(...)`?
    > Both `super(...)` and `this(...)` are calls to constructors. `super(...)` refers to the constructor of the base class and it must be the first line in the constructor of the derived class. `this(...)` refers to the constructor of the current class. A constructor can only invoke `super(...)` or `this(...)` but never both.
3. What is method overriding?
    > Method overriding is a technique used in inheritance, where a sub-class can override a method in the parent class to provide its own specific implementation of the method
4. What are static fields and methods?
    > Static fields and methods are shared amongst all instances of a class; i.e. they "belong" to the class not to a particular object. A static field is essentially a global variable attached to the class. A static method does not require an instance of the object to be invoked.
5. Can you override a static method?
    > Static methods cannot be overridden. As they cannot be invoked dynamically, there is nothing to gain from be able to override them.
6. What is output by executing `A.f()` in the following?

    ```java
    public class A {
        public static void f() {
            C c = new C();
            c.speak();
            B b = c;
            b.speak();
            b = new B();
            b.speak();
            c.speak();
        }
    }


    public class B {
        public void speak() {
            System.out.println("moo");
        }
    }


    public class C extends B {
        public void speak() {
            System.out.println("quack");
        }
    }
    ```

    > ```
    > quack
    > quack
    > moo
    > quack
    > ```

7. What is output by executing `A.f()` in the following?

    ```java
    public class A {
        public static void f() {
            B b1 = new B();
            B b2 = new B();
            b1.incX();
            b2.incY();
            System.out.println(b1.getX() + " " + b1.getY());
            System.out.println(b2.getX() + " " + b2.getY());
        }
    }

    public class B {
        private int x;
        private static int y;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void incX() {
            x++;
        }

        public void incY() {
            y++;
        }
    }
    ```

    > ```
    > 1 1
    > 0 1
    > ```

## Java Exercise

Create an `Employee` class which has private fields for an employee's name and salary and appropriate getters, setters, and constructors. Document the class with Javadoc.

> See code

* How many constructors should the class have? What arguments should they take?

    > Only one. If we don't define a constructor Java automatically generates one that takes no arguments. It makes little sense to have an employee with no name or salary, so the constructor should take the name and salary as arguments.

Create a `Manager` class that is a subclass of `Employee` and has a field for the manager's hire date.

> See code

* What constructors are appropriate?

    > Because we are inheriting from Employee, Java forces us to write a constructor that calls `super(...)`, so we have to write at least one. In this case, it makes sense to have a constructor that takes the name, salary and hire date, but you could also argue there should be one that just takes the name and salary and sets the hire date to the current day. It depends on context of how the class will be used whether you want the former, the latter, or both constructors.

* Is appropriate to have a getter for the hire date? What about a setter?

    > One can assume that if the hire date is stored it is information that will be needed at some point, so a getter is necessary. However, unlike the name or salary, it is unlikely that the hire date will be updated, so a setter would only serve to break that reasonable assumption about the class.

Override the `toString()` method inherited from `Object` in both classes.

> See code

* What should the result of `toString()` contain?
    > The [documentation](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#toString--) for the `toString()` method states that it should return a string that "textually represents" the object. In this case, it should contain the name, salary and hire date (in the case of `Manager`), but also the runtime class of the object.
* Does the method in `Manager` call the one in `Employee`?
    > In order to satisfy the above requirement and not introduce unnecessary repetition, the superclass method must be called.

Override `equals(...)` in both classes.

> See code

* Why does the equals method take an `Object` as an argument?
    > By design, Java lets you compare any two objects of any type for equality. As such, it is necessary for the equals method to take an `Object` as argument, perform runtime type checks, and do any casting as necessary.
* Should an `Employee` ever be equal to a `Manager`?
    > No, at least not in terms of runtime class. If one object was constructed as an `Employee` and another as a `Manager`, they should not be considered equal, even if they both happen to have the same name and salary.
* Does the method satisfy [the documentation](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#equals-java.lang.Object-) for `equals(...)`?
    > The one in the code does. The thing to watch out for is the comparing of the runtime class for equality. Failure to do that can result in an `equals(...)` method that is not symmetric. Incorrectly defined `equals(...)` methods are a [very common problem](https://dl.acm.org/citation.cfm?id=1463800) in Java code bases.
