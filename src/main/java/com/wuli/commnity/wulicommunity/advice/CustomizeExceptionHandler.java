package com.wuli.commnity.wulicommunity.advice;

import com.alibaba.fastjson.JSON;
import com.wuli.commnity.wulicommunity.dto.FileDTO;
import com.wuli.commnity.wulicommunity.dto.ResultDTO;
import com.wuli.commnity.wulicommunity.exception.CustomizeErrorCode;
import com.wuli.commnity.wulicommunity.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable ex, Model model , HttpServletResponse response) {
        HttpStatus status = getStatus(request);
        String contentype=request.getContentType();
        System.out.println(contentype);
        if ("application/json".equals(contentype))
        {
            ResultDTO resultDTO=null;
            if (ex instanceof CustomizeException)
            {
               resultDTO=ResultDTO.errorOf((CustomizeException) ex);
            }
            else
            {
                resultDTO=ResultDTO.errorOf(CustomizeErrorCode.CESHI);
            }
            try {
                response.setStatus(200);
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                PrintWriter printWriter=response.getWriter();
                printWriter.write(JSON.toJSONString(resultDTO));
                printWriter.close();
            } catch (IOException e) {

            }
            return null;
        }
        else if(contentype.contains("multipart/form-data")){
            System.out.println(contentype);
            MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest) request;
            MultipartFile file= multipartHttpServletRequest.getFile("editormd-image-file");
            FileDTO fileDTO=new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setMessage("violent");
            fileDTO.setUrl("/picture/"+file.getOriginalFilename());
            System.out.println("success");
            System.out.println("fail");
            try {
                response.setStatus(200);
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                PrintWriter printWriter=response.getWriter();
                printWriter.write(JSON.toJSONString(fileDTO));
                printWriter.close();
            } catch (IOException e) {

            }
            return null;
        }
        else
        {
            System.out.println("111");
            if (ex instanceof CustomizeException)
            {
                model.addAttribute("message",ex.getMessage());
            }
            else
            {
                System.out.println("222");
                model.addAttribute("message","服务器撑不住了>_<!!!");
            }

            return new ModelAndView("error");
        }

    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
