package edu.uark.registerapp.models.api;

public class EmployeeSignIn {
    String employeeId;
    String password;

    public String getEmployeeId(){
        return employeeId;
    }

    public String getPassword(){
        return password;
    }

    public void setEmployeeId(String EmployeeId) {
        employeeId = EmployeeId;
    }

    public void setPassword(String Password) {
        password = Password;
    }
}
