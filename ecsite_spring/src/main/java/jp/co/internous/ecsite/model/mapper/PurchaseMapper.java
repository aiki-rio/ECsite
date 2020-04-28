package jp.co.internous.ecsite.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import jp.co.internous.ecsite.model.domain.Purchase;
import jp.co.internous.ecsite.model.domain.dto.HistoryDto;

@Mapper
public interface PurchaseMapper {
	
	List<HistoryDto> findHistory(@Param ("userId") String userId);
	List<HistoryDto> findHistoryLatest(@Param ("userId") String userId);
	
	@Insert("INSERT INTO purchase (user_id, goods_id, goods_name, item_count, total) " +
						"VALUES (#{userId}, #{goodsId}, #{goodsName}, #{itemCount}, #{total})")
	@Options(useGeneratedKeys=true, keyProperty="id")
	void insert(Purchase purchase);
	

}
