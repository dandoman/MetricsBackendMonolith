package com.cante.metrics.logic;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.javatuples.Tuple;
import org.javatuples.Unit;

public class TupleBuilder {
	private List<String> tupleValues = new ArrayList<String>();
	
	public void addValue(String s){
		tupleValues.add(s);
	}
	
	public Tuple build(){
		if(tupleValues.size() == 1){
			return new Unit<String>(tupleValues.get(0));
		}
		
		if(tupleValues.size() == 2){
			return new Pair<String,String>(tupleValues.get(0),tupleValues.get(1));
		}
		
		if(tupleValues.size() == 3){
			return new Triplet<String,String,String>(tupleValues.get(0),tupleValues.get(1),tupleValues.get(2));
		}
		
		if(tupleValues.size() == 4){
			return new Quartet<String,String,String,String>(tupleValues.get(0),tupleValues.get(1),tupleValues.get(2),tupleValues.get(3));
		}
		
		return null;
	}
}
