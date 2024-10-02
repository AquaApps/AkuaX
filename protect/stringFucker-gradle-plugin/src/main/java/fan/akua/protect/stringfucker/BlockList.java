package fan.akua.protect.stringfucker;

import java.util.ArrayList;
import java.util.List;

public final class BlockList {
    private static final List<String> CLASS_BLOCK_LIST = new ArrayList<>(){{
        add("BuildConfig");
        add("R");
        add("R2");
        add("StringFucker");
    }};
    private static boolean isEmpty(CharSequence s) {
        if (s == null) {
            return true;
        } else {
            return s.isEmpty();
        }
    }

    public static boolean inBlockList(String name) {
        return !isEmpty(name) && checkClass(shortClassName(name));
    }

    private static boolean checkClass(String name) {
        for (String className : CLASS_BLOCK_LIST) {
            if (name.equals(className)) {
                return true;
            }
        }
        return false;
    }

    private static String trueClassName(String className) {
        return className.replace('/', '.');
    }

    private static String shortClassName(String className) {
        String[] spiltArrays = trueClassName(className).split("[.]");
        return spiltArrays[spiltArrays.length - 1];
    }
}