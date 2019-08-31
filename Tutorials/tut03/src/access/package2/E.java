package access.package2;

import access.package1.A;

public class E extends A {

    public void methodE() {
        // Subclasses in a different package can access public and protected
        // members.
        System.out.println("varPub: " + varPub);
        System.out.println("varPro: " + varPro);

        // Even if it's another instance of E
        E e = new E();
        System.out.println("varPub: " + e.varPub);
        System.out.println("varPro: " + e.varPro);

        A a = new A();
        System.out.println("varPub: " + a.varPub);
        // TODO Does the following line compile if uncommented?
//        System.out.println("varPro: " + a.varPro);
        // ANSWER No. This might be surprising as we were able to access a
        // protected member of this class above. In general, if an object has a
        // protected member that it has inherited from its superclass, then it
        // can only be accessed by methods in that object, not the class. See
        // the Java language specification for more detail
        // (https://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html)
    }

    // TODO What happens if we override the protected method here?
    // ANSWER Any class in access.package2 can now call this method.
    @Override
    protected void protectedMethod() {

    }
}
