package com.example.bbs.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jjwt工具类，用于生成token
 *
 * @author chenhuaqiang
 */
public class JjwtUtils {

    private static final String JWT_SECRET = "gjal*jkjljo_dsgf_pbwei_65fa9sdf_jcewd112gdsfd161bfof_1564d16";

    /**
     * 生成token
     *
     * @param userId 用户id
     * @param ttlMillis  过期时间
     * @return token
     * @throws Exception
     */
    public static String createJWT(Integer userId, long ttlMillis) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; //当前日期和时间初始化
        //签发jwt的时间
        long nowMillis = System.currentTimeMillis();
        SecretKey key = generalKey();

        Map<String, Object> map = new HashMap<String, Object>();
        JwtBuilder builder = Jwts.builder()
                .addClaims(map) //header
                //存入用户信息
                .claim("userId", userId) //载荷
                .setIssuedAt(new Date())
                .signWith(signatureAlgorithm, key)  //签名
                .setExpiration(new Date(nowMillis + ttlMillis));
        return builder.compact();
    }

    /**
     * 生成key
     *
     * @return
     */
    private static SecretKey generalKey() {
        //秘钥
        String stringKey = JjwtUtils.JWT_SECRET;
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 解析token
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey key = generalKey();
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

}
