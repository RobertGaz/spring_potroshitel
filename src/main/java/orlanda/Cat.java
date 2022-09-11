package orlanda;

import orlanda.annotations.InjectRandomInt;
import orlanda.annotations.PostProxy;
import orlanda.annotations.Profiling;
import orlanda.annotations.ReplaceWith;

import javax.annotation.PostConstruct;

@Profiling
@ReplaceWith(otherClass = BritishShorthairCat.class)
public class Cat implements Animal {
    @InjectRandomInt
    private int age;

    private String name;

    // 1 фаза конструктора
    public Cat() {
        System.out.println("- 1 constructor -");
        System.out.println("age value: " + age);
    }

    // 2 фаза конструктора
    @PostConstruct
    public void initialize() {
        System.out.println("- 2 initialize -");
        System.out.println("age value: " + age);
    }


    @Override
    @PostProxy //3 фаза
    public void sayHello() {
        System.out.println("- 3 post proxy -");
        System.out.println("Meow!");
    }
}
