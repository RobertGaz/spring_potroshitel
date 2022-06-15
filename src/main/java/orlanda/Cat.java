package orlanda;

import javax.annotation.PostConstruct;

@Profiling
public class Cat implements Animal {
    @InjectRandomInt
    private int age;

    private String name;

    // 1 фаза конструктора
    public Cat() {
        System.out.println("- constructor -");
        System.out.println("age value: " + age);
    }

    // 2 фаза конструктора
    @PostConstruct
    public void initialize() {
        System.out.println("- initialize -");
        System.out.println("age value: " + age);
    }


    @Override
    @PostProxy //3 фаза
    public void sayHello() {
        System.out.println("- sayHello -");
        System.out.println("Meow!");
        System.out.println("My age is " + age);
    }
}
