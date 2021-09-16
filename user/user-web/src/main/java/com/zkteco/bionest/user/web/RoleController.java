package com.zkteco.bionest.user.web;

import javax.validation.Valid;

import com.zkteco.bionest.user.web.dto.request.RoleAddReqBody;
import com.zkteco.bionest.user.web.dto.mapper.RoleAddMapper;
import com.zkteco.user.application.RoleService;
import com.zkteco.user.application.command.RoleAddCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PostMapping("/save")
	public String save(@RequestBody @Valid RoleAddReqBody roleAddReqBody) {
		RoleAddCommand roleAddCommand = RoleAddMapper.INSTANCE.dto2command(roleAddReqBody);
		roleService.save(roleAddCommand);
		return "OK";
	}

}
