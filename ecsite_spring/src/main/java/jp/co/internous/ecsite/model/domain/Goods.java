package jp.co.internous.ecsite.model.domain;

import java.sql.Timestamp;
import java.util.List;

public class Goods {
	
	private long id;
	private String goodsName;
	private long price;
	private Timestamp updatedAt;
	private List<Purchase> purchaseList;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	
	public Timestamp getUpdated_at() {
		return updatedAt;
	}
	public void setUpdated_at(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public List<Purchase> getPurchaseList() {
		return purchaseList;
	}
	
	public void setPurchaseList(List<Purchase> purchaseList) {
		this.purchaseList = purchaseList;
	}

}
