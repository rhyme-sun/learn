package learn.foo;

import learn.caseutil.CaseUtils;

/**
 * Foo.
 */
public class Foo {

    public static void main(String[] args) {
        Foo foo = new Foo();
        foo.foo();
    }

    public void foo() {
        String foo = "Foo";
        System.out.println(CaseUtils.upper(foo));
    }
}