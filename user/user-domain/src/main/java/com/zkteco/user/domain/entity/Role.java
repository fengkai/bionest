package com.zkteco.user.domain.entity;

import java.util.Set;

import lombok.Data;

import org.springframework.lang.Nullable;

@Data
public class Role {

	@Nullable
	private Long id;

	private String name;

	@Nullable
	private Set<Authority> authoritySet;

}
