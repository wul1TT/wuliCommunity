package com.wuli.commnity.wulicommunity.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PageDTO {
    public List<PostDTO> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDTO> posts) {
        this.posts = posts;
    }

    public boolean isShowPre() {
        return showPre;
    }

    public void setShowPre(boolean showPre) {
        this.showPre = showPre;
    }

    public boolean isShowFir() {
        return showFir;
    }

    public void setShowFir(boolean showFir) {
        this.showFir = showFir;
    }

    public boolean isShowNex() {
        return showNex;
    }

    public void setShowNex(boolean showNex) {
        this.showNex = showNex;
    }

    public boolean isShowEnd() {
        return showEnd;
    }

    public void setShowEnd(boolean showEnd) {
        this.showEnd = showEnd;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    private List<PostDTO> posts;
    private boolean showPre;
    private boolean showFir;
    private boolean showNex;
    private boolean showEnd;
    private Integer currentPage;
    private List <Integer> pages=new ArrayList<>();

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    private Integer count;
    public void setPagination(Integer totalCount, Integer page, Integer size) {
       if(totalCount==0)
       {
           showFir=false;
           showEnd=false;
           showPre=false;
           showNex=false;
           page=1;
       }
       else
       {
           if (page<1)
               page=1;

           Integer totalPage=0;
           if(totalCount%size==0)
           {
               totalPage=totalCount/size;

           }
           else
           {
               totalPage=totalCount/size+1;
           }
           count=totalPage;
           if(page>totalPage)
               page=totalPage;
           pages.add(page);
           currentPage=page;
           for(int i=1;i<=3;i++)
           {
               if(page-i>=1)
                   pages.add(page-i);
               if(page+i<=totalPage)
                   pages.add(page+i);
           }
           Collections.sort(pages);
           //展示上一页
           if(page==1)
           {
               showPre=false;
           }
           else
           {
               showPre=true;
           }
           //展示下一页
           if(page==totalPage)
           {
               showNex=false;
           }
           else
           {
               showNex=true;
           }
           //展示第一页
           if(pages.contains(1))
           {
               showFir=false;
           }
           else
           {
               showFir=true;
           }
           //展示最后一页
           if(pages.contains(totalPage))
           {
               showEnd=false;
           }
           else
           {
               showEnd=true;
           }
       }

    }
}
