package com.xiexin.controller;

import com.xiexin.bean.Admin;
import com.xiexin.bean.AdminExample;
import com.xiexin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired(required = false)
    private AdminService adminService;
//登录
    @RequestMapping("/login")
    public Map login(Admin admin, HttpSession session) {
        Map codeMap = new HashMap();
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andAdminNameEqualTo(admin.getAdminName());
        criteria.andAdminPwdEqualTo(admin.getAdminPwd());
        List<Admin> admins = adminService.selectByExample(example);
        if (admins != null && admins.size() > 0) {
            Admin admin1 = admins.get(0);
            //把这个账号信息放到session中
            session.setAttribute("admin1", admin1);
            codeMap.put("code", 0);
            codeMap.put("msg", "登录成功");
            codeMap.put("adminName", admin1.getAdminName());
            return codeMap;
        } else {
            codeMap.put("code", 4001);
            codeMap.put("msg", "账号或者密码错误");
            return codeMap;
        }
    }
//注册
    @RequestMapping("/insert")
    public Map insert(@RequestBody Admin admin) { //  对象传参, 规则: 前端属性要和后台的属性一致!!!
        Map map = new HashMap();
        int i = adminService.insertSelective(admin);
        if (i > 0) {
            map.put("code", 200);
            map.put("msg", "添加成功");
            return map;
        } else {
            map.put("code", 400);
            map.put("msg", "添加失败,检查网络再来一次");
            return map;
        }
    }
    }
