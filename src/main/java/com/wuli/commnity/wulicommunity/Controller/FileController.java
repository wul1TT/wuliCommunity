package com.wuli.commnity.wulicommunity.Controller;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.wuli.commnity.wulicommunity.Provider.ALiCloudProvider;
import com.wuli.commnity.wulicommunity.dto.FileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Map;
import java.util.UUID;

@Controller
public class FileController {
    @Autowired
    ALiCloudProvider aLiCloudProvider;
    @RequestMapping(value = "/file/upload")
    @ResponseBody
    public FileDTO uploadimage(HttpServletRequest request)
    {

        MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest) request;
       MultipartFile file= multipartHttpServletRequest.getFile("editormd-image-file");
        try {
            aLiCloudProvider.uploadAli(file.getInputStream(),file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileDTO fileDTO=new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setMessage("violent");
        fileDTO.setUrl("/picture/"+file.getOriginalFilename());
        System.out.println("success");
        System.out.println("fail");
        return fileDTO;
    }
}
