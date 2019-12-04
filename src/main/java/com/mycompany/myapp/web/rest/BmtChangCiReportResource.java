package com.mycompany.myapp.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.jdbi.v3.core.Jdbi;


/**
 * BmtChangCiReportResource controller
 */
@RestController
@RequestMapping("/api/bmt-chang-ci-report")
public class BmtChangCiReportResource {

    private final Logger log = LoggerFactory.getLogger(BmtChangCiReportResource.class);

    /**
    * POST bmtReportResource
    */
    @PostMapping("/bmt-report-resource")
    public String bmtReportResource() {
        Jdbi jdbi = Jdbi.create("jdbc:mysql://localhost:3306/bmt?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
        log.info("jdbi"+jdbi);

        //https://howtodoinjava.com/library/json-simple-read-write-json-examples/

        //https://www.programcreek.com/java-api-examples/index.php?api=com.fasterxml.jackson.core.JsonProcessingException

        //First Employee
        JSONObject employeeDetails = new JSONObject();
        employeeDetails.put("firstName", "Lokesh");
        employeeDetails.put("lastName", "Gupta");
        employeeDetails.put("website", "howtodoinjava.com");

        JSONObject employeeObject = new JSONObject();
        employeeObject.put("employee", employeeDetails);

        //Second Employee
        JSONObject employeeDetails2 = new JSONObject();
        employeeDetails2.put("firstName", "Brian");
        employeeDetails2.put("lastName", "Schultz");
        employeeDetails2.put("website", "example.com");

        JSONObject employeeObject2 = new JSONObject();
        employeeObject2.put("employee", employeeDetails2);

        //Add employees to list
        JSONArray employeeList = new JSONArray();
        employeeList.add(employeeObject);
        employeeList.add(employeeObject2);

        //Write JSON file
        try (FileWriter file = new FileWriter("employees.json")) {

            file.write(employeeList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "bmtReportResource";
    }

}
