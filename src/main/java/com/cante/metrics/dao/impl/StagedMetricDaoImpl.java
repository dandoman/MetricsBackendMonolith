package com.cante.metrics.dao.impl;

import java.util.Random;

import org.hibernate.SessionFactory;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

import com.cante.metrics.dao.StagedMetricDao;
import com.cante.metrics.entity.pojo.StagedMetric;

@Log4j
public class StagedMetricDaoImpl implements StagedMetricDao {
	@Setter private SessionFactory sessionFactory;
	
	public void create(String ownerId, StagedMetric m) {
		if(new Random().nextBoolean()){
			log.info(String.format("Writing staged metric %s", m.toString()));
		} else{
			throw new RuntimeException("Write failed");
		}
	}

}
