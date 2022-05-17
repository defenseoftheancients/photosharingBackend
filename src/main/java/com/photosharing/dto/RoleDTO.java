package com.photosharing.dto;

import com.photosharing.constant.ERole;

public class RoleDTO {
	private Integer id;

	private ERole name;

	public void Role(ERole name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ERole getName() {
		return name;
	}

	public void setName(ERole name) {
		this.name = name;
	}
}
