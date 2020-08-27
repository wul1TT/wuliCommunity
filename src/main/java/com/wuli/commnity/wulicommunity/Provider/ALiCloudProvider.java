package com.wuli.commnity.wulicommunity.Provider;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class ALiCloudProvider {

    String endpoint = "http://oss-cn-qingdao.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    String accessKeyId = "LTAI4GEuEvVysSa4aHNK6SUy";
    String accessKeySecret ="pUjcQZOWeGMWeXFhl9IW8oM4T1tdov";
       public String uploadAli(InputStream inputStream,String fileName)
    {

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        System.out.println("111");
        String [] spilter=fileName.split("\\.");
        String geneName="";
        if (spilter.length>1)
        {
            geneName=UUID.randomUUID().toString()+"."+spilter[spilter.length-1];
        }
        else
        {
            return null;
        }
       // String fileName = UUID.randomUUID().toString()+  substring;
        System.out.println(fileName);
        System.out.println("111");
        ossClient.putObject("wulibucket", geneName,new File("C:/Users/wuliTT/Pictures/Camera Roll/4.png"));
        System.out.println("222");
        return geneName;
    }



}
