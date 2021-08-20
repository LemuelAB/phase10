package com.lagou.eduauthorityboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.lagou.eduauthorityboot.entity.UserDTO;
import com.lagou.eduauthorityboot.service.UserService;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@CrossOrigin
public class AuthorityContoller {

    @Value("${ali.sms.signName}")
    private String signName;

    @Value("${ali.sms.templateCode}")
    private String templateCode;

    @Value("${ali.sms.accessKeyId}")
    private String accessKeyId;

    @Value("${ali.sms.accessKeySecret}")
    private String accessKeySecret;

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @GetMapping("login")
    public UserDTO login(String phone, String password) {
        UserDTO dto = userService.login(phone, password);
        return dto;
    }

    @GetMapping("checkToken")
    public UserDTO checkToken(String token) {
        System.out.println("待校验的token = " + token);
        UserDTO dto = userService.checkToken(token);
        return dto;
    }
    @GetMapping("logout")
    public void logout(String token){
        redisTemplate.delete(token);
    }



    @GetMapping("sendSms")
    public boolean sendSms(String phoneNumber) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        String vcode = "";
        for(int i = 0; i<6; i++){
            vcode = vcode + (int)(Math.random()*9);
        }
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + vcode + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            String jsonStr = response.getData();
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            if("OK".equals(jsonObject.get("Message"))){
                return true;
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}