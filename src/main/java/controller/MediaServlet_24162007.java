package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.UploadUtil_24162007;

@WebServlet("/media/*")
public class MediaServlet_24162007 extends HttpServlet {

    private String mime(String name) {
        String n = name.toLowerCase();
        if (n.endsWith(".jpg") || n.endsWith(".jpeg")) return "image/jpeg";
        if (n.endsWith(".png")) return "image/png";
        if (n.endsWith(".gif")) return "image/gif";
        if (n.endsWith(".webp")) return "image/webp";
        if (n.endsWith(".mp4")) return "video/mp4";
        if (n.endsWith(".webm")) return "video/webm";
        if (n.endsWith(".ogg")) return "video/ogg";
        return "application/octet-stream";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String info = req.getPathInfo();
        if (info == null || info.length() < 2) { resp.sendError(404); return; }
        String name = info.substring(1);
        if (name.contains("/") || name.contains("\\") || name.contains("..")) { resp.sendError(404); return; }

        Path file = UploadUtil_24162007.ROOT.resolve(name).normalize();
        if (!file.startsWith(UploadUtil_24162007.ROOT) || !Files.isRegularFile(file)) { resp.sendError(404); return; }

        long len = Files.size(file);
        long start = 0, end = len - 1;

        String range = req.getHeader("Range");
        if (range != null && range.startsWith("bytes=")) {
            try {
                String[] p = range.substring(6).split("-", 2);
                if (p[0].isEmpty()) {
                    long n = Long.parseLong(p[1]);
                    start = Math.max(0, len - n);
                } else {
                    start = Long.parseLong(p[0]);
                    if (p.length > 1 && !p[1].isEmpty()) end = Long.parseLong(p[1]);
                }
            } catch (NumberFormatException e) {
                resp.setStatus(416);
                return;
            }
            end = Math.min(end, len - 1);
            if (start > end) { resp.setStatus(416); return; }
            resp.setStatus(206);
            resp.setHeader("Content-Range", "bytes " + start + "-" + end + "/" + len);
        }

        resp.setContentType(mime(name));
        resp.setHeader("Accept-Ranges", "bytes");
        resp.setContentLengthLong(end - start + 1);

        try (InputStream in = Files.newInputStream(file); OutputStream out = resp.getOutputStream()) {
            in.skipNBytes(start);
            byte[] buf = new byte[8192];
            long remaining = end - start + 1;
            int n;
            while (remaining > 0 && (n = in.read(buf, 0, (int) Math.min(buf.length, remaining))) > 0) {
                out.write(buf, 0, n);
                remaining -= n;
            }
        }
    }
}