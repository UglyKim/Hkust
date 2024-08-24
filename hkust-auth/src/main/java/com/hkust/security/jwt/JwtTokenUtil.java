package com.hkust.security.jwt;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hkust.entity.User;
import com.hkust.entity.UserExts;
import com.hkust.mapper.UserExtsMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.hkust.security.CustomUserDetails;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirMc}")
    private Long expirMC;

    @Value("${jwt.expirSc}")
    private Long expirSC;

    private UserExtsMapper userExtsMapper;

    public String generateToken(CustomUserDetails userDetails, String channel) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKey secretKey = Keys.hmacShaKeyFor(Base64.getUrlDecoder().decode(secret));
        Map<String, Object> claims = new HashMap<>();

        QueryWrapper<UserExts> wrapper = new QueryWrapper();
        wrapper.eq("student_id", userDetails.getUser().getStudentId());
        wrapper.eq("channel", channel);
        UserExts userExts = userExtsMapper.selectOne(wrapper);
        claims.put("version", userExts.getVersion());
        claims.put("channel", channel);

        Date expirDate = null;
        if (channel.equals("sc")) {
            expirDate = new Date(System.currentTimeMillis() + expirSC);
        }
        if (channel.equals("mc")) {
            expirDate = new Date(System.currentTimeMillis() + expirMC);
        }
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUser().getStudentId())
                .setIssuedAt(new Date())
                .setExpiration(expirDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
        log.info("created token:{}", token);
        return token;
    }

    public Boolean validateToken(String token, String requestURI, CustomUserDetails userDetails) {
        // 找到第一个和第二个斜杠的位置
        int firstSlash = requestURI.indexOf('/', 1); // 从第一个字符开始找
        int secondSlash = requestURI.indexOf('/', firstSlash + 1);

        // 提取两个斜杠之间的部分
        String requestChannel = requestURI.substring(firstSlash + 1, secondSlash);
        log.info("request_channel:{}", requestChannel);

        User user = userDetails.getUser();
        // 从token获取studentId
        final String studentId = getUsernameFromToken(token);
        // 从token获取channel, 验证登陆源是否一样
        final String channel = extractChannel(token);
        log.info("channel:{}", channel);

        // 数据库查询user_exts
        QueryWrapper<UserExts> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id", user.getStudentId());
        wrapper.eq("channel", requestChannel);
        UserExts userExts = userExtsMapper.selectOne(wrapper);

        final int version = extractVersion(token);
        return studentId.equals(user.getStudentId()) && !isTokenExpired(token)
                && userExts.getVersion() == version && channel.equals(userExts.getChannel());
    }

    private int extractVersion(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return (int) claims.get("version");
    }

    private String extractChannel(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return (String) claims.get("channel");
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Base64.getUrlDecoder().decode(secret));
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Autowired
    public void setUserExtsMapper(UserExtsMapper userExtsMapper) {
        this.userExtsMapper = userExtsMapper;
    }
}
