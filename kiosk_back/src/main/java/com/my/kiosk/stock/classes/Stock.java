<<<<<<< Updated upstream
package com.my.kiosk.stock.classes;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;

@Entity
@IdClass(StockId.class)
@Data
public class Stock {

    @Id
    private int menuId;  

    @Id
    private int placeId; 

    @Column(nullable = false)
    private int stockQty; 

    @Column(nullable = false)
    private Date date;
}
=======
package com.my.kiosk.stock.classes;

import java.sql.Date;

import lombok.Data;

@Data
public class Stock {

	private int menuId;
	
	private int placeId; 
	
	private int stockQty; 
	
	private Date date;
}
>>>>>>> Stashed changes
