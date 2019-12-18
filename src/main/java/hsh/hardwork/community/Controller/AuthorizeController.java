package hsh.hardwork.community.Controller;

import hsh.hardwork.community.dto.AccessTokenDto;
import hsh.hardwork.community.dto.GithubUser;
import hsh.hardwork.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    /*
    与github用户登陆授权对应的接口，自己可以定义
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name= "state") String state){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirct_uri("http://localhost:8887/callback");
        accessTokenDto.setState(state);
        accessTokenDto.setClient_id("2b7200c214be214b9295");
        accessTokenDto.setClient_secret("f64bf5dcfec9405cbac1ded65f720a543b07d4af");

        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getId());

        return "index";
    }

}
