package hsh.hardwork.community.provider;


import com.alibaba.fastjson.JSON;
import hsh.hardwork.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import hsh.hardwork.community.dto.AccessTokenDto;

import java.io.IOException;

@Component
public class GithubProvider {
    /*
    通过getAccessToken函数获取github传过来的token。
     */
    public String getAccessToken(AccessTokenDto accessTokenDto) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDto));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string=response.body().string();
            String[] split = string.split("&");
            String tokenstr=split[0];
            String token=tokenstr.split("=")[1];//得到的string要进行拆分来得到最后的token
            System.out.println(string);
            System.out.println(token);
            return token;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
    /*
    把得到的token再传回github对应的接口,得到用户的信息。
     */
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String string=response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);//把string类型的json对象自动解析为定义的java类对象
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

}