package learn.java.basics.io.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import lombok.extern.slf4j.Slf4j;

/**
 * SerializationExample.
 */
@Slf4j
public class SerializationExample {

    public static void main(String[] args) {
        //example();
        example2();
    }

    // JDK 序列化和反序列化简单示例
    private static void example()  {
        try {
            Person person = new Person(20, "Simon", "Xi'an");
            System.out.println(person);

            // 序列化
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream output = new ObjectOutputStream(buffer);
            output.writeObject(person);
            output.close();

            // 反序列化
            ByteArrayInputStream inputStream = new ByteArrayInputStream(buffer.toByteArray());
            ObjectInputStream input = new ObjectInputStream(inputStream);
            Person person2 = (Person) input.readObject();
            input.close();
            System.out.println(person2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void example2() {
        try {
            Person person = new Person(20, "Simon", "Xi'an");
            System.out.println(person);

            Kryo kryo = new Kryo();
            kryo.register(Person.class);

            // 序列化
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            Output output = new Output(buffer);
            kryo.writeObject(output, person);
            output.close();

            // 反序列化
            Input input = new Input(new ByteArrayInputStream(buffer.toByteArray()));
            Person person2 = kryo.readObject(input, Person.class);
            input.close();
            System.out.println(person2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static class Person implements Serializable {

        private int age;
        private String name;
        private transient String address;

        public Person() {
        }

        public Person(int age, String name, String address) {
            this.age = age;
            this.name = name;
            this.address = address;
            System.out.println("constructor...");
        }

        @Override
        public String toString() {
            return "Person{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    ", address='" + address + '\'' +
                    '}';
        }
    }
}