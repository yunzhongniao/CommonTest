package org.yunzhong.CommonTest.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author yunzhong
 *
 */
class CommonBuilderTest {

    public static class Student {
        private String id;
        private String name;
        private String num;
        private String sex;
        private Integer age;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

    }

    @Test
    void testWithConsumer1OfTP1P1() {
        Student s = CommonBuilder.of(Student::new)
                .with(Student::setName, "学生名称")
                .with(Student::setAge, 12)
                .build();
        assertEquals("学生名称", s.getName());
        assertEquals(12, s.getAge());
    }

    @Test
    void testWithF() {
        Student s1 = CommonBuilder.of(Student::new)
                .with(Student::setName, "学生名称")
                .with(Student::setAge, 12)
                .with(Student::setNum, "210")
                .build();
        Student s = CommonBuilder.of(Student::new)
                .withF(Student::setName, () -> "学生名称".toString())
                .withF(Student::setNum, () -> s1.getNum())
                .build();
        assertEquals(s1.getName(), s.getName());
        assertEquals(s1.getNum(), s.getNum());
    }

}
