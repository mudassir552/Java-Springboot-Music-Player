/*
 * package com.example.demo.JWT;
 * 
 * import java.security.Key; import java.sql.Date; import java.util.ArrayList;
 * import java.util.HashMap; import java.util.List; import java.util.Map; import
 * java.util.function.Function;
 * 
 * import org.springframework.security.core.userdetails.UserDetails;
 * 
 * import org.springframework.stereotype.Service;
 * 
 * import com.example.demo.Roles.Role; import com.example.demo.Users.User;
 * 
 * import io.jsonwebtoken.Claims; import io.jsonwebtoken.Jwts; import
 * io.jsonwebtoken.SignatureAlgorithm; import io.jsonwebtoken.io.Decoders;
 * import io.jsonwebtoken.security.Keys;
 * 
 * 
 * 
 * @Service public class Jwtservice {
 * 
 * public static final String SECRET =
 * "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437899994574775757677674758475857485585885";
 * public String generateToken(String userName ) { //List<String> rolesAsString
 * = convertRolesToString(role); // Convert Role objects to strings Map<String,
 * Object> claims = new HashMap<>(); //claims.put("roles",rolesAsString); return
 * createToken(claims, userName); }
 * 
 * private String createToken(Map<String, Object> claims, String userName) {
 * return Jwts.builder() .setClaims(claims) .setSubject(userName)
 * .setIssuedAt(new Date(System.currentTimeMillis())) .setExpiration(new
 * Date(System.currentTimeMillis() + 1000 * 60 * 30*30)) .signWith(getSignKey(),
 * SignatureAlgorithm.HS512) // <-- This can be helpful to you .compact();
 * 
 * }
 * 
 * private Key getSignKey() { byte[] keyBytes= Decoders.BASE64.decode(SECRET);
 * return Keys.hmacShaKeyFor(keyBytes); }
 * 
 * public String extractUsername(String token) { return extractClaim(token,
 * Claims::getSubject); }
 * 
 * public Date extractExpiration(String token) { return (Date)
 * extractClaim(token, Claims::getExpiration); }
 * 
 * public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
 * final Claims claims = extractAllClaims(token); return
 * claimsResolver.apply(claims); }
 * 
 * private Claims extractAllClaims(String token) { return Jwts .parserBuilder()
 * .setSigningKey(getSignKey()) .build() .parseClaimsJws(token) .getBody(); }
 * 
 * private Boolean isTokenExpired(String token) { return
 * extractExpiration(token).before(new Date(System.currentTimeMillis())); }
 * 
 * public Boolean validateToken(String token, UserDetails userDetails) { final
 * String username = extractUsername(token); return
 * (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); }
 * 
 * private List<String> convertRolesToString(List<Role> userRoles) { // Convert
 * Role objects to strings or extract role names as needed // Here's an example
 * assuming Role has a method "getRoleName()" // Modify this based on your Role
 * class structure List<String> rolesAsString = new ArrayList<>(); for (Role
 * role : userRoles) { rolesAsString.add(role.getRole()); // Assuming
 * getRoleName returns the role name } return rolesAsString; }
 * 
 * 
 * 
 * }
 * 
 */