package review.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Department {

    /*
    "items": [
        { "department_id": 10,
            "department_name": "Administration",
            "manager_id": 200 }
        ]
     */


    public Department(){

    }

    private String department_name;
    private int department_id;
    private int manager_id;

    public Department(String department_name, int department_id, int manager_id){

        this.department_name = department_name;
        this.department_id = department_id;
        this.manager_id = manager_id;

    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    @Override
    public String toString() {
        return "Department{" +
                "department_name='" + department_name + '\'' +
                ", department_id=" + department_id +
                ", manager_id=" + manager_id +
                '}';
    }



}
