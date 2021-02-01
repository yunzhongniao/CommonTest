package org.yunzhong.CommonTest.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CamelUtils {
    
        /**
         * 下划线转驼峰式（String）
         * @param lineString
         * @param smallCamel    true-返回小驼峰,false-大驼峰式
         * @return
         */
        public static String underlineToCamel(String lineString,boolean smallCamel){
            if(null == lineString || "".equals(lineString)) return lineString;
            StringBuffer sb = new StringBuffer();
            Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");    //字母、数字、下划线
            Matcher matcher = pattern.matcher(lineString);
            while (matcher.find()) {
                String word = matcher.group();
                sb.append(smallCamel && matcher.start() == 0 ?
                        Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0)));
                int index = word.lastIndexOf("_");
                if(index > 0){
                    sb.append(word.substring(1, index).toLowerCase());
                }else{
                    sb.append(word.substring(1).toLowerCase());
                }
            }
            return sb.toString();
        }
        /**
         * 驼峰式转下划线（String）
         * @param camelString
         * @return
         */
        public static String camelToUnderline(String camelString){
            if(null == camelString || "".equals(camelString)) return camelString;
            camelString = String.valueOf(camelString.charAt(0)).toUpperCase().concat(camelString.substring(1));
            
            StringBuffer sb = new StringBuffer();
            Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?"); //字母、数字、下划线
            Matcher matcher = pattern.matcher(camelString);
            while (matcher.find()) {
                String word = matcher.group();
                sb.append(word.toLowerCase());
                sb.append(matcher.end() == camelString.length() ? "" : "_");
            }
            return sb.toString();
        }
        
        
        /**
         * 下划线转驼峰式（Map）
         * @param map
         * @return  newMap
         */
        public static Map<String,Object> underlineToCamel(Map<String,Object> map){
            Map<String,Object> newMap  = new HashMap<String,Object>();
            Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
            Entry<String, Object> entry;
            while(iterator.hasNext()){
                entry  = iterator.next();
                if(null != entry.getKey() && entry.getKey() instanceof String){
                    newMap.put(underlineToCamel(entry.getKey(), true), entry.getValue());
                }
                iterator.remove();
            }
            return newMap;
        }
        /**
         * 驼峰式转下划线（Map）
         * @param map
         * @return  newMap
         */
        public static Map<String,Object> camelToUnderline(Map<String,Object> map){
            Map<String,Object> newMap  = new HashMap<String,Object>();
            Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
            Entry<String, Object> entry;
            while(iterator.hasNext()){
                entry  = iterator.next();
                if(null != entry.getKey() && entry.getKey() instanceof String){
                    newMap.put(camelToUnderline(entry.getKey()), entry.getValue());
                }
                iterator.remove();
            }
            return newMap;
        }
        
        public static void main(String[] args) {
            System.out.println(underlineToCamel("Justin_qin_jj", false));
            System.out.println(camelToUnderline("JustinQinJj"));
            
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("yourName", "Justin");
            map.put("yourSex", "男");
            map.put("youreAge", 22);
            
            Map<String, Object> camelToUnderlineMap = camelToUnderline(map);
            System.out.println(camelToUnderlineMap.toString());
            
            Map<String, Object> underlineToCamelMap = underlineToCamel(camelToUnderlineMap);
            System.out.println(underlineToCamelMap.toString());
            
        }
}
