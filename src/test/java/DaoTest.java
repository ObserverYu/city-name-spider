import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.chen.dao.CityMapper;
import org.chen.dao.MySqlFactoryBuilder;
import org.chen.entity.City;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 *  
 * @author YuChen
 * @date 2019/12/30 10:32
 **/
 
public class DaoTest {
	@Test
	public void testInsertCity() throws IOException {
		SqlSessionFactory sqlSessionFactory = MySqlFactoryBuilder.build();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		CityMapper mapper = sqlSession.getMapper(CityMapper.class);
		mapper.insert(new City("21231","0","测试","110","fsadadsasdas",1));

	}

	@Test
	public void testBatchInsertCity() throws IOException {
		SqlSessionFactory sqlSessionFactory = MySqlFactoryBuilder.build();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		CityMapper mapper = sqlSession.getMapper(CityMapper.class);
		List<City> list = new ArrayList<>();
		for (int i = 0; i < 30; i++) {
			list.add(new City("21231"+i,"0","测试"+i,"110","fsadadsasdas",2));
		}
		mapper.batchInsert(list);
	}
}
