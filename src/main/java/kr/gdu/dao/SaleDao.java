package kr.gdu.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.gdu.dao.mapper.SaleMapper;
import kr.gdu.logic.Sale;

@Repository
public class SaleDao {
	
	private SqlSessionTemplate template;
	Class<SaleMapper> cls = SaleMapper.class;
	
	public SaleDao(SqlSessionTemplate template) {
		this.template = template;
	}
	
	public int getMaxSaleId() {
		return template.getMapper(cls).maxid();
	}
	
	public void insert(Sale sale) {
		template.getMapper(SaleMapper.class).insert(sale);
	}

	public List<Sale> list(String userid) {
		List<Sale> result = template.getMapper(SaleMapper.class).select(userid);
		return result;
	}
	
	
}
