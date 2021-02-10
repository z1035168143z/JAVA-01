package com.zrzhao.framework.beans;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zrzhao
 * @date 2021/2/6
 */
public class XmlBeanFactory extends AbstractBeanFactory {

    private static final String XML_PATH = "application-custombeans.xml";

    @Override
    protected void loadBeanDefinitions() {
        Document document = loadDocument();
        NodeList beans = document.getElementsByTagName("bean");
        if (beans != null && beans.getLength() > 0) {
            for (int i = 0; i < beans.getLength(); i++) {
                Node item = beans.item(i);

                String beanName = item.getAttributes().getNamedItem("name").getNodeValue();
                try {
                    String className = item.getAttributes().getNamedItem("class").getNodeValue();
                    BeanDefinition beanDefinition = new BeanDefinition(beanName, Thread.currentThread().getContextClassLoader().loadClass(className));
                    NodeList childNodes = item.getChildNodes();
                    if (childNodes.getLength() > 0) {
                        List<Object> parameters = new ArrayList<>(childNodes.getLength());
                        for (int j = 0; j < childNodes.getLength(); j++) {
                            Node child = childNodes.item(j);
                            if ("constructor-arg".equals(child.getNodeName())) {
                                parameters.add(child.getAttributes().getNamedItem("value").getNodeValue());
                            }
                        }
                        Class<?>[] classes = new Class[parameters.size()];
                        int index = 0;
                        for (Object parameter : parameters) {
                            classes[index++] = parameter.getClass();
                        }
                        beanDefinition.setParameters(parameters.toArray());
                        beanDefinition.setParameterTypes(classes);
                    }
                    this.registerBeanDefinition(beanName, beanDefinition);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Document loadDocument() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(XmlBeanFactory.class.getClassLoader().getResourceAsStream(XML_PATH));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
