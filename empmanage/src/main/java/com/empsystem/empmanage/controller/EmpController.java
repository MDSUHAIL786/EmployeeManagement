package com.empsystem.empmanage.controller;

import java.util.Optional;

// import org.apache.catalina.valves.rewrite.InternalRewriteMap.Escape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// import org.springframework.http.ResponseEntity;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestBody;

import com.empsystem.empmanage.Entities.Employee;
import com.empsystem.empmanage.services.EmpServices;

@Controller
public class EmpController {
    @Autowired
    private EmpServices eServices;

    // manage details via browser
    @GetMapping("/")
    public String showAll(Model m) {
        m.addAttribute("listOfEmployees", eServices.getAll());
        return "index";
    }

    // add new employee
    @GetMapping("/addEmployee")
    public String addEmp(Model m) {

        m.addAttribute("newEmp", new Employee());
        return "AddEmp";
    }

    @PostMapping("/saveEmp")
    public String saveEmp(@ModelAttribute("newEmp") Employee newEmp) {
        // let email as a candidate key i.e unique values
        Employee e = this.eServices.findByEmail(newEmp.getEmail());
        if (e == null) {
            this.eServices.addEmp(newEmp);
            return "redirect:/";

        } else {
            return "error";
        }
    }

    // update
    @PostMapping("/updEmp")
    public String updateEmp(@ModelAttribute("newEmp") Employee newEmp) {

        this.eServices.addEmp(newEmp);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String del(@ModelAttribute(value = "id") int id) {

        this.eServices.deleteByEmpId(id);
        return "redirect:/";
    }

    @GetMapping("/updateEmp/{id}")
    public String update(@PathVariable(value = "id") int id, Model m) throws Exception {

        m.addAttribute("emp", eServices.getbyEmpId(id));
        return "updateEmp";
    }

    // employee management via postman
    @GetMapping("/{id}")
    public ResponseEntity<Employee> book(@PathVariable("id") int id) throws Exception {
        Employee b = eServices.getbyEmpId(id);
        if (b == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(b));
    }

    @PostMapping("/employee")
    public ResponseEntity<Employee> add(@RequestBody Employee e) {
        // Employee emp=null;
        try {
            this.eServices.addEmp(e);
            return ResponseEntity.of(Optional.of(e));
        } catch (Exception exc) {
            exc.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {

        try {
            eServices.deleteByEmpId(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody Employee emp, @PathVariable("id") int id) throws Exception {
        // Employee e=null;
        try {
            // e=eServices.getbyEmpId(id);
            emp.setId(id);
            eServices.addEmp(emp);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        // emp.setId(id);
        // eServices.addEmp(emp);
    }

}