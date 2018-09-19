package com.htsc.cams.controller;

import com.htsc.cams.dao.UserRepository;
import com.htsc.cams.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

/**
 * 用户操作
 */
@Api(value = "用户", description = "接口API")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository applicationUserRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @ApiOperation(value="获取用户列表")
    @GetMapping("/userList")
    @ResponseBody
    public Map<String, Object> userList(){
        List<User> myUsers = applicationUserRepository.findAll();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("users",myUsers);
        return map;
    }
    /**
     * 该方法是注册用户的方法，默认放开访问控制
     * @param user
     */
    @ApiOperation(value="注册用户")
    @PostMapping("/signup")
    public String signUp(@ApiParam(value = "用户实体类", required = true) @RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(user);
        return "success";
    }

    @ApiOperation(value="登录获取token")
    @PostMapping(value = "/login")
    public String login(@ApiParam(value = "用户实体类", required = true) @RequestBody User user) throws IOException {
        /**
         * 认证过程
         *   1、用户使用用户名和密码进行登录。
             2、Spring Security将获取到的用户名和密码封装成一个实现了Authentication接口的UsernamePasswordAuthenticationToken。
             3、将上述产生的token对象传递给AuthenticationManager进行登录认证。
             4、AuthenticationManager认证成功后将会返回一个封装了用户权限等信息的Authentication对象。
             5、通过调用SecurityContextHolder.getContext().setAuthentication(...)将AuthenticationManager返回的Authentication对象赋予给当前的SecurityContext。
         */
        Authentication auth = authenticationManager.authenticate(
                // 1 生成
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword(),
                        new ArrayList())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = Jwts.builder()
                .setSubject(auth.getName())
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                .signWith(SignatureAlgorithm.HS512, "MyJwtSecret") //采用什么算法是可以自己选择的，不一定非要采用HS512
                .compact();
        return token;
    }
}
