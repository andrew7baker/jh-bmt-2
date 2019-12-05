package com.mycompany.myapp.web.rest;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.net.ftp.*;
import org.jdbi.v3.core.Handle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.jdbi.v3.core.Jdbi;

import java.io.*;
import java.util.List;

import java.text.MessageFormat;


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
        long startTimeMillis = System.currentTimeMillis();
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

        }

        try (FileWriter file = new FileWriter("payReport.json")) {

            file.write("");
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        String FTP_HOST  = "";
        int FTP_PORT = 21;
        String FTP_USER  = "your_username";
        String FTP_PASS = "your_password";
        String FTP_REMOTE_DIRECTORY = "/2-yimi";
        String FTP_LOCAL_DOWNLOAD_DIR = ".";

        List<String> list =jdbi.withHandle(handle ->
            handle.createQuery("select value from SYS_CONFIG where code = ?")
                .bind(0, "FTP_USER")
                .mapTo(String.class)
                .list());
        log.info("list="+list);
        if(list.size()>0){
            FTP_USER  = list.get(0);
        }

        list =jdbi.withHandle(handle ->
            handle.createQuery("select value from SYS_CONFIG where code = ?")
                .bind(0, "FTP_PASS")
                .mapTo(String.class)
                .list());
        log.info("list="+list);
        if(list.size()>0){
            FTP_PASS = list.get(0);
        }

        list =jdbi.withHandle(handle ->
            handle.createQuery("select value from SYS_CONFIG where code = ?")
                .bind(0, "FTP_HOST")
                .mapTo(String.class)
                .list());
        log.info("list="+list);
        if(list.size()>0){
            FTP_HOST = list.get(0);
        }


//  https://gist.github.com/nabil-hassan/7e7c6700eee628a7c491
        FTPClient ftp = new FTPClient();

        try {
            ftp = new FTPHTTPClient(FTP_HOST , FTP_PORT, FTP_USER , FTP_PASS);
            log.info("ftp="+ftp);
            ftp.changeWorkingDirectory(FTP_REMOTE_DIRECTORY);
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                throw new RuntimeException("Cannot change to FTP directory: " + FTP_REMOTE_DIRECTORY);
            }

            // Iteratively download all files in the directory.
            for (FTPFile file : ftp.listFiles()) {
                System.out.println(MessageFormat.format("Transferring remote file: {0} to local directory: {1}",
                    file.getName(), FTP_LOCAL_DOWNLOAD_DIR));
                File target = new File(FTP_LOCAL_DOWNLOAD_DIR + file.getName());
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(target));
                ftp.retrieveFile(file.getName(), outputStream);
                outputStream.close();

                if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                    System.out.println(MessageFormat.format("Download for file: {0} failed", file.getName()));
                }
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }finally {
            // Calculate time taken
            long endTimeMillis = System.currentTimeMillis();
            long totalTimemillis = endTimeMillis - startTimeMillis;
            System.out.println(MessageFormat.format("Upload process took {0} ms", totalTimemillis));
            try {
                if (ftp.isConnected()) {
                    ftp.logout();
                    ftp.disconnect();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return "bmtReportResource";
    }

}
