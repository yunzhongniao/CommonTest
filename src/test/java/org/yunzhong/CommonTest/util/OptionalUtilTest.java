package org.yunzhong.CommonTest.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("optional单元测试")
public class OptionalUtilTest {

    @BeforeAll
    public static void beforeAll() {
        System.out.println("first of all.");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("after of all.");
    }

    @Nested
    @DisplayName("of方法单元测试")
    class OfTest {

        @Test
        @DisplayName("of方法正常")
        void testOf() {
            OptionalUtil<Double> value = OptionalUtil.of(Double.valueOf(12.2));
            assertNotNull(value);
            assertEquals(12.2, value.get(), 1);
        }

        @Test
        @DisplayName("of方法null抛出异常")
        void testOfWithNull() {
            NullPointerException exception = assertThrows(NullPointerException.class, () -> OptionalUtil.of(null));
            assertNotNull(exception);
        }
    }

    @Nested
    @DisplayName("ofNullable方法单元测试")
    class OfNullableTest {

        @Test
        @DisplayName("ofNullable方法正常")
        void testOf() {
            OptionalUtil<Double> value = OptionalUtil.ofNullable(Double.valueOf(12.2));
            assertNotNull(value);
            assertEquals(12.2, value.get(), 1);
        }

        @Test
        @DisplayName("ofNullable方法null,get返回null")
        void testOfWithNull() {
            Object value = OptionalUtil.ofNullable(null).get();
            assertNull(value);
        }
    }

    @Nested
    @DisplayName("getProperty方法单元测试")
    class GetPropertyTest {
        class TestModel {
            private String id;
            private String name;
            private TestModel model;

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

            public TestModel getModel() {
                return model;
            }

            public void setModel(TestModel model) {
                this.model = model;
            }

        }

        @Test
        @DisplayName("getProperty方法正常")
        void testgetProperty() {
            TestModel model = new TestModel();
            model.setId("id1");
            model.setName("name1");
            TestModel modelInner = new TestModel();
            modelInner.setId("innerId");
            modelInner.setName("innerName");
            model.setModel(modelInner);

            String result = OptionalUtil.ofNullable(model).getProperty(TestModel::getModel)
                    .getProperty(TestModel::getId).get();
            assertEquals("innerId", result);
        }

        @Test
        @DisplayName("getProperty方法null,get返回null")
        void testgetPropertyNull() {
            TestModel model = new TestModel();
            model.setId("id1");
            model.setName("name1");

            String result = OptionalUtil.ofNullable(model).getProperty(TestModel::getModel)
                    .getProperty(TestModel::getId).get();
            assertNull(result);
        }

        @Test
        @DisplayName("test Optional map")
        void testOptional() {
            TestModel model = new TestModel();
            model.setId("id1");
            model.setName("name1");
            NoSuchElementException assertThrows = assertThrows(NoSuchElementException.class,
                    () -> Optional.ofNullable(model).map(m -> m.getModel()).map(t -> t.getName()).get());
            assertNotNull(assertThrows);
        }
    }

    @Test
    @DisplayName("OrElse方法")
    void testOrElse() {
        Double orElse = OptionalUtil.ofNullable(Double.valueOf(12)).orElse(100.0);
        assertEquals(12, orElse, 0);
        Double param = null;
        Double orElse2 = OptionalUtil.ofNullable(param).orElse(100.0D);
        assertEquals(100, orElse2, 0);
    }

    @Test
    @DisplayName("OrElseGet方法")
    void testOrElseGet() {
        Double orElse = OptionalUtil.ofNullable(Double.valueOf(12)).orElseGet(() -> {
            return 10 * 12D;
        });
        assertEquals(12, orElse, 0);
        Double param = null;
        Double orElse2 = OptionalUtil.ofNullable(param).orElseGet(() -> {
            return 10 * 12D;
        });
        assertEquals(120, orElse2, 0);
    }

    @Test
    @DisplayName("OrElseThrow单元测试")
    void testOrElseThrow() {
        Double param = null;
        NullPointerException assertThrows = assertThrows(NullPointerException.class,
                () -> OptionalUtil.ofNullable(param).orElseThrow(() -> new NullPointerException()));
        assertNotNull(assertThrows);
    }

    @Test
    @DisplayName("IsPresent单元测试")
    void testIsPresent() {
        boolean present = OptionalUtil.ofNullable(Double.valueOf(33)).isPresent();
        assertEquals(true, present);
        present = OptionalUtil.ofNullable(null).isPresent();
        assertEquals(false, present);
    }

    @Test
    @DisplayName("IfPresent单元测试")
    void testIfPresent() {
        OptionalUtil.ofNullable(Double.valueOf(33)).ifPresent((value) -> {
            System.out.println(value);
        });
    }

    @Nested
    @DisplayName("Filter单元测试")
    class FilterTest {

        @Test
        @DisplayName("Filter null 单元测试")
        void testFilterNull() {
            Object result = OptionalUtil.ofNullable(null).filter((value) -> {
                if (value == null) {
                    return true;
                } else {
                    return false;
                }
            }).get();
            assertNull(result);
        }

        @Test
        @DisplayName("Filter null 单元测试")
        void testFilter() {
            Double param = 100D;
            Double result = OptionalUtil.ofNullable(param).filter((value) -> {
                if (value > 10) {
                    return true;
                } else {
                    return false;
                }
            }).get();
            assertEquals(100D, result, 1);

            param = 3D;
            result = OptionalUtil.ofNullable(param).filter((value) -> {
                if (value > 10) {
                    return true;
                } else {
                    return false;
                }
            }).get();
            assertNull(result);
        }
    }
}
