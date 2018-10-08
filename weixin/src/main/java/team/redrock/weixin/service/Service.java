package team.redrock.weixin.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import team.redrock.weixin.dao.Dao;
import team.redrock.weixin.util.ReptileUtil;
import team.redrock.weixin.util.Util;
import team.redrock.weixin.vo.Record;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {
    public static String login(String account, String password){
        HttpClient httpClient = new HttpClient();
        String url = "http://www.zycq.org/app/user/login.php?m=login";
        HttpMethod method = HttpMethod.POST;
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("uname", account);
        params.add("upass", Util.getRSA(password));
        params.add("referer", "http%253A%252F%252Fwww.zycq.org%252Fapp%252Fuser%252Fhour.php");
        String str = httpClient.client(url, method, params);

        JsonObject jsonObject = new JsonParser().parse(str).getAsJsonObject();
        String code = jsonObject.get("code").getAsString();
        return code;
    }
}
