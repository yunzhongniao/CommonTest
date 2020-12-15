package org.yunzhong.CommonTest.model;

/**
 * @author yunzhong
 *
 */
public class DefaultBeanFactory {

    /**
     * @author yunzhong
     *
     */
    public static class DefaultBean {
        private String code;
        private String message;
        private Object data;

        public DefaultBean() {

        }

        public DefaultBean(String code, Object data) {
            this.code = code;
            this.data = data;
        }

        public DefaultBean(Object data) {
            this("200", data);
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

    }

    public static DefaultBean ok(Object obj) {
        return new DefaultBean(obj);
    }

    public static DefaultBean error(String message) {
        return new DefaultBean("500", message);
    }
}
