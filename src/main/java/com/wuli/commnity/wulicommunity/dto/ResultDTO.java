package com.wuli.commnity.wulicommunity.dto;

import com.wuli.commnity.wulicommunity.exception.CustomizeErrorCode;
import com.wuli.commnity.wulicommunity.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDTO {
    private Integer code;
    private String message;
    public static ResultDTO errorOf(Integer code,String message)
    {
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;

    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(errorCode.getCode());
        resultDTO.setMessage(errorCode.getMessage());
        return resultDTO;
    }
    public static ResultDTO okOf()
    {
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(1999);
        resultDTO.setMessage("发布成功");
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeException ex) {
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(ex.getCode());
        resultDTO.setMessage(ex.getMessage());
        return resultDTO;

    }
}
