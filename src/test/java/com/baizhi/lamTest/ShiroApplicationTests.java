package com.baizhi.lamTest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class ShiroApplicationTests {
    public static void main(String[] args) {
        testlogin("xiaohei", "111111");
    }

    //    后台认证方法
    public static void testlogin(String name, String password) {
//        初始化安全管理器工厂
        IniSecurityManagerFactory factory =
                new IniSecurityManagerFactory("classpath:shiro.ini");
//        根据安全管理器工厂初始化安全管理器
        SecurityManager securityManager = factory.createInstance();
//        将安全管理器交给安全工具类
        SecurityUtils.setSecurityManager(securityManager);
//        根据安全工具类获取主体对象
        Subject subject = SecurityUtils.getSubject();
//        创建令牌 token = 身份信息(userName)+凭证信息(password)
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
//        认证
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            System.out.println("未知的账号异常   用户名不正确");
        } catch (IncorrectCredentialsException e) {
            System.out.println("不正确的凭证异常   密码错误");
        }
        boolean authenticated = subject.isAuthenticated();
        System.out.println("认证状态: " + authenticated);
        if (authenticated) {
//            获取角色,渲染页面
//            判断当前主体是否有该角色
            boolean admin = subject.hasRole("admin");
            System.out.println("当前主体是否有该角色:" + admin);
//            判断当前主体是否含有这些角色
            boolean[] booleans = subject.hasRoles(Arrays.asList("admin", "super"));
            for (boolean b : booleans) {
                System.out.println("当前主体是否含有该角色" + b);
            }
//            判断当前主体是否有这些所有的角色
            boolean b = subject.hasAllRoles(Arrays.asList("admin", "super"));
            System.out.println("角色授权状态" + b);
//            判断当前主体是否有该权限
            boolean permitted = subject.isPermitted("user:query");
            System.out.println("角色权限状态" + permitted);
//            判断当前主体是否有这些权限
            boolean all = subject.isPermittedAll("user:query", "admin:add", "admin:del");
            System.out.println("角色是否拥有这些权限状态" + all);
        }
    }
}
