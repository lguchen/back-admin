package com.gu.backadmin.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;
/**
 * @Description
 * @Author: luo
 * @Date 2023年01月26日 23:52:08
 */


/**
 * 生成token
 *
 * @return
 */
public class TokenUtils {
    public static String getToken(String userId, String password) {
        return JWT.create().withAudience(userId) // 将 user id 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) //2小时后token过期
                .sign(Algorithm.HMAC256(password)); // 以 password 作为 token 的密钥
    }
}