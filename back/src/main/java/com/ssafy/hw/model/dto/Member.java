package com.ssafy.hw.model.dto;

import java.sql.Timestamp;

public class Member {
    private int memberId;
    private String email;
    private String password;
    private String name;
    private Timestamp registeredAt;
    private int age;
    private String preferedPlace1;
    private String preferedPlace2;
    private String preferedPlace3;
    private String preferedType;

    public Member() {
    }

    public Member(int memberId, String email, String password, String name, Timestamp registeredAt, int age, String preferedPlace1, String preferedPlace2, String preferedPlace3, String preferedType) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.registeredAt = registeredAt;
        this.age = age;
        this.preferedPlace1 = preferedPlace1;
        this.preferedPlace2 = preferedPlace2;
        this.preferedPlace3 = preferedPlace3;
        this.preferedType = preferedType;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Timestamp registeredAt) {
        this.registeredAt = registeredAt;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPreferedPlace1() {
        return preferedPlace1;
    }

    public void setPreferedPlace1(String preferedPlace1) {
        this.preferedPlace1 = preferedPlace1;
    }

    public String getPreferedPlace2() {
        return preferedPlace2;
    }

    public void setPreferedPlace2(String preferedPlace2) {
        this.preferedPlace2 = preferedPlace2;
    }

    public String getPreferedPlace3() {
        return preferedPlace3;
    }

    public void setPreferedPlace3(String preferedPlace3) {
        this.preferedPlace3 = preferedPlace3;
    }

    public String getPreferedType() {
        return preferedType;
    }

    public void setPreferedType(String preferedType) {
        this.preferedType = preferedType;
    }
}
