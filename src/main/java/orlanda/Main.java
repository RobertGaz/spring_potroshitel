package orlanda;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application_context.xml");
        Animal bean = context.getBean(Animal.class);

        bean.sayHello();
        context.close();
    }
}