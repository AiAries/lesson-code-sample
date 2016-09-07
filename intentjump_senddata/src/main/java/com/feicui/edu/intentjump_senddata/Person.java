package com.feicui.edu.intentjump_senddata;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class Person implements Serializable{
   private int age;
   private String name;

    public Person(int age, String name, String hobby) {
        this.age = age;
        this.name = name;
        this.hobby = hobby;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHobby() {

        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private String hobby;
}
