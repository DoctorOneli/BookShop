package org.freedom.boot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.freedom.boot.bean.Order;
import org.freedom.boot.bean.OrderExample;

public interface OrderMapper {
    long countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    int deleteByPrimaryKey(Integer orderId);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExampleWithBLOBs(OrderExample example);

    List<Order> selectByExample(OrderExample example);
    
    List<Order> selectByExampleWithAll(OrderExample example);
    
    List<Order> selectByExampleWithAddress(OrderExample example);
    
    Order selectByPrimaryKey(Integer orderId);
    
    Order selectOrderCancelByPrimaryKey(Integer orderId);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExampleWithBLOBs(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKeyWithBLOBs(Order record);

    int updateByPrimaryKey(Order record);

	
}