package orlanda.postprocessors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import orlanda.annotations.Profiling;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

public class ProfilingAnnotationBeanPostProcessor implements BeanPostProcessor {
    private Set<String> names = new HashSet<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Profiling.class)) {
            names.add(beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (names.contains(beanName)) {
            Object proxyBean = Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    long start = System.nanoTime();
                    Object result = method.invoke(bean, args);
                    long end = System.nanoTime();
                    System.out.println(end-start + " ns");
                    return result;
                }
            });

            return proxyBean;
        }
        return bean;
    }
}
