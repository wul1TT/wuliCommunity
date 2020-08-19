package com.wuli.commnity.wulicommunity.cache;

import com.wuli.commnity.wulicommunity.dto.CacheDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagCache {
    public static List<CacheDTO> get()
    {
        List<CacheDTO> cacheDTOs=new ArrayList<>();
        for(int i=0;i<4;i++)
        {
            if (i==0)
            {
                CacheDTO cacheDTO=new CacheDTO();
                cacheDTO.setTabName("开发语言");
                cacheDTO.setTags(Arrays.asList("Java","Python","Golang","C++","Html","Css","JavaScript","node.js"));
                cacheDTOs.add(cacheDTO);
            }
            if (i==1)
            {
                CacheDTO cacheDTO=new CacheDTO();
                cacheDTO.setTabName("框架技术");
                cacheDTO.setTags(Arrays.asList("Spring","SpringBoot","Mybatis","Lombok"));
                cacheDTOs.add(cacheDTO);
            }
            if (i==2)
            {
                CacheDTO cacheDTO=new CacheDTO();
                cacheDTO.setTabName("数据库");
                cacheDTO.setTags(Arrays.asList("MySQL","Redis","Oracle","MongoDB"));
                cacheDTOs.add(cacheDTO);
            }
            if (i==3)
            {
                CacheDTO cacheDTO=new CacheDTO();
                cacheDTO.setTabName("日常");
                cacheDTO.setTags(Arrays.asList("面经","基础知识点","其他"));
                cacheDTOs.add(cacheDTO);
            }

        }
        return cacheDTOs;
    }
}
