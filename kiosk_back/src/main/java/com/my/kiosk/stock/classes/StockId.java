package com.my.kiosk.stock.classes;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class StockId implements Serializable {
    private int menuId;
    private int placeId;
}


