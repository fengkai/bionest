package com.zkteco.bionest.user.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import com.zkteco.bionest.boot.exception.BusinessException;
import com.zkteco.bionest.boot.web.PageReqBody;
import com.zkteco.bionest.boot.web.PageResponse;
import com.zkteco.bionest.boot.web.Result;
import com.zkteco.bionest.user.web.dto.mapper.UserAddMapper;
import com.zkteco.bionest.user.web.dto.mapper.UserAddRoleMapper;
import com.zkteco.bionest.user.web.dto.request.UserAddReqBody;
import com.zkteco.bionest.user.web.dto.request.UserAddRoleReqBody;
import com.zkteco.user.application.UserService;
import com.zkteco.user.application.command.UserAddCommand;
import com.zkteco.user.application.command.UserAddRoleCommand;
import com.zkteco.user.application.dto.UserDto;
import com.zkteco.user.application.dto.assembler.UserDtoAssembler;
import com.zkteco.user.domain.entity.User;
import com.zkteco.user.domain.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public Result<PageResponse<UserDto>> page(@RequestBody PageReqBody pageReqBody) {
		PageResponse<User> pageResponse = userRepository.pageQuery(pageReqBody);
		List<User> userList = pageResponse.getData();
		List<UserDto> userDtos = new ArrayList<>();
		for (User user : userList) {
			UserDto userDto = UserDtoAssembler.INSTANCE.user2Dto(user);
			userDtos.add(userDto);
		}
		PageResponse<UserDto> userDtoPageResponse = new PageResponse<>();
		userDtoPageResponse.setPageNumber(pageResponse.getPageNumber());
		userDtoPageResponse.setPageSize(pageResponse.getPageSize());
		userDtoPageResponse.setTotal(pageResponse.getTotal());
		userDtoPageResponse.setData(userDtos);
		return Result.success(userDtoPageResponse);
	}

	@PostMapping("/save")
	public String save(@RequestBody @Valid UserAddReqBody userAddReqBody) {
		UserAddCommand userAddCommand = UserAddMapper.INSTANCE.dto2command(userAddReqBody);
		userService.save(userAddCommand);
		return "OK";
	}

	@GetMapping("/{id}")
	public Result<UserDto> user(@PathVariable Long id) {
		try {
			UserDto userDto = userService.queryById(id);
			return Result.success(userDto);
		}
		catch (BusinessException ex) {
			return Result.fail(-1, ex.getMessage());
		}
	}

	@PutMapping("/{id}")
	public Result<UserDto> modify(@PathVariable Long id, @RequestBody UserAddRoleReqBody reqBody) {
		try {
			UserAddRoleCommand command = UserAddRoleMapper.INSTANCE.dto2command(reqBody);
			command.setUserId(id);
			UserDto userDto = userService.addRole(command);
			return Result.success(userDto);
		}
		catch (BusinessException ex) {
			return Result.fail(-1, ex.getMessage());
		}
	}
}
