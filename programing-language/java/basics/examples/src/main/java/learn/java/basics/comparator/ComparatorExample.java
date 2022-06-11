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

        createComparator();
    }

    private static void createComparator() {
        Comparator<Student> comparator = Comparator.comparing((Student s) -> s.age).thenComparing(s -> s.name);
        final List<Student> students = generateRandomStudent();
        log.info("Students: {}", students);
        Collections.sort(students);
        Collections.sort(students, comparator);
        log.info("Ordered students: {}", students);
    }

    private static List<Student> generateRandomStudent() {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int age = (int) (Math.random() * 10) + 10;
            String name = String.valueOf((char) ('a' + (int) (Math.random() * 10)));
            students.add(new Student(name, age));
        }
        return students;
    }

    static class Student implements Comparable<Student> {

        String name;
        int age;

        public Student(int age) {
            this.age = age;
        }

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public int compareTo(Student o) {
            return this.age - o.age;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
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
