package com.zkteco.bionest.user.builder;

import com.zkteco.bionest.user.persistence.AuthorityDO;
import com.zkteco.bionest.user.types.authority.AuthorityCode;
import com.zkteco.bionest.user.types.authority.AuthorityType;
import com.zkteco.user.domain.entity.Authority;

import org.springframework.stereotype.Component;

/**
 * Authority/AuthorityDO builder.
 *
 * @author Tyler Feng
 */
@Component
public class AuthorityBuilder {

	/**
	 * DO to entity
	 * @param authorityDO
	 * @return
	 */
	public Authority toAuthority(AuthorityDO authorityDO) {
		Authority authority = new Authority();
		authority.setId(authorityDO.getId());
		authority.setCode(new AuthorityCode(authorityDO.getCode()));
		authority.setType(new AuthorityType(authorityDO.getType()));
		authority.setName(authorityDO.getName());
		return authority;
	}

	/**
	 * Entity to DO
	 * @param authority
	 * @return
	 */
	public AuthorityDO fromAuthority(Authority authority) {
		AuthorityDO authorityDO = new AuthorityDO();
		if (authority.getId() != null) {
			authorityDO.setId(authority.getId());
		}
		authorityDO.setName(authority.getName());
		authorityDO.setType(authority.getType().getType());
		authorityDO.setCode(authority.getCode().getCode());

		return authorityDO;
	}

}
