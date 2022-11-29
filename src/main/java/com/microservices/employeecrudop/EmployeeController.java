package com.microservices.employeecrudop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    //Hello World api
    @GetMapping("/getMsg")
    public String getHelloWorld(){
        return "Hello World";
    }

    // HTTP methods --> GET, PUT, POST, DELETE
    // CRUD - create read update delete

    @PostMapping("/createEmp")
    public Employee createEmployee(@RequestBody Employee employee){
        try {
            return employeeRepository.save(employee);
        }catch (Exception e){
            return null;
        }
    }

    @GetMapping("/findAllEmp")
    public String findAllEmp(){
        // return new ResponseEntity<List<Employee>>(employeeRepository.findAll(), HttpStatus.FOUND);
        // cache call --> return list from cache
        List<Integer> empIDList = CacheManager.cache.keySet().stream().collect(Collectors.toList());
        return CacheManager.cache.values().stream().collect(Collectors.toList()).toString();
    }

//    @GetMapping("/findEmpByID/{empID}")
//    public Optional<Employee> findEmpByID(@PathVariable int empID){
//        return employeeRepository.findById(empID);
//        return employeeRepository.findById(empID).get();  // if we don't want to use optional class
//    }

    @GetMapping("/findEmpByID/{empID}")
    public String findEmpByID(@PathVariable int empID)
    {
        // cache call --> return list from cache
        // don't make a DB call instead just fetch employee data from cache
        // Optional<Employee> employee = employeeRepository.findById(empID);
        Employee employee = CacheManager.cache.get(empID);
        if(employee != null){
            return employee.toString();
        }else {
            return "No record was found for given empID";
        }
    }

//    update
    @PutMapping("/updateEmp/{empID}")
    public String updateEmp(@PathVariable int empID, @RequestBody Employee employee) {
        Optional<Employee> employee1 = employeeRepository.findById(empID);
        if (employee1.isPresent()) {
            //transformation --> make changes in old record
            Employee updateEmp = employee1.get();
            updateEmp.setEmpName(employee.getEmpName());
            updateEmp.setEmpAddress(employee.getEmpAddress());
            return employeeRepository.save(updateEmp).toString();
        } else {
            return "Employee with given empID is not present";
        }
    }

    @DeleteMapping("/deleteEmp/{empID}")
    public  String deleteEmp(@PathVariable int empID){
        Optional<Employee> employee = employeeRepository.findById(empID);
        if(employee.isPresent()){
            employeeRepository.deleteById(empID);
            return "Employee is deleted";
        }else {
            return "Employee is unable to delete as employee is not present";
        }
    }




//    Date date = new Date();
//    LocalTime time = new LocalTime();
//    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//    Date date = new Date();
//    System.out.println(formatter.format(date));
//    @GetMapping("/getMsg1")
//    public Date getDate() {
//        return date;
//    }




}
