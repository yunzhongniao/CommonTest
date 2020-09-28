package org.yunzhong.CommonTest.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.yunzhong.CommonTest.model.UserTableModel;
import org.yunzhong.CommonTest.model.UserTableModel.UserStat;

public class ParamsUtil {

    public static Map<String, String> parseToMap(UserTableModel userTableModel) {
        if (userTableModel == null) {
            return null;
        }
        Map<String, String> result = new HashMap<String, String>();
        if (!StringUtils.isEmpty(userTableModel.getClassName())) {
            result.put("userStat.className", userTableModel.getClassName());
        } else {
            result.put("userStat.className", "");
        }
        if (!CollectionUtils.isEmpty(userTableModel.getUserStats())) {
            for (int i = 0; i < userTableModel.getUserStats().size(); i++) {
                UserStat userStat = userTableModel.getUserStats().get(i);
                String titleName = String.format("%s.%s.%s", "userStat.userStats", String.valueOf(i), "title");
                String titleValue = userStat.getTitle();
                result.put(titleName, titleValue);

                String userCountName = String.format("%s.%s.%s", "userStat.userStats", String.valueOf(i), "userCount");
                String userCountValue = userStat.getUserCount().toString();
                result.put(userCountName, userCountValue);

                String perName = String.format("%s.%s.%s", "userStat.userStats", String.valueOf(i), "per");
                String perValue = userStat.getPer().toString();
                result.put(perName, perValue);
            }
        }

        return result;
    }

    public static Map<String, Object> parseToObjMap(UserTableModel userTableModel) {
        if (userTableModel == null) {
            return null;
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(userTableModel.getClassName())) {
            result.put("userStat.className", userTableModel.getClassName());
        } else {
            result.put("userStat.className", "");
        }
        if (!CollectionUtils.isEmpty(userTableModel.getUserStats())) {
            for (int i = 0; i < userTableModel.getUserStats().size(); i++) {
                UserStat userStat = userTableModel.getUserStats().get(i);
                String titleName = String.format("%s.%s.%s", "userStat.userStats", String.valueOf(i), "title");
                String titleValue = userStat.getTitle();
                result.put(titleName, titleValue);

                String userCountName = String.format("%s.%s.%s", "userStat.userStats", String.valueOf(i), "userCount");
                result.put(userCountName, Long.valueOf(userStat.getUserCount()));

                String perName = String.format("%s.%s.%s", "userStat.userStats", String.valueOf(i), "per");
                result.put(perName, Double.valueOf(userStat.getPer()));
            }
        }

        return result;
    }

    public static Map<String, String> parseToKeyMap(UserTableModel userTableModel) {
        if (userTableModel == null) {
            return null;
        }
        Map<String, String> result = new HashMap<String, String>();
        if (StringUtils.isEmpty(userTableModel.getClassName())) {
            result.put("${userStat.className}", userTableModel.getClassName());
        } else {
            result.put("${userStat.className}", "");
        }
        if (!CollectionUtils.isEmpty(userTableModel.getUserStats())) {
            for (int i = 0; i < userTableModel.getUserStats().size(); i++) {
                UserStat userStat = userTableModel.getUserStats().get(i);
                String titleName = String.format("${%s.%s.%s}", "userStat.userStats", String.valueOf(i), "title");
                String titleValue = userStat.getTitle();
                result.put(titleName, titleValue);

                String userCountName = String.format("${%s.%s.%s}", "userStat.userStats", String.valueOf(i),
                        "userCount");
                String userCountValue = userStat.getUserCount().toString();
                result.put(userCountName, userCountValue);

                String perName = String.format("${%s.%s.%s}", "userStat.userStats", String.valueOf(i), "per");
                String perValue = userStat.getPer().toString();
                result.put(perName, perValue);
            }
        }

        return result;
    }
}
