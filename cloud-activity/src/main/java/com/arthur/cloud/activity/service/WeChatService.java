package com.arthur.cloud.activity.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arthur.cloud.activity.model.User;
import com.arthur.cloud.activity.util.HttpRequestUtils;
import com.arthur.cloud.activity.util.WeChatAuthProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description 微信授权登录
 * @create 2020-06-05 17:05
 * @Version 1.0
 **/
@Service
public class WeChatService {

    private static final Logger logger = LoggerFactory.getLogger(WeChatService.class);

    @Resource
    private UserService userService;

    /**
     * 服务器第三方session有效时间，单位秒, 默认1天
     */
    private static final Long EXPIRES = 86400L;

    private RestTemplate wxAuthRestTemplate = new RestTemplate();

    @Resource
    private WeChatAuthProperties wechatAuthProperties;



    /**
     * 微信登录获取oppenId uuid
     * @param code code
     * @return
     */
    public JSONObject getWxSession(String code){
        //微信登录的code值
        String wxCode = code;
        //服务器端调用接口的url
        String requestUrl = wechatAuthProperties.getSessionHost();
        //封装需要的参数信息
        Map<String,String> requestUrlParam = new HashMap<String,String>();
        //开发者设置中的appId
        requestUrlParam.put("appid",wechatAuthProperties.getAppId());
        //开发者设置中的appSecret
        requestUrlParam.put("secret",wechatAuthProperties.getSecret());
        //小程序调用wx.login返回的code
        requestUrlParam.put("js_code", wxCode);
        //默认参数
        requestUrlParam.put("grant_type", wechatAuthProperties.getGrantType());

        StringBuilder param = new StringBuilder();

        for (String key : requestUrlParam.keySet()) {
            param.append(key).append("=").append(requestUrlParam.get(key)).append("&");
        }

        JSONObject jsonObject = JSON.parseObject(HttpRequestUtils.sendGet(requestUrl, param.toString()));
        logger.info("授权返回信息:"+jsonObject.toString());
        return jsonObject;
    }

}
