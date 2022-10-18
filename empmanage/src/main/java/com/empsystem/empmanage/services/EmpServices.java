package com.empsystem.empmanage.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.empsystem.empmanage.Entities.Employee;
import com.empsystem.empmanage.dao.EmployeeRepository;

@Component
public class EmpServices {
    
    @Autowired
    private EmployeeRepository empRepo;

    public List<Employee> getAll(){
        return (List<Employee>)empRepo.findAll();
    }

    public void addEmp(Employee emp){
        empRepo.save(emp);
    }

    public void deleteByEmpId(int id){
        this.empRepo.deleteById(id);
    }

    public Employee getbyEmpId(int id) throws Exception{
        Optional<Employee> opt=this.empRepo.findById(id);
        Employee emp=null;
        if(opt.isPresent()){
            emp=opt.get();
        }else{
            throw new Exception("Employee not found for id :: "+id);
        }
        return emp;
        
    }
    public Employee findByEmail(String mailid){
        Employee emp=null;
        emp=this.empRepo.findByemail(mailid);
        return emp;
    }

    
    
    

    
}
