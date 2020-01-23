/***************************************************************************f******************u************zz*******y**
 * File: EmployeeDao.java
 * Course materials (20W) CST 8277
 *
 * @author (original) Mike Norman
 *
 */
package com.algonquincollege.cst8277.employeedirectory.dao;

import java.util.List;

import com.algonquincollege.cst8277.employeedirectory.model.EmployeeDTO;

/**
 * Description: API for the database C-R-U-D operations
 */
public interface EmployeeDao {

    // C
    public EmployeeDTO createEmployee(EmployeeDTO employee);
    // R
    public EmployeeDTO readEmployeeById(int employeeId);
    public List<EmployeeDTO> readAllEmployees();
    // U
    public void updateEmployee(EmployeeDTO employee);
    // D
    public void deleteEmployeeById(int employeeId);

}