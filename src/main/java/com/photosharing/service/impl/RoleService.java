package com.photosharing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photosharing.constant.ERole;
import com.photosharing.dto.RoleDTO;
import com.photosharing.repository.RoleRepository;
import com.photosharing.service.IRoleService;
import com.photosharing.util.RoleUtil;

@Service
public class RoleService implements IRoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public RoleDTO findByName(ERole name) {
		return RoleUtil._EntitytoDTO(roleRepository.findByName(name).orElse(null));
	}
	
}
