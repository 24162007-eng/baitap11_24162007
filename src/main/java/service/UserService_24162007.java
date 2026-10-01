package service;

import java.sql.Timestamp;
import java.util.List;
import dao.UserDAO_24162007;
import entity.Users_24162007;
import util.MailUtil_24162007;

public class UserService_24162007 {

    private UserDAO_24162007 userDAO = new UserDAO_24162007();

    public Users_24162007 login(String username, String password) {
        return userDAO.checkLogin(username, password);
    }

    public String register(Users_24162007 u) {
        if (userDAO.checkUsernameExists(u.getUsername())) {
            return "Username đã tồn tại";
        }
        String otp = MailUtil_24162007.generateOtp();
        u.setAdmin(false);
        u.setActive(false);
        u.setOtpCode(otp);
        u.setOtpExpire(new Timestamp(System.currentTimeMillis() + 5 * 60 * 1000));
        boolean ok = userDAO.insertUser(u);
        if (!ok) {
            return "Đăng ký thất bại";
        }
        if (!MailUtil_24162007.sendOtp(u.getEmail(), otp)) {
            userDAO.deleteUser(u.getUsername()); // gửi mail lỗi -> hủy tài khoản vừa tạo
            return "Không gửi được mã OTP tới email. Vui lòng kiểm tra email hoặc cấu hình mail rồi thử lại";
        }
        return "OK";
    }

    public boolean verifyOtp(String username, String otp) {
        return userDAO.verifyOtp(username, otp);
    }

    public List<Users_24162007> getUsersPaginated(int page, int pageSize) {
        return userDAO.getUsersPaginated(page, pageSize);
    }

    public int getTotalPages(int pageSize) {
        int total = userDAO.countUsers();
        return (int) Math.ceil((double) total / pageSize);
    }

    public Users_24162007 getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    public boolean updateUser(Users_24162007 u) {
        return userDAO.updateUser(u);
    }

    public boolean deleteUser(String username) {
        return userDAO.deleteUser(username);
    }

    public boolean createUserByAdmin(Users_24162007 u) {
        if (userDAO.checkUsernameExists(u.getUsername())) return false;
        u.setActive(true);
        return userDAO.insertUser(u);
    }
}