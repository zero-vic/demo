package com.hy.demo.generator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MybatisPlusGeneratorApplicationTests {

    @Test
    void contextLoads() {
        String tableName = "test_user_ce";

        String[] split = tableName.toLowerCase().split("_");
        StringBuilder sb = new StringBuilder();
        for(String str: split ){
            sb.append(str.substring(0,1).toUpperCase());
            sb.append(str.substring(1,str.length()));
        }
        System.out.println(sb.toString());
    }

}
