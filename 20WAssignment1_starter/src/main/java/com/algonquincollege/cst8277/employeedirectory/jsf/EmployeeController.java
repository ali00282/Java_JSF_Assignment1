/***************************************************************************f******************u************zz*******y**
 * File: EmployeeController.java
 * Course materials (20W) CST 8277
 * @author (original) Mike Norman
 *
 */
package com.algonquincollege.cst8277.employeedirectory.jsf;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import com.algonquincollege.cst8277.employeedirectory.dao.EmployeeDao;
import com.algonquincollege.cst8277.employeedirectory.model.EmployeeDTO;

/**
 * Description: Responsible for collection of Employee DTO's in XHTML (list) <h:dataTable> </br>
 * Delegates all C-R-U-D behaviour to DAO
 */

@Named
@SessionScoped
public class EmployeeController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    protected ExternalContext externalContext;
    // inject sessionMap
    protected Map<Object, String> sessionMap;
    protected EmployeeDao employeeDao;
    protected List<EmployeeDTO> employees;

    @Inject
    public EmployeeController(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    private void logMsg(String msg) {
        ((ServletContext)externalContext.getContext()).log(msg);
    }

    //necessary methods to make controller work

    public void loadEmployees() {
        logMsg("loading all employees");
        setEmployees(employeeDao.readAllEmployees());
    }

    public List<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDTO> employees) {
        this.employees = employees;
    }

    public void deleteEmployee(int empId) {
        logMsg("not yet implemented");
        // TODO
       employeeDao.deleteEmployeeById(empId);
      
    }
    
    public String addEmployee() {
        externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> map = externalContext.getSessionMap();
        map.put("emp", new EmployeeDTO());
        return "new-employee?faces-redirect=true";
    }   
    
    public String insertEmployee(EmployeeDTO employees) {
        EmployeeDTO empdto= employeeDao.createEmployee(employees);
        
        return "list-employees?faces-redirect=true";
        
    }
    
//    public String delEmployee(EmployeeDTO employees) {
//        EmployeeDTO empdto= employeeDao.deleteEmployeeById(employeeId);
//        
//        return "list-employees?faces-redirect=true";
//        
//    }
//    
    

    // TODO - additional methods required to handle rest of C-R-U-D
    //        and navigation across XHTML pages

}