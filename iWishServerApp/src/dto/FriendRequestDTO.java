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
public class FriendRequestDTO {
    private String fromEmail;
    private String toEmail;
    private java.sql.Date requestDate;

    // Constructors, getters, and setters

    // Example constructors (you may need to create more)
    public FriendRequestDTO(String fromEmail, String toEmail, java.sql.Date requestDate) {
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.requestDate = requestDate;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

}
