package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** Đọc cấu hình từ app.properties (trong classpath). */
public class AppConfig_24162007 {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = AppConfig_24162007.class.getClassLoader().getResourceAsStream("app.properties")) {
            if (in != null) {
                PROPS.load(new java.io.InputStreamReader(in, java.nio.charset.StandardCharsets.UTF_8));
            } else {
                System.out.println("[AppConfig] Không tìm thấy app.properties trong classpath");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key, String defaultValue) {
        String v = PROPS.getProperty(key);
        return (v == null) ? defaultValue : v.trim();
    }
}
