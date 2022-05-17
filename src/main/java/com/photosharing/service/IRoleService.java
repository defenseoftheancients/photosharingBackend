package com.photosharing.service;

import com.photosharing.constant.ERole;
import com.photosharing.dto.RoleDTO;

public interface IRoleService {
	RoleDTO findByName(ERole name);
}
