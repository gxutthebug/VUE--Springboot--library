package com.example.mylibrary.Controller;

import com.example.mylibrary.domain.Manage;
import com.example.mylibrary.domain.Student;
import com.example.mylibrary.service.imp.ManageServiceImpl;
import com.example.mylibrary.service.imp.StudentServiceImpl;
import com.example.mylibrary.utils.JwtUtils;
import com.example.mylibrary.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//@RequestMapping("/account")
@RestController
@CrossOrigin // 本地请求因为端口号不同,属于跨域请求需要打开跨域
public class LoginController {

    @Autowired
      private StudentServiceImpl studentService;

    @Autowired
      private ManageServiceImpl manageService;


    @PostMapping("/account/login")
    public Map<String,Object> login(@RequestBody Student student) {

        Map<String,Object> loginMap = new HashMap<>();  // 一个集合就是一个JS的纯属性对象，即一个{}里面没有方法!!!!!!

        Map<String,Object> dataMap = new HashMap<>();
        Map<String,Object> metaMap = new HashMap<>(); // 总的返回对象

//        System.out.println(loginUser.getName());
//        System.out.println(loginUser.getAge());
        System.out.println(student);


        Student loginstudent = studentService.selectByusername(student.getStuusername());

        System.out.println(student);
        System.out.println(loginstudent);


        if(loginstudent.getStupassword().equals(student.getStupassword())){
            System.out.println("这个学生登录成功了");
            dataMap.put("data",loginstudent);
            loginMap.put("data",dataMap); // 把dataMap放入loginMap,loginMap是最外层大括号键名为data

            metaMap.put("msg","学生登录成功");
            metaMap.put("status",200);
        } else {
            System.out.println("学生登录失败");
            metaMap.put("msg","登录失败，用户名或密码错误");
            metaMap.put("status",0);
        }

        loginMap.put("meta",metaMap);
        return  loginMap;
    }


    @PostMapping("/manage/login")
    public Result login2(@RequestBody Manage manage) {  // 这个接口用了封装好的Result类来做统一的响应
        System.out.println("lalla");
         Manage loginmanage = manageService.SelectBymgename(manage.getMgename());
        System.out.println(manage.getMgename());
        System.out.println(loginmanage);

        if(loginmanage.getPassword().equals(manage.getPassword())){
            System.out.println("密码正确");
            String token = JwtUtils.generateToken(manage.getMgename());
            return Result.ok().data("token",token);
        }else {
            return Result.error();
        }

    }


}
