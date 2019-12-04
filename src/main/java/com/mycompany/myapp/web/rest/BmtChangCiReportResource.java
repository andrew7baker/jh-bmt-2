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


        return "bmtReportResource";
    }

}
