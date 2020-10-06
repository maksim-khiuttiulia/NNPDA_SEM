package cz.upce.NNPDASEM1.utils;

public class StringUtils {
    public static boolean isNotEquals(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return false;
        }
        if (str1 == null) {
            return true;
        }
        return !str1.equals(str2);
    }
}
