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
@Table(name = "t_authority")
@Data
public class AuthorityDO {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "TYPE", nullable = false)
	private String type;

	@Column(name = "CODE", nullable = false)
	private String code;

	@ManyToMany
	@JoinTable(joinColumns = { @JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID") },
			inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") })
	private Set<RoleDO> roleDoSet = new HashSet<>();

}
