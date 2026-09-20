package org.example.campusmarket.controller;
import jakarta.validation.Valid;
import org.example.campusmarket.common.Result;
import org.example.campusmarket.entity.User;
import org.example.campusmarket.mapper.UserMapper;
import org.example.campusmarket.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.example.campusmarket.util.JwtUtil;
@RestController
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    public UserController(UserMapper userMapper, UserService userService , JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.jwtUtil=jwtUtil;
    }
    @GetMapping("/users/{id}")
    public Result<User> getUser(@PathVariable Long id){
        User user = userMapper.findById(id);
        if (user==null) {
            return Result.error(404, "用户不存在");
        }
        return Result.success(user);


    }
    @PostMapping("/users/register")
    public Result<?> register(@Valid @RequestBody User user){
        userService.register(user);
        return Result.success(null);
    }
    @PostMapping("/users/login")
    public Result<String> login(@Valid@RequestBody User user){
        User dbuser=userService.login(
                user.getUsername(),
                user.getPassword()

        );
        if (dbuser==null){
            return Result.error(400,"账号或密码错误");
        }
        String token= jwtUtil.generateToken(
                dbuser.getId(),
                dbuser.getUsername()
        );
        return Result.success(token);
    }
    @GetMapping("/users/me")
    public Result<String> getMyInfo(
            @RequestAttribute("userId") Long userId,
            @RequestAttribute("username") String username){
        String info ="userId="+userId+",username="+username;
        return Result.success(info);
    }
}
