package com.baizhi.lamTest;

import com.baizhi.UserS.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.Arrays;

public class MyRealm extends AuthorizingRealm {
    //    认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        强转
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
//        获取用户名
        String username = upToken.getUsername();
//        查询数据库
        User user = new User(
                "1", "xiaohei",
                "c3f2b09474f65a0bb8eda78e3682955f",
                "abcd");
//
        AuthenticationInfo info = null;
        if (username.equals(user.getName())) {
            info = new SimpleAuthenticationInfo(
                    user.getName(),
                    user.getPassword(),
                    ByteSource.Util.bytes("abcd"),
                    this.getName()
            );
        }
        return info;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        获取用户主身份
        String username = (String) principalCollection.getPrimaryPrincipal();
//        值
        User user = new User(
                "1", "xiaohei",
                "c3f2b09474f65a0bb8eda78e3682955f",
                "abcd");
//        判断授权
        SimpleAuthorizationInfo info = null;
        if (username.equals(user.getName())) {
//            设置配置权限
            info = new SimpleAuthorizationInfo();
//            给当前主体赋予一个角色
//            info.addRole("admin");
//            给当前主体赋予多个角色
//            info.addRoles(Arrays.asList("admin","admins"));
//            给当前主体赋予一个权限
//            info.addStringPermission("user:query");
//            给当前主体赋予一些权限
            info.addStringPermissions(Arrays.asList("user:query", "admin:add", "user:del"));
        }
        return info;
    }
}
