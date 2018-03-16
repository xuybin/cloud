package people.data.cloud.zuul;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -3301605591108950415L;

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_AUDIENCE = "aud";
    static final String CLAIM_KEY_CREATED = "iat";

    static final String AUDIENCE_UNKNOWN = "unknown";
    static final String AUDIENCE_WEB = "web";
    static final String AUDIENCE_MOBILE = "mobile";
    static final String AUDIENCE_TABLET = "tablet";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String getAudienceFromToken(String token) {
        return getClaimFromToken(token, Claims::getAudience);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token) //忽略None算法
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //重置密码之后,之前的token失效,在这之后创建的才有效
    private Boolean isCreatedAfterLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset == null || created.equals(lastPasswordReset)|| created.after(lastPasswordReset));
    }
    //重新登之后,之前的token失效,在这之后创建的才有效
    private Boolean isCreatedAfterLastPasswordLogin(Date created, Date lastPasswordLogin) {
        return (lastPasswordLogin == null || created.equals(lastPasswordLogin)|| created.after(lastPasswordLogin));
    }


    private Boolean ignoreTokenExpiration(String token) {
        String audience = getAudienceFromToken(token);
        return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
    }

    public String generateToken( Date createdDate) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, "XYB", "ABC",createdDate);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject, String audience,Date createdDate) {
        final Date expirationDate = calculateExpirationDate(createdDate);
        return Jwts.builder()
                // .setId(UUID.randomUUID().toString())//一次性token时开启,存储KEY为Claims.ID->"jti"
                .setClaims(claims)
                .setSubject(subject)
                .setAudience(audience)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromToken(token);
        return isCreatedAfterLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public String refreshToken(String token) {
        final Date createdDate = new Date();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean validateToken(String token) {
        final String username = getUsernameFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);
        return true;
//        //final Date expiration = getExpirationDateFromToken(token);
//        return (
//                username.equals(dbUserDetails.getUsername())
//                        && !isTokenExpired(token)
//                        && isCreatedAfterLastPasswordLogin(created, dbUserDetails.getLastPasswordLoginDate())
//                        && isCreatedAfterLastPasswordReset(created, dbUserDetails.getLastPasswordResetDate())
//        );
    }

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }
}
