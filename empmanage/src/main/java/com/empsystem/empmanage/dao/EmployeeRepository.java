package com.empsystem.empmanage.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.empsystem.empmanage.Entities.Employee;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer>{
    public Employee findByemail(String emailId);
}
