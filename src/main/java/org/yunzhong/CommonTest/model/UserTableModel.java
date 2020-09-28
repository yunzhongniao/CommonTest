package org.yunzhong.CommonTest.model;

import java.util.List;

public class UserTableModel {

    public static class User {
        private String name;
        private String password;
        private String sex;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

    }

    public static class UserStat {
        private String title;
        private Long userCount;
        private Double per;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Long getUserCount() {
            return userCount;
        }

        public void setUserCount(Long userCount) {
            this.userCount = userCount;
        }

        public Double getPer() {
            return per;
        }

        public void setPer(Double per) {
            this.per = per;
        }

    }

    private String className;

    private List<User> users;

    private List<UserStat> userStats;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<UserStat> getUserStats() {
        return userStats;
    }

    public void setUserStats(List<UserStat> userStats) {
        this.userStats = userStats;
    }

}
