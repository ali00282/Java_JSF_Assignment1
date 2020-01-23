/***************************************************************************f******************u************zz*******y**
 * File: EmployeeDaoImpl.java
 * Course materials (20W) CST 8277
 *
 * @author (original) Mike Norman
 *
 */
package com.algonquincollege.cst8277.employeedirectory.dao;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import com.algonquincollege.cst8277.employeedirectory.model.EmployeeDTO;

/**
* Description: Implements the C-R-U-D API for the database
*/

@Named
@ApplicationScoped
public class EmployeeDaoImpl implements EmployeeDao, Serializable {
    /** explicitly set serialVersionUID */
    private static final long serialVersionUID = 1L;

    private static final String EMPLOYEE_DS_JNDI = 
        "java:app/jdbc/employeeDirectory";
    private static final String READ_ALL = 
        "select * from employee";
    private static final String READ_EMPLOYEE_BY_ID =
        "select * from employee where id = ?";
    private static final String UPDATE_EMPLOYEE_ALL_FIELDS = // TODO
        "update employee set fname= ?, lname=?, email=?, title=?, salary=? where id=? ";
    private static final String DELETE_EMPLOYEE_BY_ID = // TODO
        "delete from employee where id=?";
    private static final String INSERT_NEW_EMPLOYEE =
            "insert into employee (fname, lname, email, title,salary) values(?,?,?,?,?)";

    @Inject
    protected ExternalContext externalContext;
    private void logMsg(String msg) {
        ((ServletContext)externalContext.getContext()).log(msg);
    }

    @Resource(lookup = EMPLOYEE_DS_JNDI)
    protected DataSource employeeDS;

    protected Connection conn;
    protected PreparedStatement readAllPstmt;
    protected PreparedStatement readByIdPstmt;
    protected PreparedStatement createPstmt;
    protected PreparedStatement updatePstmt;
    protected PreparedStatement deleteByIdPstmt;

    @PostConstruct
    protected void buildConnectionAndStatements() {
        try {
            logMsg("building connection and stmts");
            conn = employeeDS.getConnection();
            readAllPstmt = conn.prepareStatement(READ_ALL);
            readByIdPstmt = conn.prepareStatement(READ_EMPLOYEE_BY_ID);
            createPstmt = conn.prepareStatement(INSERT_NEW_EMPLOYEE);
            updatePstmt = conn.prepareStatement(UPDATE_EMPLOYEE_ALL_FIELDS);
            
            
            //TODO - prepare rest of statements for rest of C-R-U-D
        }
        catch (Exception e) {
            logMsg("something went wrong getting connection from database: " + e.getLocalizedMessage());
        }
    }

    @PreDestroy
    protected void closeConnectionAndStatements() {
        try {
            logMsg("closing stmts and connection");
            readAllPstmt.close();
            readByIdPstmt.close();
            createPstmt.close();
            updatePstmt.close();
            //TODO - close rest of statements ...
            conn.close();
        }
        catch (Exception e) {
            logMsg("something went wrong closing stmts or connection: " + e.getLocalizedMessage());
        }
    }

    //TODO - fill in rest of EmployeeDao C-R-U-D API
    
   
    @Override
    public List<EmployeeDTO> readAllEmployees() {
        logMsg("reading all employees");
        List<EmployeeDTO> employees = new ArrayList<>();
        try {
            ResultSet rs = readAllPstmt.executeQuery();
            while (rs.next()) {
                EmployeeDTO newEmployee = new EmployeeDTO();
                newEmployee.setId(rs.getInt("id"));
                newEmployee.setFirstName(rs.getString("fname"));
                newEmployee.setLastName(rs.getString("lname"));
                newEmployee.setEmail(rs.getString("email"));
                newEmployee.setSalary(rs.getDouble("salary"));
                newEmployee.setTitle(rs.getString("title"));
                employees.add(newEmployee);
            }
            try {
                rs.close();
            }
            catch (Exception e) {
                logMsg("something went wrong closing resultSet: " + e.getLocalizedMessage());
            }
        }
        catch (SQLException e) {
            logMsg("something went wrong accessing database: " + e.getLocalizedMessage());
        }
        return employees;
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employee) {
        logMsg("creating an employee");
        // TODO - see note in Assignment doc about Statement.RETURN_GENERATED_KEYS
        employee = new EmployeeDTO();
        try {
            
            createPstmt.setString(1, employee.getFirstName());
            createPstmt.setString(2, employee.getLastName());
            createPstmt.setString(3, employee.getEmail());
            createPstmt.setString(4, employee.getTitle());
            createPstmt.setDouble(5, employee.getSalary());
            int create = createPstmt.executeUpdate();
            System.out.println(create);
             
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        return employee;
    }

    @Override
    public EmployeeDTO readEmployeeById(int employeeId) {
        logMsg("read a specific employee");
        // TODO
        EmployeeDTO foundEmployee = new EmployeeDTO();
        try {
            ResultSet rs = readByIdPstmt.executeQuery();
            foundEmployee.setId(rs.getInt(employeeId));
            
            System.out.println("Emloyee Id is" + rs.getInt(employeeId));
            
        } catch (SQLException e) {
            
            e.printStackTrace();
        }

        return foundEmployee;
    }

    @Override
    @Transactional
    public void updateEmployee(EmployeeDTO employee) {
        logMsg("updating a specific employee");
        // TODO
        try {
            ResultSet rs = updatePstmt.executeQuery();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void deleteEmployeeById(int employeeId) {
        logMsg("deleting a specific employee");
        // TODO
        EmployeeDTO foundEmployee = readEmployeeById(employeeId);
        
       try {
           
        ResultSet rs = deleteByIdPstmt.executeQuery();
        
        
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
        
    }

}