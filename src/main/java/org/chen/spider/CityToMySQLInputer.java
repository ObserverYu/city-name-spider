package org.chen.spider;

import cn.hutool.core.collection.CollectionUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.chen.dao.CityMapper;
import org.chen.dao.MySqlFactoryBuilder;
import org.chen.entity.City;
import org.chen.spider.dispater.GetAreaDispatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 将city实体导入数据库
 *
 * @author YuChen
 * @date 2019/12/30 13:52
 **/

public class CityToMySQLInputer {

	public static void inputToMySQL(GetAreaDispatcher dispatcher) {
		Collection<City> collector = dispatcher.getCollector();
		final LinkedBlockingDeque linkedBlockingDeque;
		if (collector instanceof LinkedBlockingDeque) {
			linkedBlockingDeque = (LinkedBlockingDeque) collector;
		} else {
			return;
		}
		SqlSessionFactory build = MySqlFactoryBuilder.build();
		SqlSession sqlSession = build.openSession();
		CityMapper mapper = sqlSession.getMapper(CityMapper.class);
		Thread thread = new Thread(() -> {
			List<City> list = new ArrayList<>();
			while (!dispatcher.isFinished()) {
				if (dispatcher.isFinished() || list.size() == 30) {
					mapper.batchInsert(list);
					list.clear();
				}
				City city = (City) linkedBlockingDeque.pollFirst();
				if(city == null){
					if(CollectionUtil.isNotEmpty(list)){
						mapper.batchInsert(list);
						list.clear();
					}
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}else {
					list.add(city);
				}
			}
			if(CollectionUtil.isNotEmpty(list)){
				mapper.batchInsert(list);
				list.clear();
			}

		});
		thread.setDaemon(true);
		thread.start();
	}

}
