package com.zkteco.bionest.user.web.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RoleAddReqBody {

	@NotNull
	private String name;

}
