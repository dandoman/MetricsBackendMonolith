package com.cante.metrics.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "locks")
public class Lock {
	@Id private String id;
	private String hostId;
	private String ownerId;
	private long rvn; //For optimistic locking
}
