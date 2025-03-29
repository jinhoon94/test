<<<<<<< Updated upstream
package com.my.kiosk.stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.kiosk.stock.classes.StockIn;
import com.my.kiosk.stock.service.StockService;

@RestController
@RequestMapping("/api")
public class StockRestController {
	
	@Autowired
	StockService stock_ser;
	/*
	@GetMapping("test")
	public String test() {
		stock_ser.test();
		
		return "테스트입니다";
	}
		*/
	
	    @PostMapping("stockin")
    public ResponseEntity<String> addStockIn(@RequestBody StockIn stockIn) {
        try {
			System.out.println("Received StockIn: " + stockIn);  // 전달받은 객체 확인
            stock_ser.addStockIn(stockIn);
            return new ResponseEntity<>("Stock In added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add Stock In", HttpStatus.INTERNAL_SERVER_ERROR);
        }
	
	}
	
}
=======
	package com.my.kiosk.stock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.kiosk.stock.classes.Stock;
import com.my.kiosk.stock.classes.StockIn;
import com.my.kiosk.stock.classes.StockOut;
import com.my.kiosk.stock.service.StockService;

@RestController
@RequestMapping("/api/stock")
public class StockRestController {
	
	@Autowired
	StockService stock_ser;
	
	@GetMapping("/place/{placeId}")
	public ResponseEntity<?> placeStock(@PathVariable("placeId") int placeId) {
		List<Stock> placeStock = stock_ser.getStockByPlace(placeId);
		return ResponseEntity.ok(placeStock);
	}

	@GetMapping("/list")
	public ResponseEntity<List<Stock>> stockList() {
		List<Stock> stockList = stock_ser.getStockList();
		return ResponseEntity.ok(stockList);
	}
	
	@PostMapping("/consume")
	public ResponseEntity<?> consumeStock(){
		return null;
		
	}
	
	@PostMapping("/in")
	public ResponseEntity<?> stockIn(@RequestBody StockIn stockIn){ 
		try {
			 StockIn savedStockIn = stock_ser.stockIn(stockIn);
				return new ResponseEntity<>(savedStockIn, HttpStatus.CREATED);
			} catch (RuntimeException e) {
				
		// 예외가 발생하면 400 Bad Request를 반환
		e.printStackTrace();
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
	}
	
	@PostMapping("/out")
	public ResponseEntity<?> stockOut(@RequestBody StockOut stockOut){
		try {
			StockOut savedStockOut = stock_ser.stockOut(stockOut);
				return new ResponseEntity<>(savedStockOut, HttpStatus.CREATED);
			} catch (RuntimeException e) {
				
		// 예외가 발생하면 400 Bad Request를 반환
		e.printStackTrace();
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
	}

	@GetMapping("/inout")
public ResponseEntity<?> stockInOutList() {
    try {
        List<StockIn> stockInList = stock_ser.stockInfindAll();  // StockIn 내역 조회
        List<StockOut> stockOutList = stock_ser.stockOutfindAll();  // StockOut 내역 조회
        
        Map<String, Object> response = new HashMap<>();
        response.put("stockInList", stockInList);
        response.put("stockOutList", stockOutList);
        
        return ResponseEntity.ok(response);
		
    } catch (Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
	
}
>>>>>>> Stashed changes
