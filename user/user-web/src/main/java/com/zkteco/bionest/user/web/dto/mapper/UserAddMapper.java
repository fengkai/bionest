package com.zkteco.bionest.user.web.dto.mapper;

import com.zkteco.bionest.user.web.dto.request.UserAddReqBody;
import com.zkteco.user.application.command.UserAddCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Map user add dto to command.
 */
@Mapper
public interface UserAddMapper {

	UserAddMapper INSTANCE = Mappers.getMapper(UserAddMapper.class);

	UserAddCommand dto2command(UserAddReqBody userAddReqBody);

}
