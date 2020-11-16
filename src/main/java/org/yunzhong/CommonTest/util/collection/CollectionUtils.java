package org.yunzhong.CommonTest.util.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yunzhong
 *
 */
public class CollectionUtils {

    /**
     * @param list
     * @return
     */
    public static String[] toArray(List<String> list) {
        return list.toArray(new String[list.size()]);
    }

    /**
     * @param array
     * @return
     */
    public static List<String> toList(String[] array) {
        List<String> list = new ArrayList<>();
        for (String string : array) {
            list.add(string);
        }
        return list;
    }

    /**
     * never use this! never!
     * 
     * @param array
     * @return
     */
    @Deprecated
    public static List<String> toListDeprecated(String[] array) {
        return Arrays.asList(array);
    }

    /**
     * @param array
     * @return
     */
    public static List<String> toListStream(String[] array) {
        return Arrays.stream(array).collect(Collectors.toList());
    }

    /**
     * @param array
     * @return
     */
    public static List<String> addAll(String[] array) {
        ArrayList<String> arrayList = new ArrayList<String>();
        Collections.addAll(arrayList, array);
        return arrayList;
    }
}
