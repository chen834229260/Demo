package com.example.domain;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class MenuPermission implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7326147426958517015L;
	
	private int id;
	private String name;
	private String href;
	private String permission;
	private String menuImg;
	private int parent_id;
	private int child_id;
	
	private List<MenuPermission> childMenu;
}
