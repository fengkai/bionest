package com.zkteco.bionest.user.builder;

import java.util.HashSet;
import java.util.Set;

import com.zkteco.bionest.user.persistence.AuthorityDO;
import com.zkteco.bionest.user.persistence.RoleDO;
import com.zkteco.bionest.user.types.authority.AuthorityCode;
import com.zkteco.bionest.user.types.authority.AuthorityType;
import com.zkteco.user.domain.entity.Authority;
import com.zkteco.user.domain.entity.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Role entity/do builder.
 *
 * @author Tyler Feng
 */
@Component
public class RoleBuilder {

	@Autowired
	private AuthorityBuilder authorityBuilder;

	/**
	 * Entity to DO
	 * @param role
	 * @return
	 */
	public RoleDO fromRole(Role role){
		RoleDO roleDo = new RoleDO();
		roleDo.setId(role.getId());
		roleDo.setCode(role.getName());
		roleDo.setName(role.getName());

		Set<Authority> authoritySet = role.getAuthoritySet();

		if (!CollectionUtils.isEmpty(authoritySet)) {
			Set<AuthorityDO> authorityDoSet = new HashSet<>();
			for (Authority authority : authoritySet) {
				AuthorityDO authorityDo = new AuthorityDO();
				String name = authority.getName();
				AuthorityCode code = authority.getCode();
				AuthorityType type = authority.getType();

				authorityDo.setName(name);
				authorityDo.setCode(code.getCode());
				authorityDo.setType(type.getType());
				authorityDoSet.add(authorityDo);
			}
			roleDo.setAuthorityDoSet(authorityDoSet);
		}

		return roleDo;
	}

	/**
	 * DO to entity
	 * @param roleDO
	 * @return
	 */
	public Role toRole(RoleDO roleDO) {
		Role role = new Role();
		role.setId(roleDO.getId());
		role.setName(roleDO.getName());

		Set<AuthorityDO> authorityDoSet = roleDO.getAuthorityDoSet();
		if (!CollectionUtils.isEmpty(authorityDoSet)) {
			Set<Authority> authoritySet = new HashSet<>();
			for (AuthorityDO authorityDO : authorityDoSet) {
				Authority authority = authorityBuilder.toAuthority(authorityDO);
				authoritySet.add(authority);
			}
			role.setAuthoritySet(authoritySet);
		}
		return role;
	}

}
