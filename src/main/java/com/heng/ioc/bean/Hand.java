package com.heng.ioc.bean;

public class Hand {

    public People people;

    public void say(){
        System.out.println("这是手");
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }
}
