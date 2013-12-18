package com.zcyservice.bean;

import javax.persistence.Table;

@Table(name = ManufacturerTemp.TABLE_NAME_TEMP)
public class ManufacturerTemp extends Manufacturer {
	public static final String TABLE_NAME_TEMP = "ManufacturerTemp";

}
