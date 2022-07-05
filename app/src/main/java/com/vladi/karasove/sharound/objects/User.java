package com.vladi.karasove.sharound.objects;

public class User {
    private String userPic;
    private String userFirstName;
    private String userLastName;
    private String userPhoneNumber;
    private String userBirthYear;

    @Override
    public String toString() {
        return "User{" +
                "userPic='" + userPic + '\'' +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", userPhoneNumber='" + userPhoneNumber + '\'' +
                ", birthYear='" + userBirthYear + '\'' +
                '}';
    }

    public User() {
    }

    public void setBirthYear(String birthYear) {
        this.userBirthYear = birthYear;
    }

    public String getUserBirthYear() {
        return userBirthYear;
    }

    public String getUserPic() {
        return userPic;
    }

    public User setUserPic(String userPic) {
        this.userPic = userPic;
        return this;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public User setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
        return this;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public User setUserLastName(String userLastName) {
        this.userLastName = userLastName;
        return this;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public User setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
        return this;
    }


}
