package com.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.MenuPermissionDao;
import com.example.domain.MenuPermission;
import com.google.gson.Gson;

@RestController
@RequestMapping("/api")
public class MenuController {

	@Autowired
	private MenuPermissionDao menuDao;

	@GetMapping("/mainMenu")
	public Map<MenuPermission, List<MenuPermission>> MainMenu() {
		List<MenuPermission> listParent = menuDao.getParentId();
		List<MenuPermission> listChild = menuDao.getChildId();
		Map<MenuPermission, List<MenuPermission>> map = new HashMap<MenuPermission, List<MenuPermission>>();
		List<MenuPermission> listChilds = null;
		for (MenuPermission list : listParent) {
			listChilds = new ArrayList<MenuPermission>();
			for (MenuPermission childlist : listChild) {
				if (list.getId() == childlist.getParent_id()) {
					listChilds.add(childlist);
				}
			}
			map.put(list, listChilds);
		}
		Gson gson=new Gson();
		gson.toJson(map);
		return map;
	}
	/*
	@GetMapping("/mainMenu")
	public List<MenuPermission> MainMenu() {
		List<MenuPermission> resultList = new ArrayList<>();
		// parent menu list
		List<MenuPermission> parentList = menuDao.getParentId();
		try {
			if(parentList != null) {
				// all child menu list
				List<MenuPermission> allChildList = menuDao.getChildId();
				for(MenuPermission parent : parentList) {
					MenuPermission model = new MenuPermission();
					 BeanUtils.copyProperties(parent, model);
					// filter
					List<MenuPermission> childList = new ArrayList<>();
					for(MenuPermission child : allChildList) {
						if (parent.getId() == child.getParent_id()) {
							childList.add(child);
						}
					}
					// set child menu list 
					model.setChildMenu(childList);
					// add
					resultList.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}*/
}
