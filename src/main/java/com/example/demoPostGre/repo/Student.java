package com.example.demoPostGre.repo;

public class Student {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int marks;
    public Student (int id,String name, int marks){
        this.id=id;
        this.name=name;
        this.marks=marks;
    }
}
