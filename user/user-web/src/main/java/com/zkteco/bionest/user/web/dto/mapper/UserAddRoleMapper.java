package com.zkteco.bionest.user.web.dto.mapper;

import com.zkteco.bionest.user.web.dto.request.UserAddRoleReqBody;
import com.zkteco.user.application.command.UserAddRoleCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Map user add dto to command.
 */
@Mapper
public interface UserAddRoleMapper {

	UserAddRoleMapper INSTANCE = Mappers.getMapper(UserAddRoleMapper.class);

	UserAddRoleCommand dto2command(UserAddRoleReqBody reqBody);

}
