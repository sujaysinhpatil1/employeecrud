package com.microservices.employeecrudop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class CacheManager {

    // cache is generated --> we can access this cache from controller

    @Autowired
    EmployeeRepository employeeRepository;

    // find all employee from DB and store them HashMap where key is empID and value is employee
    public static HashMap<Integer,Employee> cache = new HashMap<>();

    //real time automatic call --> automatically cache load
    // scheduled time expression
    @Scheduled(cron = "* * * * * *")    // every second it will get called
    public void loadCache()
    {
        System.out.println("Cache loading started");
        List<Employee> employeeList = employeeRepository.findAll();
        employeeList.forEach(employee -> cache.put(employee.getEmpId(),employee));  //short code (one line code)
        System.out.println("Cache loading ended");

    }


    // cache implement-->real time cron job-->DB record-->cache load-->return
    // reduce time for data search which are in millions
    // cache is DataStructure --> HashMap(key,value)-->ID(key),Employee(value)

}
