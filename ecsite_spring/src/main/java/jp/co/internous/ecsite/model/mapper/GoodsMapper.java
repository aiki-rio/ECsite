package jp.co.internous.ecsite.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import jp.co.internous.ecsite.model.domain.Goods;

@Mapper
public interface GoodsMapper {
	
	@Select("SELECT * FROM goods")
	List<Goods> findAll();

	@Delete("DELETE FROM goods WHERE Id = #{id}")
	boolean deleteById(long id);

	@Insert("INSERT INTO goods (goods_name, price) " +
			"VALUES (#{goodsName}, #{price})")
	@Options(useGeneratedKeys=true, keyProperty="id")
	void insert(Goods goods);

	

}
