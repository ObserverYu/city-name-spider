package org.chen.dao;

import org.chen.entity.City;

import java.util.List;

/**
 * <p>
 * 城市表 Mapper 接口
 * </p>
 *
 * @author YuChen
 * @since 2019-12-30
 */
public interface CityMapper  {

	void insert(City city);

	void batchInsert(List<City> cities);
}
