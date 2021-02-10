package com.zrzhao.framework.beans;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zrzhao
 * @date 2021/2/6
 */
public class YmlBeanFactory extends AbstractBeanFactory {

    @Override
    protected void loadBeanDefinitions() {
        Yaml yaml = new Yaml();
        //文件路径是相对类目录(src/main/java)的相对路径
        InputStream in = YmlBeanFactory.class.getClassLoader().getResourceAsStream("application.yml");//或者app.yaml
        Map<String, Object> map = yaml.loadAs(in, Map.class);
        LinkedHashMap<String, LinkedHashMap> linkedHashMap = (LinkedHashMap<String, LinkedHashMap>) map.getOrDefault("beans", Collections.emptyMap());
        for (Map.Entry<String, LinkedHashMap> mapEntry : linkedHashMap.entrySet()) {
            String beanName = String.valueOf(mapEntry.getValue().get("beanName"));
            try {
                BeanDefinition beanClass = new BeanDefinition(beanName, Thread.currentThread().getContextClassLoader().loadClass(String.valueOf(mapEntry.getValue().get("beanClass"))));
                List parameters = (List) mapEntry.getValue().get("parameters");
                if (parameters != null && !parameters.isEmpty()) {
                    beanClass.setParameters(parameters.toArray());
                    Class<?>[] classes = new Class[parameters.size()];
                    int index = 0;
                    for (Object parameter : parameters) {
                        classes[index++] = parameter.getClass();
                    }
                    beanClass.setParameterTypes(classes);
                }
                this.registerBeanDefinition(beanName, beanClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
