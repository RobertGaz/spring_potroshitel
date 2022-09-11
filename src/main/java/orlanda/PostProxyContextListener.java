package orlanda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import orlanda.annotations.PostProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class PostProxyContextListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        String[] beanNames = context.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            // достаем beanDefinition, чтобы из него вытащить className оригинального класса,
            // ведь это третья фаза и тут уже может прийти прокси
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            String className = beanDefinition.getBeanClassName();

            try {
                Class<?> beanOriginalClass = Class.forName(className);
                for (Method method : beanOriginalClass.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(PostProxy.class)) {

                        // method - это метод оригинального класса, а бин у нас прокси класса
                        // и это два разных класса
                        // поэтому найдем у объекта прокси класса такой же метод и вызовем его

                        String methodName = method.getName();
                        Object bean = context.getBean(beanName);
                        Method currentMethod = bean.getClass().getDeclaredMethod(methodName, method.getParameterTypes());
                        currentMethod.invoke(bean);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
}
