package com.zrzhao.autoconfigure;

import com.zrzhao.autoconfigure.entity.ISchool;
import com.zrzhao.autoconfigure.entity.Klass;
import com.zrzhao.autoconfigure.entity.School;
import com.zrzhao.autoconfigure.entity.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@ConditionalOnProperty(prefix = "custom.autoconfiguration", name = "enabled")
@AutoConfigureBefore(Student.class)
public class CustomAutoConfiguration {

    @Bean("student100")
    public Student getStudent100() {
        Student student = new Student();
        student.setId(100);
        student.setName("student type in");
        return student;
    }

    @Bean
    public Klass getKlass() {
        Klass klass = new Klass();

        klass.setStudents(Arrays.asList(new Student(1), new Student(2)));

        return klass;
    }

    @Bean
    public ISchool getSchool() {
        return new School();
    }

}
