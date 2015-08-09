package com.cante.metrics.activity;

import java.util.List;

import javax.persistence.QueryHint;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cante.metrics.entity.pojo.Customer;
import com.cante.metrics.logic.CustomerLogic;
import com.cante.metrics.request.CreateCustomerRequest;

@Log4j
@Controller
@RequestMapping(value = "/customer")
@Transactional
public class CustomerActivity {

	private static final String APPLICATION_JSON = "application/json";
	@Setter private CustomerLogic customerLogic;
	
	@RequestMapping(method = RequestMethod.GET, produces = { APPLICATION_JSON })
    @ResponseBody
    public List<Customer> queryCustomers(@RequestParam String ownerId) {
    	return null;
    }
    
    @RequestMapping(method = RequestMethod.POST, produces = { APPLICATION_JSON })
    @ResponseBody
    public Customer createCustomer(@RequestBody CreateCustomerRequest r) {
    	return customerLogic.createCustomer(r); //Badsauce, shouldn't pass in request to next level
    }
		
}
