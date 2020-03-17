package com.example.myapplication.model;

public class UserDTO {

    private String username;

    private int gender;

    private String email;

    private String iconimg_url;

    public UserDTO(){
        username=User.getUsername();
        gender=User.getGender();
        email=User.getEmail();
        iconimg_url =User.getIconimg_url();
    }
    public UserDTO(String username, int gender, String email, String iconimgUrl){
        this.username=username;
        this.gender=gender;
        this.email=email;
        this.iconimg_url =iconimgUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getIconimg_url() {
        return iconimg_url;
    }

    public void setIconimg_url(String iconimg_url) {
        this.iconimg_url = iconimg_url == null ? null : iconimg_url.trim();
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", iconimg_url='" + iconimg_url + '\'' +
                '}';
    }
}