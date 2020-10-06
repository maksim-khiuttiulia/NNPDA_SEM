package cz.upce.NNPDASEM1.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageUtils {
    public static String getMessage(String name, Locale locale, Object... params) {
        String message = ResourceBundle.getBundle("messages", locale).getString(name);
        for (int i = 0; i < params.length; i++) {
            message = message.replace("${" + i + "}", params[i].toString());
        }
        return message;
    }
}
