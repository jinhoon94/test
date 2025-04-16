package com.my.kiosk.stock.classes;


import lombok.Data;

@Data
public class StockOutDTO {
	private int menu_id;
	private int place_id;
	private int amount;
}
