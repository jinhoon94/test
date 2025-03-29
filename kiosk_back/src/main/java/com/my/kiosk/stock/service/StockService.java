package com.my.kiosk.stock.service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.kiosk.stock.classes.Stock;
import com.my.kiosk.stock.classes.StockIn;
import com.my.kiosk.stock.classes.StockOut;
import com.my.kiosk.stock.repository.StockMapper;

@Service
public class StockService {

	@Autowired
	StockMapper stockmapper;
	
	public List<Stock> getStockByPlace(int placeId) {
		return stockmapper.selectStockByPlace(placeId);
	}
    public List<Stock> getStockList() {
		return stockmapper.findAll();
	}
	
	public StockIn stockIn(StockIn stockIn){
		stockIn.setDate(Date.valueOf(LocalDateTime.now().toLocalDate()));
        
        // StockIn 데이터 추가
        stockmapper.stockIn(stockIn);

            // Stock 객체 조회
            Stock stock = stockmapper.findStockByMenuIdAndPlaceId(stockIn.getMenuId(), stockIn.getPlaceId());

            if (stock != null) {
                // 기존 stock의 수량 업데이트
                stock.setStockQty(stock.getStockQty() + stockIn.getAmount());
                stockmapper.plusStock(stock);
            } else {
                // 새로운 stock 객체 생성
                stock = new Stock();
                stock.setMenuId(stockIn.getMenuId()); // menuId 설정
                stock.setPlaceId(stockIn.getPlaceId()); // placeId 설정
                stock.setStockQty(stockIn.getAmount());
                stock.setDate(Date.valueOf(LocalDateTime.now().toLocalDate()));
                stockmapper.newStock(stock);
            }
            return stockIn;
        } 

   

	public StockOut stockOut(StockOut stockOut) {
		stockOut.setDate(Date.valueOf(LocalDateTime.now().toLocalDate()));
        
        stockmapper.stockOut(stockOut);

            Stock stock = stockmapper.findStockByMenuIdAndPlaceId(stockOut.getMenuId(), stockOut.getPlaceId());

            if (stock != null) {
                stock.setStockQty(stock.getStockQty() - stockOut.getAmount());
                stockmapper.minusStock(stock);
            } else {
            	System.out.println("재고가 존재하지 않습니다.");
            }
            return stockOut;
        } 

     public List<StockIn> stockInfindAll() {
        return stockmapper.stockInfindAll(); 
        }
    

    public List<StockOut> stockOutfindAll() {
         return stockmapper.stockOutfindAll(); 
        }
	
}