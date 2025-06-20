package kr.gdu.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Sale {// 주문정보
	
	private int saleid;
	private String userid;
	private Date saledate;
	private User user;
	private List<SaleItem> itemList = new ArrayList<>();
	
	public int getTotal() {
		return itemList.stream()
				.mapToInt(s->s.getItem().getPrice() * s.getQuantity())
				.sum();
	}
}
