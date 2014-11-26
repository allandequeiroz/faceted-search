package co.mutt.fun.infra;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by <a href="mailto:allandequeiroz@gmail.com">Allan de Queiroz</a>.
 */
public class Resources {

    private Resources() {}

    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("messages", Locale.getDefault());

    public static final String getMessage(String key, Object... params) {
        return MessageFormat.format(MESSAGES.getString(key), params);
    }
}
