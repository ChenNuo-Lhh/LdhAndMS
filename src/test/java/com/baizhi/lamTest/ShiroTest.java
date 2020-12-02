package com.baizhi.lamTest;

import org.apache.shiro.crypto.hash.Sha1Hash;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ShiroTest {
    public static void main(String[] args) {
        Sha1Hash sha1Hash = new Sha1Hash("111111abcd");
        System.out.println(sha1Hash.toHex());
    }
}
