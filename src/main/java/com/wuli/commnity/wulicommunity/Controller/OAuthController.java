package com.wuli.commnity.wulicommunity.Controller;

import com.wuli.commnity.wulicommunity.Provider.GithubProvider;
import com.wuli.commnity.wulicommunity.dto.AccessTokenDTO;
import com.wuli.commnity.wulicommunity.dto.GithubUser;
import com.wuli.commnity.wulicommunity.mapper.UserMapper;
import com.wuli.commnity.wulicommunity.model.User;
import com.wuli.commnity.wulicommunity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class OAuthController {
    @Autowired
   private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.client.uri}")
    private String clientUri;
   @Autowired(required = false)
    private UserMapper userMapper;
   @Autowired(required = false)
   private UserService userService;
    @RequestMapping("/callback")
    public String CallBack(@RequestParam(name = "code")String code,
                           @RequestParam(name="state")String state,
                           HttpServletResponse response)
    {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(clientUri);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser=githubProvider.getUser(accessToken);
        //System.out.println(githubUser.getName());
        if(githubUser!=null)
        {
            User user=new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccount_id(String.valueOf(githubUser.getId()));
            user.setTime_create(System.currentTimeMillis());
            user.setTime_update(user.getTime_create());
            user.setAvatarUri(githubUser.getAvatar_url());
            //userMapper.insert(user);
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token",user.getToken()));
            //request.getSession().setAttribute("myuser",githubUser);
            return "redirect:/";
        }
        else
        {
            return "redirect:/";
        }

    }
    @RequestMapping("/logout")
        public String logout(HttpServletRequest request,
                             HttpServletResponse response)
        {
            request.getSession().removeAttribute("myuser");
            request.getSession().removeAttribute("myId");
            Cookie cookie=new Cookie("token",null);
            cookie.setMaxAge(0);
            //cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:/";
        }
}
