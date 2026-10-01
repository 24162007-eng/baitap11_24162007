package entity;

import java.sql.Date;

public class Shares_24162007 {
    private int shareId;
    private String emails;
    private Date sharedDate;
    private String username;
    private String videoId;

    public Shares_24162007() {}

    public int getShareId() { return shareId; }
    public void setShareId(int shareId) { this.shareId = shareId; }
    public String getEmails() { return emails; }
    public void setEmails(String emails) { this.emails = emails; }
    public Date getSharedDate() { return sharedDate; }
    public void setSharedDate(Date sharedDate) { this.sharedDate = sharedDate; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getVideoId() { return videoId; }
    public void setVideoId(String videoId) { this.videoId = videoId; }
}