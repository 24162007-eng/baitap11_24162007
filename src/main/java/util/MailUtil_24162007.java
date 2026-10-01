package util;

import java.security.SecureRandom;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil_24162007 {

    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Gửi OTP qua Gmail.
     * @return true nếu gửi thành công; false nếu gửi lỗi.
     * Nếu chưa cấu hình mail.password thì chỉ in OTP ra Console (để test) và trả về true.
     */
    public static boolean sendOtp(String toEmail, String otp) {
        final String from = AppConfig_24162007.get("mail.user", "");
        final String pass = AppConfig_24162007.get("mail.password", "");

        if (from.isEmpty() || pass.isEmpty()) {
            System.out.println("[MailUtil] CHƯA cấu hình mail.password -> không gửi mail thật. OTP cho " + toEmail + " = " + otp);
            return true;
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pass);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Mã OTP xác thực tài khoản", "UTF-8");
            message.setText("Mã OTP của bạn là: " + otp + ". Mã có hiệu lực trong 5 phút.", "UTF-8");
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String generateOtp() {
        return String.valueOf(100000 + RANDOM.nextInt(900000));
    }
}
