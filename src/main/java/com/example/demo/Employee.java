package com.example.demo;

public class Employee {
    private boolean is_deleted;
    private int id;
    private String name;
    private String country;
    private String email;

    public Employee(boolean isDeleted, String name, String email, String country) {
        is_deleted = isDeleted;
        this.name = name;
        this.email = email;
        this.country = country;
    }
    public Employee() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
