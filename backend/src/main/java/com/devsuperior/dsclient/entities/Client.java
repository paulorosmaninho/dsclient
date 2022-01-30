package com.devsuperior.dsclient.entities;

import java.io.Serializable;
import java.time.Instant;

public class Client implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String cpf;
	private Double income;
	private Instant birthDate;
	private Integer children;
	
	

}
