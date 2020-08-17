
function post() {
    var post_id = $("#post_id").val();
    var content = $("#comment_content").val();
    var obj={};
    obj["post_id"]=post_id;
    obj["content"]=content;
    obj["type"]=1;
    if(!content)
    {
        alert("评论内容不能为空");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        data: JSON.stringify(obj),
        contentType : "application/json",
        success: function (response) {
            if (response.code==1999)
            {
                $("#comment_content").val("");
                window.location.reload();
            }
            else if(response.code==2000)
            {
               var isLogin=confirm(response.message);
               if(isLogin)
               {
                   window.open("https://github.com/login/oauth/authorize?client_id=Iv1.749148c7d9a1f9af&redirect_uri=http://127.0.0.1:8081/callback&scope=user&state=1");
                   window.localStorage.setItem("closable","true");

               }
            }
            else
            {
                alert(response.message);
            }

            console.log(response);
        },
        dataType: "json"
    });

}
function collapse(e) {
   // var targetId=date();
    // var targetId = document.getElementById("comment_second");
     var id=e.getAttribute("data-id");
     e.setAttribute("data-target","#"+id);
     var flag=e.getAttribute("data-collapse");

     if(flag)
     {
         e.classList.add("active");
         e.removeAttribute("data-collapse");
     }
     else
     {   e.setAttribute("data-collapse","collapse");
         e.classList.remove("active");
     }
     console.log(flag);
}