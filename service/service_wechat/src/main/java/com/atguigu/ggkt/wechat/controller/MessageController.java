package com.atguigu.ggkt.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.wechat.service.MessageService;
import com.atguigu.ggkt.wechat.utils.SHA1;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wechat/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    private static final String token = "ggkt";


    @GetMapping
    public String verifyToken(HttpServletRequest request) {
        String signature = request.getParameter("signature");
        String timeStamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if (this.checkSignature(signature, timeStamp, nonce)) {
            return echostr;
        }
        return echostr;
    }

    @GetMapping("/pushPayMessage")
    public Result pushPayMessage() throws WxErrorException{
        messageService.pushPayMessage(1L);
        return Result.ok(null);
    }

    //接受微信服务器发送过来的消息
    @PostMapping
    public String receiveMessage(HttpServletRequest request) throws Exception{
//        WxMpXmlMessage wxMpXmlMessage = WxMpXmlMessage.fromXml(request.getInputStream());
//        System.out.println(JSONObject.toJSONString(wxMpXmlMessage));

        Map<String ,String> param = this.parseXml(request);
        System.out.println(param);
        String message = messageService.receiveMessage(param);
        System.out.println(message);
        return message;
    }


    //TODO:看懂理解
    private Map<String, String> parseXml(HttpServletRequest request)throws Exception{
        Map<String ,String>map = new HashMap<String, String>();
        InputStream inputStream = request.getInputStream();
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();
        for (Element e :
                elementList) {
            map.put(e.getName(), e.getText());
        }
        inputStream.close();
        inputStream = null;
        return map;
    }

    private boolean checkSignature(String signature, String timestamp, String nonce) {

        String[] str = new String[]{token, timestamp, nonce};
        //排序
        Arrays.sort(str);
        //拼接字符串
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < str.length; i++) {
            buffer.append(str[i]);
        }
        //进行sha1加密
        String temp = SHA1.encode(buffer.toString());
        //与微信提供的signature进行匹配
        return signature.equals(temp);
    }
}
