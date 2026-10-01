package util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;
import jakarta.servlet.http.Part;

public class UploadUtil_24162007 {

    public static final Path ROOT = Paths.get(System.getProperty("user.home"), "de04_uploads");
    private static final Set<String> IMAGE_EXT = Set.of("jpg", "jpeg", "png", "gif", "webp");
    private static final Set<String> VIDEO_EXT = Set.of("mp4", "webm", "ogg");

    /** type = "image" hoặc "video". Trả về tên file đã lưu, hoặc null nếu không chọn file. */
    public static String save(Part part, String type) throws IOException {
        if (part == null || part.getSize() == 0) return null;

        String original = part.getSubmittedFileName();
        int dot = (original == null) ? -1 : original.lastIndexOf('.');
        String ext = (dot < 0) ? "" : original.substring(dot + 1).toLowerCase();

        Set<String> allowed = type.equals("image") ? IMAGE_EXT : VIDEO_EXT;
        if (!allowed.contains(ext)) {
            throw new IOException("Định dạng .'" + ext + "' không được hỗ trợ");
        }

        Files.createDirectories(ROOT);
        String saved = UUID.randomUUID().toString().replace("-", "") + "." + ext;
        try (InputStream in = part.getInputStream()) {
            Files.copy(in, ROOT.resolve(saved), StandardCopyOption.REPLACE_EXISTING);
        }
        return saved;
    }
}