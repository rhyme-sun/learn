package learn.bar;

import learn.caseutil.CaseUtils;

/**
 * Bar.
 */
public class Bar {

    public static void main(String[] args) {
        Bar bar = new Bar();
        bar.bar();
    }

    public void bar() {
        String bar = "Bar";
        System.out.println(CaseUtils.lower(bar));
    }
}