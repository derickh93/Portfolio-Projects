package edu.ncc.nccdepartmentdatabase;
public class DepartmentEntry {
    private long id;
    private String name;
    private String location;
    private String phone;
    private String email;


    public DepartmentEntry()
    {

    }

    public DepartmentEntry(String name, String location, String phone, String email)
    {
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.email = email;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean equals(Object otherDept)
    {
        return this.id == ((DepartmentEntry)otherDept).id;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return id + ": " + name + " - " + location + " - " + phone + " - " + email;
    }
}

