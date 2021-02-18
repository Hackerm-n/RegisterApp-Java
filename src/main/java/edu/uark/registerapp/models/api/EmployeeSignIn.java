package edu.uark.registerapp.models.api;

public class EmployeeSignIn {
    String employeeId;
    String password;

    String getEmployeeId(){
        return employeeId;
    }

    String getPassword(){
        return password;
    }

    void setEmployeeId(String EmployeeId) {
        employeeId = EmployeeId;
    }

    void setPassword(String Password) {
        password = Password;
    }
}
