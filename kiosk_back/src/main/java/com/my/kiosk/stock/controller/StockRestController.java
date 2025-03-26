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