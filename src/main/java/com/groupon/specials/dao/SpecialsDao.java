package com.groupon.specials.dao;

import lombok.Setter;


public class SpecialsDao {
    @Setter private String dbName;
    
    public String getDbName(){
        return dbName;
    }
}
