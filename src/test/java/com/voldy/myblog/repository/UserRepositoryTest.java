package com.voldy.myblog.repository;

import com.voldy.myblog.MyblogApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MyblogApplication.class})
public class UserRepositoryTest {
    @Test
    public void test(){
        System.out.println("test...");
    }

}