package helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class FormattedHelpers {
    public static String del = "|";

    public static String addToLikedStr(String likedString, int id) {
        HashSet<String> list = new HashSet<String>(Arrays.asList(likedString.split(del)));
        if (likedString == null) likedString = Integer.toString(id);
        if (!list.contains(Integer.toString(id))) likedString = likedString.concat(del + Integer.toString(id));
        return likedString;
    }

    public static String removeFromLikedStr(String str, int id) {
        HashSet<String> list = new HashSet<String>(Arrays.asList(str.split(del)));
        String newString = "";
        for (String item : list) {
            if (!item.equals(Integer.toString(id)) && !item.equals(del)) newString = newString.concat(del + item);
        }
        return newString;
    }


    public static List<Integer> getListIDLikedUsers(String likedString) throws NumberFormatException {
        HashSet<String> listString = new HashSet<String>(Arrays.asList(likedString.split(del)));
        List<Integer> list = new ArrayList<Integer>();
        for (String item : listString) {
            try {
                if(!item.equals("|")) {
                    int itemInt = Integer.parseInt(item);
                    if (itemInt > 0) list.add(itemInt);
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
