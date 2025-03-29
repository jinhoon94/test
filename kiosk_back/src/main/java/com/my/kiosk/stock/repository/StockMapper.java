package com.my.kiosk.stock.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.my.kiosk.stock.classes.Stock;
import com.my.kiosk.stock.classes.StockIn;
import com.my.kiosk.stock.classes.StockOut;

@Mapper
public interface StockMapper {
    List<Stock> selectStockByPlace(@Param("placeId") int placeId);

    List<Stock> findAll();
    
    Stock findStockByMenuIdAndPlaceId(@Param("menuId") int menuId, @Param("placeId") int placeId);

    void plusStock(Stock stock);
    
    void minusStock(Stock stock);

    void newStock(Stock stock);

    void stockIn(StockIn stockIn);

	void stockOut(StockOut stockOut);

    List<StockIn> stockInfindAll();
    
    List<StockOut> stockOutfindAll();
}