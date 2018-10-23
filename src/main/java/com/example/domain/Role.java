package com.example.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class Role implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -860170241799510984L;
	private int id;
	private String name;
	private String memo;
}
