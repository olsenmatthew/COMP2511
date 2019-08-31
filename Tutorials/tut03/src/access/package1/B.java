package access.package1;

/**
 * @author Aarthi
 * A class defining variables with different access modifiers
 */
public class B extends A {

    public void methodB() {
        // Within the sub-class, have access to variables with default, public
        // and protected scope
        System.out.println("var: " + var);
        System.out.println("varPub: " + varPub);
        System.out.println("varPro: " + varPro);
        // The line below does not compile because
        // sub-classes do not have access to private variables in super class
//        System.out.println("varPriv: " + varPriv);

        B b = new B();
        System.out.println("var: " + b.var);
        System.out.println("varPub: " + b.varPub);
        System.out.println("varPro: " + b.varPro);

        A a = b;
        // TODO Does it make a difference that we're accessing these fields on
        // a variable of type A?
        System.out.println("var: " + a.var);
        System.out.println("varPub: " + a.varPub);
        System.out.println("varPro: " + a.varPro);
        // ANSWER No, this class is in the same package, it doesn't in fact
        // matter that it is a subclass of A.
    }

    // TODO Which of these two methods will work if uncommented (note the
    // different access modifier)?

    // ANSWER Only the first method works. Java will let us override methods
    // with less restrictive access modifiers, but not more restrictive access
    // modifiers. This is necessary to satisfy the Liskov Substitution Principle.
    @Override
    public void protectedMethod() {

    }

//    @Override
//    void protectedMethod() {
//
//    }

}
