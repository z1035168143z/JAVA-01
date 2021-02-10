package com.zrzhao.framework.beans;

import com.zrzhao.framework.beans.annotation.Inject;
import org.springframework.util.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * store beans definition
 * store beans instance
 *
 * @author zrzhao
 * @date 2021/2/6
 */
public abstract class AbstractBeanFactory implements BeanFactory {

    /** bean name definition map **/
    protected final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    /** bean instance **/
    private final Map<Class<?>, Object> beanInstanceMap = new ConcurrentHashMap<>(16);

    /** processor bean before bean init **/
    private final List<BeanPostProcessor> beanPostProcessors = new CopyOnWriteArrayList<>();

    /** load bean definition **/
    protected abstract void loadBeanDefinitions();

    /** start application context **/
    public void refresh() throws IllegalAccessException {

        // load bean
        this.loadBeanDefinitions();

    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        if (this.beanDefinitionMap.containsKey(beanName)) {
            throw new RuntimeException("bean definition is not unique");
        }
        this.beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) {
        if (beanInstanceMap.containsKey(requiredType)) {
            return (T) beanInstanceMap.get(requiredType);
        }

        if (!beanDefinitionMap.containsKey(name)) {
            throw new IllegalArgumentException("there is no bean named :" + name);
        }

        synchronized (beanInstanceMap) {
            if (beanInstanceMap.containsKey(requiredType)) {
                return (T) beanInstanceMap.get(requiredType);
            }
            BeanDefinition beanDefinition = beanDefinitionMap.get(name);
            try {
                T instance = doGetBean(beanDefinition);
                beanInstanceMap.put(requiredType, instance);
                return instance;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        throw new IllegalArgumentException("cannot create bean " + name);
    }

    private <T> T doGetBean(BeanDefinition beanDefinition) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Object target = null;
        if (noneArgsConstructor(beanDefinition)) {
            target = beanDefinition.getBeanClass().newInstance();
        }
        if (target == null) {
            Constructor<?>[] declaredConstructors = beanDefinition.getBeanClass().getDeclaredConstructors();
            for (Constructor<?> declaredConstructor : declaredConstructors) {
                if (matchConstructor(declaredConstructor.getParameterTypes(), beanDefinition.getParameterTypes())) {
                    target = declaredConstructor.newInstance(beanDefinition.getParameters());
                    break;
                }
            }
        }

        return (T) postProcessBeforeInitialization(beanDefinition, target);
    }

    private boolean noneArgsConstructor(BeanDefinition beanDefinition) {
        return beanDefinition.getParameterTypes() == null;
    }

    private boolean matchConstructor(Class<?>[] constructorParameterTypes, Class<?>[] beanDefinitionParameterTypes) {
        if (constructorParameterTypes == null) {
            return false;
        }
        if (constructorParameterTypes.length != beanDefinitionParameterTypes.length) {
            return false;
        }
        for (int i = 0; i < constructorParameterTypes.length; i++) {
            if (!constructorParameterTypes[i].equals(beanDefinitionParameterTypes[i])) {
                return false;
            }
        }
        return true;
    }

    private Object postProcessBeforeInitialization(BeanDefinition beanDefinition, Object target) throws IllegalAccessException, InstantiationException {
        this.handleDependent(beanDefinition, target);

        for (InstantiationAwareBeanPostProcessor bp : getInstantiationAware()) {
            Object result = bp.postProcessBeforeInstantiation(beanDefinition.getBeanClass(), target);
            if (result != null) {
                return result;
            }
        }
        return target;
    }


    /**
     * inject field
     **/
    private void handleDependent(BeanDefinition beanDefinition, Object target) throws IllegalAccessException {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Field[] declaredFields = beanClass.getDeclaredFields();
        for (Field field : declaredFields) {
            Inject inject = field.getAnnotation(Inject.class);
            if (inject == null) {
                continue;
            }
            if (!this.beanDefinitionMap.containsKey(inject.value())) {
                throw new RuntimeException("no such bean named " + inject.value());
            }
            field.setAccessible(true);
            Object injectObject = this.getBean(inject.value(), field.getType());
            field.set(target, injectObject);
        }
    }

    private List<InstantiationAwareBeanPostProcessor> getInstantiationAware() {
        List<InstantiationAwareBeanPostProcessor> instantiationAwareBeanPostProcessors = new ArrayList<>();
        for (BeanPostProcessor bp : this.beanPostProcessors) {
            if (bp instanceof InstantiationAwareBeanPostProcessor) {
                instantiationAwareBeanPostProcessors.add((InstantiationAwareBeanPostProcessor) bp);
            }
        }
        return instantiationAwareBeanPostProcessors;
    }

    public AbstractBeanFactory registerBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        Assert.notNull(beanPostProcessor, "");
        this.beanPostProcessors.add(beanPostProcessor);
        return this;
    }

}
