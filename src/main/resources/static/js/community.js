
function post() {
    var post_id = $("#post_id").val();
    var content = $("#comment_content").val();
   comment2target(post_id,1,content);

}
function comment(e) {
    var targetId=e.getAttribute("data-id");
    var content=$("#reply-"+targetId).val();
    comment2target(targetId,2,content);
}
function comment2target(targetId,type,content) {
    var post_id = targetId
    var content = content
    var obj={};
    obj["post_id"]=post_id;
    obj["content"]=content;
    obj["type"]=type;
    var httptype;
    var urltype;
  //  if(type==1)
   // {
        httptype="POST";
        urltype="/comment";
   // }
   // else
   // {
       // httptype="GET"
       // urltype="/comment/"+targetId;
   // }
    if(!content)
    {
        alert("评论内容不能为空");
        return;
    }
    $.ajax({
        type: httptype,
        url: urltype,
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

           // console.log(response);
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
     console.log(flag);
     if(flag=="collapse")
     {
         var subCommentContainer = $("#" + id);
         if (subCommentContainer.children().length != 1) {
             e.classList.add("active");
             e.setAttribute("data-collapse","collapsein");
         }
         else
         {
             $.getJSON( "/comment/"+id, function( data ) {
                 console.log(data);
                 $.each(data.data.reverse(), function (index, comment) {
                 var mediaLeftElement = $("<div/>", {
                     "class": "media-left"
                 }).append($("<img/>", {
                     "class": "media-object img-obj",
                     "src": comment.user.avatarUri
                 }));

                 var mediaBodyElement = $("<div/>", {
                     "class": "media-body"
                 }).append($("<h5/>", {
                     "class": "media-heading",
                     "html": comment.user.name
                 })).append($("<div/>", {
                     "html": comment.content
                 })).append($("<div/>", {
                     "class": "menu"
                 }).append($("<span/>", {
                     "class": "pull-right",
                     "style":"margin-bottom: 10px",
                     "html": moment(comment.gmt_create).format('YYYY-MM-DD')
                 })));

                 var mediaElement = $("<div/>", {
                     "class": "media"
                 }).append(mediaLeftElement).append(mediaBodyElement);

                 var commentElement = $("<div/>", {
                     "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                 }).append(mediaElement);
                 subCommentContainer.prepend(commentElement);
             });
             e.classList.add("active");
             e.setAttribute("data-collapse","collapsein");
             });
         }
     }
     else
     {
         e.setAttribute("data-collapse","collapse");
         e.classList.remove("active");
     }

}

function selectTag(e) {
 var preTag=$("#tag").val();
 var nowTag=e.getAttribute("data-tag");
 if(preTag!=null)
 {
     if (preTag.indexOf(nowTag)!==-1)
     {

     }
     else
     {
         $("#tag").val(preTag+' '+nowTag);
     }

 }
 else
 {
     $("#tag").val(nowTag);
 }

}
function removeTag(e) {
var preTag=$("#tag").val();
var nowTag=e.getAttribute("data-tag");
console.log(nowTag);
if(preTag!=null)
{

    if (preTag.indexOf(" "+nowTag)!==-1)
    {
        preTag=preTag.replace(" "+nowTag,"");
        $("#tag").val(preTag);
        console.log(preTag);
    }
    else if (preTag.indexOf(nowTag)!==-1)
    {
        preTag=preTag.replace(nowTag,"");
        $("#tag").val(preTag);
    }
}
}
function displayTab() {
    //var displayT=$("#Tab").style.display;

    //if(displayT==="none")
    //{
        $("#Tab").show();
    //}
    //else
   // {
    //    $("#Tab").style.display="none";
   // }

}