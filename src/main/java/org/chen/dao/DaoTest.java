package org.chen.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * 
 *  
 * @author YuChen
 * @date 2019/12/27 9:39
 **/
 
public class DaoTest {
    public static void main(String[] args) throws IOException {
        String resource = "resources/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

    }
}
