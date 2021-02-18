package com.test;

import com.utils.JwtUtils;
import com.utils.Payload;
import com.utils.RsaUtils;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class MainTest {

    public static void main(String[] args) throws Exception {
        String privatePath = "./id_key_rsa";
        String publicPath = "./id_key_rsa.pub";
        // RsaUtils.generateKey(publicPath, privatePath, "zhangc476", 2048);

        PublicKey publicKey = RsaUtils.getPublicKey(publicPath);
        System.out.println(publicKey.toString());

        PrivateKey privateKey = RsaUtils.getPrivateKey(privatePath);
        System.out.println(privateKey.toString());


        Map<String, String> user = new HashMap<String, String>();
        user.put("logo", "la");
        Payload payload = new Payload();
        payload.setUserInfo(user);

        // 使用rsa私钥生成的jwtToken
        String jwtToken = JwtUtils.generateTokenExpireInMinutes(payload, privateKey, 5);
        System.out.println(jwtToken);

        // 使用rsa公钥解析jwtToken
        Payload<Object> payloadInfo = JwtUtils.getInfoFromToken(jwtToken, publicKey);
        System.out.println(payload);
    }
}