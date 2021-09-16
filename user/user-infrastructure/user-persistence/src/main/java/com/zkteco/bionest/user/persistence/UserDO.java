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

/**
 * User DO.
 *
 * @author Tyler Feng
 */
@Entity
@Table(name = "t_user")
@Data
public class UserDO {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "USER_NAME", nullable = false, length = 50)
	private String userName;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PHONE")
	private String phone;

	@ManyToMany
	@JoinTable(joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") },
			inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") })
	private Set<RoleDO> roleSet = new HashSet<>();

}
