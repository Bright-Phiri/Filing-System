/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.model;

/**
 *
 * @author Bright
 */
public class Employee extends User {

    private String department;
    private String staffType;
    private String fileName;

    public Employee(int id, String firstName, String lastName, String department, String staffType) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        this.department = department;
        this.staffType = staffType;
    }

    public Employee(String firstName, String lastName, String department, String fileName) {
        setFirstName(firstName);
        setLastName(lastName);
        this.department = department;
        this.fileName = fileName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
