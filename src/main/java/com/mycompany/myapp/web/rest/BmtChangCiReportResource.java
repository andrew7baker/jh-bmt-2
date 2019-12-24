package com.mycompany.myapp.web.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
//import com.mycompany.myapp.util.ResponseJsonUtils;
import org.apache.commons.net.ftp.*;
import org.jdbi.v3.core.Handle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.jdbi.v3.core.Jdbi;

import java.io.*;
import java.util.*;

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

        String jsonStr = "[{\"JACKIE_ZHANG\":\"张学友\"},{\"ANDY_LAU\":\"刘德华\"},{\"LIMING\":\"黎明\"},{\"Aaron_Kwok\":\"郭富城\"}]" ;
        //做5次测试


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
        String FTP_REMOTE_DIRECTORY = "/";
        String FTP_LOCAL_DOWNLOAD_DIR = "d:/download/";

        List<String> list =jdbi.withHandle(handle ->
            handle.createQuery("select value from SYS_CONFIG where code = ?")
                .bind(0, "FTP_USER")
                .mapTo(String.class)
                .list());
        FTP_USER  = list.size()>0?list.get(0):"";

        list =jdbi.withHandle(handle ->
            handle.createQuery("select value from SYS_CONFIG where code = ?")
                .bind(0, "FTP_PASS")
                .mapTo(String.class)
                .list());
        FTP_PASS  = list.size()>0?list.get(0):"";

        list =jdbi.withHandle(handle ->
            handle.createQuery("select value from SYS_CONFIG where code = ?")
                .bind(0, "FTP_REMOTE_DIRECTORY")
                .mapTo(String.class)
                .list());
        FTP_REMOTE_DIRECTORY  = list.size()>0?list.get(0):"";

        list =jdbi.withHandle(handle ->
            handle.createQuery("select value from SYS_CONFIG where code = ?")
                .bind(0, "FTP_HOST")
                .mapTo(String.class)
                .list());
        FTP_HOST  = list.size()>0?list.get(0):"";

//  https://gist.github.com/nabil-hassan/7e7c6700eee628a7c491
        FTPClient ftp = new FTPClient();
        PrintWriter writer = new PrintWriter(System.out);
        try {
//            ftp = new FTPHTTPClient(FTP_HOST , FTP_PORT, FTP_USER , FTP_PASS);
            // Redirect FTP commands to stdout if flag set.
//            if (FTP_PROTOCOL_DEBUGGING) {
//                ftp.addProtocolCommandListener(new PrintCommandListener(writer));
//            }

            // Connect/login.
            System.out.println(MessageFormat.format("Connecting to ftp host: {0} on port: {1}",
                FTP_HOST, FTP_PORT));
            ftp.connect(FTP_HOST, FTP_PORT);
            boolean success =ftp.login(FTP_USER, FTP_PASS);
            log.info("\n 连接返回 {}", success);
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                throw new RuntimeException("不能连接主机 ====== " + FTP_HOST);
            }
            ftp.changeWorkingDirectory(FTP_REMOTE_DIRECTORY);
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                throw new RuntimeException("不能改变FTP目录 ====== " + FTP_REMOTE_DIRECTORY);
            }
            int replyCode = ftp.getReplyCode();
            FTPFile[] files = ftp.listFiles();
            System.out.println("files="+ Arrays.toString(files));

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
            log.debug("\n FTP耗时: {}  ", totalTimemillis);
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

    @GetMapping("/bmt-report")
    public String bmtGetReport() {
        Map<String, Object> data = new HashMap<String, Object>();

        Map<String, Object> data1 = new HashMap<String, Object>();
        data1.put("changDiName","2号场地周四");

        Map<String, Object> payChangCi1 = new HashMap<String, Object>();
        payChangCi1.put("changCiName","12月19日");

        Map<String, Object> payRecord = new HashMap<String, Object>();
        payRecord.put("payPersonName","93社员");
        payRecord.put("payAmount","35");
        Map<String, Object> payRecord1 = new HashMap<String, Object>();
        payRecord1.put("payPersonName","老李");
        payRecord1.put("payAmount","35");
        payChangCi1.put("payRecords",new Object[]{payRecord,payRecord1});
        data1.put("payChangCis",new Object[]{payChangCi1});

        Map<String, Object> data2 = new HashMap<String, Object>();
        data2.put("changDiName","5号场地周日");
        data.put("payChangDis",new Object[]{data1,data2});
        String s = JSON.toJSONString(data);
        return s;
    }

}
