package jp.co.internous.ecsite.model.domain.dto;

import java.sql.Timestamp;

import jp.co.internous.ecsite.model.domain.Goods;
import jp.co.internous.ecsite.model.domain.Purchase;

public class HistoryDto {
	
	private long id;
	private long userId;
	private long goodsId;
	private String goodsName;
	private long itemCount;
	private long total;
	private Timestamp createdAt;
	
	public HistoryDto() {
	}
	
	public HistoryDto(Goods goods) {
		Purchase p = goods.getPurchaseList().get(0);
		this.setId(p.getId());
		this.setUserId(p.getUserId());
		this.setGoodsId(p.getGoodsId());
		this.setGoodsName(goods.getGoodsName());
		this.setItemCount(p.getItemCount());
		this.setTotal(p.getTotal());
		this.setCreatedAt(p.getCreatedAt());
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}
	
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	public long getItemCount() {
		return itemCount;
	}
	public void setItemCount(long itemCount) {
		this.itemCount = itemCount;
	}
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

}
