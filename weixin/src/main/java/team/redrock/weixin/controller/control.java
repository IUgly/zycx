package team.redrock.weixin.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.redrock.weixin.service.IService;
import team.redrock.weixin.service.Service;
import team.redrock.weixin.service.impl.IServiceImp;
import team.redrock.weixin.util.*;
import team.redrock.weixin.vo.Record;
import team.redrock.weixin.vo.User;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class control extends AbstractBaseController {
    @Autowired
    private IServiceImp iServiceImp;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("/select")
    public String Record(String uid){

        String resp = this.redisTemplate.opsForValue().get(uid);
        User user = this.iServiceImp.selectUser(uid);
        if (user==null){
            return Util.assembling("-2", "该学号还没有绑定志愿者账号", "");
        }
        if (resp!=null){
            return resp;
        }
        List<Record> recordList = this.iServiceImp.selectRecordList(uid);
        if (recordList.size()==0){
             String code = Service.login(user.getAccount(), user.getPassword());
             if (!code.equals("0")){
                 return Util.assembling("3", "该志愿者账号密码被修改，请重新绑定", "");
             }
            recordList =  ReptileUtil.detail(user.getAccount(), user.getPassword());
        }
        double allHours = 0.0;
        for (int i=0; i<recordList.size(); i++){
            Record record = recordList.get(i);
            record.setUid(uid);
            this.iServiceImp.insertRecord(record);

            allHours = allHours+Double.parseDouble(record.getHours());
        }
        JsonArray jsonArray = new Gson().toJsonTree
                (recordList, new TypeToken<List<Record>>() {}.getType()).getAsJsonArray();
        this.redisTemplate.opsForValue().set(uid, Util.message("0", "success", allHours, jsonArray));
        this.redisTemplate.expire(uid, 604800, TimeUnit.SECONDS);

        return Util.message("0", "success", allHours, jsonArray);
    }
    @PostMapping("/binding")
    public String binding(@RequestBody User user){
        String code = Service.login(user.getAccount(), user.getPassword());
        if (code.equals("0")){
            if (this.iServiceImp.selectUser(user.getUid())==null){
                this.iServiceImp.insertBind(user);
            }else {
                this.iServiceImp.updateUser(user);
            }
            return Util.assembling("0","success", "");
        }else {
            return Util.assembling("-1", "志愿者账号密码错误","");
        }
    }
}