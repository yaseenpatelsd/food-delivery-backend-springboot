package food_delivery_app_backend.Jwt;

import food_delivery_app_backend.Service.GlobalUserService.UserServiceIm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserServiceIm userService;

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    private static final List<String> EXCLUDE_PREFIXES = List.of(

            //user register flow
            "/user/login",
            "/user/register",
            "/user/account-verification",
            "/user/password-reset",
            "/user/request-otp",

            //owner register flow
            "/owner/register",
            "/owner/account-verification",
            "/owner/login",
            "/owner/request-otp",
            "/owner/reset-password",

            //delivery-partner register flow
            "/delivery/partner/register",
            "/delivery/partner/account-verification",
            "/delivery/partner/login",
            "/delivery/partner/request-otp",
            "/delivery/partner/reset-password",


            "/admin/register",
            "/account-verification",
            "/otp-request",
            "/password-reset",
            "/api/product/search",

            // SWAGGER
            "/swagger-ui",
            "/swagger-ui/",
            "/swagger-ui.html",
            "/swagger-ui/index.html",
            "/v3/api-docs",
            "/v3/api-docs/",
            "/v3/api-docs/swagger-config",
            "/swagger-resources",
            "/swagger-resources/",
            "/webjars/"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        for (String prefix : EXCLUDE_PREFIXES) {
            if (path.startsWith(prefix)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        String authHeader=request.getHeader("Authorization");
        String username=null;
        String token=null;

     if (authHeader!=null&& authHeader.startsWith("Bearer ")){
         token=authHeader.substring(7).trim();
         System.out.println("TOKEN = " + token);
         try {
             username=jwtUtil.extractUserNameFromToken(token);
         }catch (Exception e){
             try {
                 throw new Exception("Username cant be extract "+e.getMessage());
             } catch (Exception ex) {
                 throw new RuntimeException(ex);
             }
         }

         if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
             UserDetails user=userService.loadUserByUsername(username);

             if (jwtUtil.isValid(token)){
                 UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                 authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                 SecurityContextHolder.getContext().setAuthentication(authToken);
             }
         }
     }
     filterChain.doFilter(request,response);
    }
}
