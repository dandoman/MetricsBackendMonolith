package com.cante.metrics.activity;

import java.util.List;

import javax.persistence.QueryHint;
import javax.servlet.http.HttpServletResponse;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cante.metrics.entity.pojo.Customer;
import com.cante.metrics.logic.CustomerLogic;
import com.cante.metrics.request.CreateCustomerRequest;
import com.cante.metrics.request.LoginRequest;
import com.cante.metrics.request.UpdateCustomerRequest;

@Log4j
@Controller
@RequestMapping(value = "/customer")
@Transactional
public class CustomerActivity {

	private static final String APPLICATION_JSON = "application/json";
	@Setter private CustomerLogic customerLogic;
	
	@RequestMapping(method = RequestMethod.POST, value = "/login", produces = { APPLICATION_JSON })
    @ResponseBody
    public Customer login(@RequestBody LoginRequest r,
                          HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Max-Age", "1728000");
    	return customerLogic.login(r.getEmail(),r.getPassword());
    }
    
    @RequestMapping(method = RequestMethod.POST, produces = { APPLICATION_JSON })
    @ResponseBody
    public Customer createCustomer(@RequestBody CreateCustomerRequest r) {
    	return customerLogic.createCustomer(r); //Badsauce, shouldn't pass in request to next level
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = { APPLICATION_JSON })
    @ResponseBody
    public Customer getCustomer(@RequestParam(required = true) String customerId) {
    	return customerLogic.getCustomerById(customerId);
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = { APPLICATION_JSON }, value ="/{id}")
    @ResponseBody
    public Customer updateCustomer(@RequestBody UpdateCustomerRequest r, @PathVariable String id) {
    	return customerLogic.updateCustomer(id, r);
    }
    
}
