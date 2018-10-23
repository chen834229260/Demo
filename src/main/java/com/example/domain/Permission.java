package com.example.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class Permission implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7476468878870253216L;
	private int id;
	private String url;
	private String name;
	
}
