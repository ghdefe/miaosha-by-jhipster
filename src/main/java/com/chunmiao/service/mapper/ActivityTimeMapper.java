package com.chunmiao.service.mapper;

import com.chunmiao.domain.Good;
import com.chunmiao.domain.SecActivity;
import com.chunmiao.service.dto.ActivityTime;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ActivityTimeMapper extends EntityMapper<ActivityTime, SecActivity> {

}

