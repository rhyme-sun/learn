package learn.java.basics.comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * ComparatorExample.
 */
@Slf4j
public class ComparatorExample {

    public static void main(String[] args) {
        final List<Student> students = generateRandomStudent();
        log.info("Students: {}", students);
        Collections.sort(students);
        Collections.sort(students, Collections.reverseOrder());

        Collections.sort(students, new StudentComparator());
        Collections.sort(students, Collections.reverseOrder(new StudentComparator()));
        log.info("Ordered students: {}", students);
    }

    private static List<Student> generateRandomStudent() {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int age = (int) (Math.random() * 10) + 10;
            students.add(new Student(age));
        }
        return students;
    }

    static class Student implements Comparable<Student> {

        private int age;

        public Student(int age) {
            this.age = age;
        }

        @Override
        public int compareTo(Student o) {
            return this.age - o.age;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "age=" + age +
                    '}';
        }
    }

    static class StudentComparator implements Comparator<Student> {

        @Override
        public int compare(Student o1, Student o2) {
            return o1.age - o2.age;
        }
    }
}
