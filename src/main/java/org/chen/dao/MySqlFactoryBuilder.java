package org.chen.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 构造一个sqlfactory
 *  
 * @author YuChen
 * @date 2019/12/30 10:33
 **/
 
public class MySqlFactoryBuilder {

	public static SqlSessionFactory build() {
		InputStream inputStream = null;
		try {
			File file = new File("src/main/resources/mybatis-config.xml");
			inputStream = new FileInputStream(file);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory;
	}
}
