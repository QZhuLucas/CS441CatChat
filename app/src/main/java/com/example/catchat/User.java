package com.example.catchat;

public class User {

  private String Course;
  private String emailAddress;
  private String userType;
  private String gender;
  private String country;
  private String name;
  private String phone;

  public User(){

  }

  public User( String course, String emailAddress, String userType, String gender, String country, String name, String phone) {

    this.Course = course;
    this.emailAddress = emailAddress;
    this.userType = userType;
    this.gender = gender;
    this.country = country;
    this.name = name;
    this.phone = phone;
  }


  public void setCourse(String course) {
    Course = course;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public void setName(String name) { this.name = name; }

  public void setPhone(String phone) { this.phone = phone; }

  public String getCourse() {
    return Course;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public String getUserType() {
    return userType;
  }

  public String getGender() {
    return gender;
  }

  public String getCountry() {
    return country;
  }

  public String getName() {
    return name;
  }

  public String getPhone() { return phone; }
}