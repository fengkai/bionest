package com.zkteco.bionest.user.web.dto.request;

import java.util.Set;

import lombok.Data;

@Data
public class UserAddRoleReqBody {

	private Set<Long> roleIds;

}
