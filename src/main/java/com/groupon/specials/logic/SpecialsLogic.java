package com.groupon.specials.logic;

import lombok.Setter;
import com.groupon.specials.dao.SpecialsDao;

public class SpecialsLogic {
    @Setter private SpecialsDao dao;
    
    public String getDb() {
        return dao.getDbName();
    }

}
