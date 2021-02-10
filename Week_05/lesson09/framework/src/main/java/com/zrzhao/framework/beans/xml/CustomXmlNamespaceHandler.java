package com.zrzhao.framework.beans.xml;

import com.zrzhao.human.Student;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author zrzhao
 * @date 2021/2/6
 */
public class CustomXmlNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("student", new CustomDefinitionParser(Student.class));
    }
}
