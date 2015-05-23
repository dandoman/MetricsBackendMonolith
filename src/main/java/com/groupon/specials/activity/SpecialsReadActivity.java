package com.groupon.specials.activity;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.groupon.specials.logic.SpecialsLogic;

@Log4j
@Controller
@RequestMapping(value = "/read")
public class SpecialsReadActivity {
    
    private static final String APPLICATION_JSON = "application/json";
    @Setter private SpecialsLogic logic;
    
    @RequestMapping(method = RequestMethod.GET, produces = { APPLICATION_JSON }, value = "/db")
    @ResponseBody
    public String helloWorld() {
        String message = "Youd database is: " + logic.getDb();
        log.warn(message);
        return message;
    }
}
