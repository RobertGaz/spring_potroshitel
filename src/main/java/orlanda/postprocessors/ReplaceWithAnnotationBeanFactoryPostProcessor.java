//package orlanda.postprocessors;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanDefinition;
//import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
//import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
//import orlanda.annotations.ReplaceWith;
//
//public class ReplaceWithAnnotationBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
//    @Override
//    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//        String[] names = beanFactory.getBeanDefinitionNames();
//        for (String name : names) {
//            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(name);
//            String className = beanDefinition.getBeanClassName();
//            try {
//                Class<?> originalClass = Class.forName(className);
//                ReplaceWith annotation = originalClass.getAnnotation(ReplaceWith.class);
//                if (annotation != null) {
//                    Class<?> otherClass = annotation.otherClass();
//                    beanDefinition.setBeanClassName(otherClass.getName());
//                }
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//    }
//}
