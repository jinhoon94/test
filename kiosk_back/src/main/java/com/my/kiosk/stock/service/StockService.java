<<<<<<< Updated upstream
package com.my.kiosk.stock.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.kiosk.stock.classes.Stock;
import com.my.kiosk.stock.classes.StockId; 
import com.my.kiosk.stock.classes.StockIn;
import com.my.kiosk.stock.repository.StockInJPA;
import com.my.kiosk.stock.repository.StockJPA;

@Service
public class StockService {
    
    @Autowired
    StockJPA jpa_stock;
    
    @Autowired
    StockInJPA jpa_stock_in;

    public StockIn addStockIn(StockIn stockIn) {
        
        // 현재 날짜 설정
        stockIn.setDate(java.sql.Date.valueOf(LocalDateTime.now().toLocalDate())); 
        StockIn savedStockIn = jpa_stock_in.save(stockIn);

        // StockIn 데이터를 기반으로 stock을 업데이트하거나 새로 추가
        StockIn stockInFromDB = jpa_stock_in.findByMenuIdAndPlaceId(stockIn.getMenuId(), stockIn.getPlaceId());

        if (stockInFromDB != null) {
            
            // Stock 객체 조회
            Stock stock = jpa_stock.findByMenuIdAndPlaceId(stockIn.getMenuId(), stockIn.getPlaceId());

            if (stock != null) {
                // 기존 stock의 수량 업데이트
                stock.setStockQty(stock.getStockQty() + stockIn.getAmount());
            } else {
                // 새로운 stock 객체 생성
                stock = new Stock();
                stock.setMenuId(stockIn.getMenuId()); // menuId 설정
                stock.setPlaceId(stockIn.getPlaceId()); // placeId 설정
                stock.setStockQty(stockIn.getAmount());
                stock.setDate(java.sql.Date.valueOf(LocalDateTime.now().toLocalDate()));
            }
            
            // stock 저장
            jpa_stock.save(stock);
        } else {
            // StockIn 데이터가 없으면 예외 발생
            throw new RuntimeException("Stock In data could not be found in database");
        }
        
        return savedStockIn;
    }
}
=======
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
>>>>>>> Stashed changes
