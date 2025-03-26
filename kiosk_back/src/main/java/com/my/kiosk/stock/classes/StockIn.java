package com.my.kiosk.stock.classes;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class StockIn {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	@JsonProperty("menu_id")
	private int menuId;
	
	@Column(nullable = false)
	@JsonProperty("place_id")
	private int placeId;
	
	@Column(nullable = false)
	private int amount;
	
	@Column(nullable = false)
	private Date date;
}
