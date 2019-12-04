package com.mycompany.myapp.web.rest;

import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.jdbi.v3.core.Jdbi;

import java.io.FileWriter;
import java.io.IOException;


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

        //https://www.programcreek.com/java-api-examples/?api=com.alibaba.fastjson.JSON

        //https://www.cnblogs.com/pcheng/p/6875760.html

        String jsonStr = "[{\"JACKIE_ZHANG\":\"张学友\"},{\"ANDY_LAU\":\"刘德华\"},{\"LIMING\":\"黎明\"},{\"Aaron_Kwok\":\"郭富城\"}]" ;
        //做5次测试
        for(int i=0,j=5;i<j;i++)
        {
            JSONArray jsonArray = JSONArray.parseArray(jsonStr);

            for(int k=0;k<jsonArray.size();k++){
                System.out.print(jsonArray.get(k) + "\t");
            }
            System.out.println();//用来换行
        }


    //First Employee
//        JSONObject employeeDetails = new JSONObject();
//        employeeDetails.put("firstName", "Lokesh");
//        employeeDetails.put("lastName", "Gupta");
//        employeeDetails.put("website", "howtodoinjava.com");
//
//        JSONObject employeeObject = new JSONObject();
//        employeeObject.put("employee", employeeDetails);
//
//        //Second Employee
//        JSONObject employeeDetails2 = new JSONObject();
//        employeeDetails2.put("firstName", "Brian");
//        employeeDetails2.put("lastName", "Schultz");
//        employeeDetails2.put("website", "example.com");
//
//        JSONObject employeeObject2 = new JSONObject();
//        employeeObject2.put("employee", employeeDetails2);
//
//        //Add employees to list
//        JSONArray employeeList = new JSONArray();
//        employeeList.add(employeeObject);
//        employeeList.add(employeeObject2);
//
//        //Write JSON file
        try (FileWriter file = new FileWriter("payReport.json")) {

            file.write("");
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "bmtReportResource";
    }

}
