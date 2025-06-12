package kr.gdu.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.gdu.dao.mapper.SaleItemMapper;
import kr.gdu.logic.SaleItem;

@Repository
public class SaleItemDao {
	
	private SqlSessionTemplate template;
	Class<SaleItemMapper> cls = SaleItemMapper.class;
	
	public SaleItemDao(SqlSessionTemplate template) {
		this.template = template;
	}

	public void insert(SaleItem saleItem) {
		template.getMapper(cls).insert(saleItem);
		
	}

	public List<SaleItem> list(int saleid) {
		return template.getMapper(cls).select(saleid);
	}

	
}
