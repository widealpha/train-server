package cn.widealpha.train.util;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 * @author kmh
 * @date 2021/7/13
 */
public class JwtUtil {
    private static final String SECRET = "train_A_!_1"; //JWT签证密钥
    private static final String ROLE = "ROLE"; //Jwt中携带的身份key
    private static final String USER_ID = "USER_ID"; //Jwt中携带的用户ID的key
    private static final Long EXPIRATION = 60 * 60 * 24 * 7L; //过期时间7天
    public static final String TOKEN_HEADER = "Authorization"; //Header标识JWT
    public static final String TOKEN_PREFIX = "Bearer "; //JWT标准开头,注意空格

    /**
     * 创建JWT
     * @param username 账户名
     * @param userId 用户ID
     * @param roles 用户角色,以英文逗号(,)分隔开
     * @return 创建好的Token
     */
    public static String createToken(String username,Integer userId, String roles) {
        Map<String, Object> map = new HashMap<>();
        map.put(ROLE, roles);
        map.put(USER_ID, userId);
        return Jwts.builder().signWith(SignatureAlgorithm.HS256, SECRET)
                .setClaims(map).setSubject(username).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION * 1000))
                .compact();
    }

    /**
     * 根据token获取用户名
     * @param token JWT
     * @return 用户名
     */
    public static String getUsername(String token) {
        try {
            return getTokenBody(token).getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *  获取Token载体信息
     * @param token JWT
     * @return token携带的claim
     */
    private static Claims getTokenBody(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 获取token携带的用户角色列表
     * @param token JWT
     * @return 用户角色,以英文逗号(,)分隔开
     */
    public static String getUserRole(String token) {
        return (String) getTokenBody(token).get(ROLE);
    }

    /**
     * 获取token携带的用户ID
     * @param token JWT
     * @return 用户ID
     */
    public static Integer getUserId(String token){
        return (Integer) getTokenBody(token).get(USER_ID);
    }

    /**
     * 判断token是否国企
     * @param token JWT
     * @return 是否过期
     */
    public static Boolean isExpiration(String token) {
        try {
            return getTokenBody(token).getExpiration().before(new Date());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    /**
     * 获取签发日期
     * @param token JWT
     * @return 签发Token的日期
     */
    public static Date getIssuedAt(String token){
        return getTokenBody(token).getIssuedAt();
    }
}
