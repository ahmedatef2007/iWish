/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.sql.Date;

/**
 *
 * @author ahmed
 */
public class NotificationDTO {
    private String userEmail;
    private String fromEmail;
    private String notificationText;
    private java.sql.Date notificationDate;
    private boolean isNotified;

    // Constructors, getters, and setters

    public NotificationDTO(String userEmail, String fromEmail, String notificationText, java.sql.Date notificationDate, boolean isNotified) {
        this.userEmail = userEmail;
        this.fromEmail = fromEmail;
        this.notificationText = notificationText;
        this.notificationDate = notificationDate;
        this.isNotified = isNotified;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public Date getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Date notificationDate) {
        this.notificationDate = notificationDate;
    }

    public boolean isIsNotified() {
        return isNotified;
    }

    public void setIsNotified(boolean isNotified) {
        this.isNotified = isNotified;
    }
}
