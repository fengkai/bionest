package com.zkteco.user.domain.entity;

import com.zkteco.bionest.user.types.authority.AuthorityCode;
import com.zkteco.bionest.user.types.authority.AuthorityType;
import lombok.Data;

import org.springframework.lang.Nullable;

@Data
public class Authority {

	@Nullable
	private Long id;

	private String name;

	private AuthorityType type;

	private AuthorityCode code;

}
