package com.zkteco.bionest.user.persistence;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "t_role")
@Data
public class RoleDO {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "CODE", nullable = false)
	private String code;

	@ManyToMany
	@JoinTable(joinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") },
			inverseJoinColumns = { @JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID") })
	private Set<AuthorityDO> authorityDoSet = new HashSet<>();

	@ManyToMany
	@JoinTable(joinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") },
			inverseJoinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") })
	private Set<UserDO> userDoSet = new HashSet<>();

}
