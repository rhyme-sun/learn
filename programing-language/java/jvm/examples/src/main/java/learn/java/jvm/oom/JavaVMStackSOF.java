package learn.java.jvm.oom;

        /**
         * JavaVMStackSOF.
         * -Xss128k
         */
        public class JavaVMStackSOF {

    private int stackLength;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        JavaVMStackSOF sof = new JavaVMStackSOF();
        try {
            sof.stackLeak();
        } catch (Throwable e) {
            System.out.println("StackLength: " + sof.stackLength);
            throw e;
        }
    }
}
