package com.zkteco.bionest.user.web.dto.mapper;

import com.zkteco.bionest.user.web.dto.request.RoleAddReqBody;
import com.zkteco.user.application.command.RoleAddCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Map user add dto to command.
 */
@Mapper
public interface RoleAddMapper {

	RoleAddMapper INSTANCE = Mappers.getMapper(RoleAddMapper.class);

	RoleAddCommand dto2command(RoleAddReqBody roleAddReqBody);

}
