package com.example.techiedany.campusadmin.dummy;


public class StudentDetails {


    private String name;
    private String phoneNumber;
    private String date;
    private String img;
    private String uname;

    public StudentDetails() {
        // This is default constructor.
    }

    public String getStudentName() {

        return name;
    }

    public void setStudentName(String name) {

        this.name = name;
    }

    public String getStudentPhoneNumber() {

        return phoneNumber;
    }

    public void setStudentPhoneNumber(String phonenumber) {

        this.phoneNumber = phonenumber;
    }


    public String getDate() {

        return date;
    }

    public void setDate(String date){

        this.date = date;
    }

    public String getImg() {

        return img;
    }

    public void setImg(String img){

        this.img = img;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname){

        this.uname = uname;
    }


}