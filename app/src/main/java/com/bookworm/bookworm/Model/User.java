package com.bookworm.bookworm.Model;

public class User {

private String email;
    private String fullname;
    private String phone;
    private String pincode;
    private String pro_image;
    private String show_mob;
    private String verif;

    public User(String email, String fullname, String phone, String pincode, String pro_image, String show_mob, String verif) {
        this.email = email;
        this.fullname = fullname;
        this.phone = phone;
        this.pincode = pincode;
        this.pro_image = pro_image;
        this.show_mob = show_mob;
        this.verif = verif;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPro_image() {
        return pro_image;
    }

    public void setPro_image(String pro_image) {
        this.pro_image = pro_image;
    }

    public String getShow_mob() {
        return show_mob;
    }

    public void setShow_mob(String show_mob) {
        this.show_mob = show_mob;
    }

    public String getVerif() {
        return verif;
    }

    public void setVerif(String verif) {
        this.verif = verif;
    }
}
