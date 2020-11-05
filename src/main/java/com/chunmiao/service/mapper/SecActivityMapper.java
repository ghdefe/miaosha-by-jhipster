package com.chunmiao.service.mapper;


import com.chunmiao.domain.*;
import com.chunmiao.service.dto.SecActivityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SecActivity} and its DTO {@link SecActivityDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SecActivityMapper extends EntityMapper<SecActivityDTO, SecActivity> {



    default SecActivity fromId(Long id) {
        if (id == null) {
            return null;
        }
        SecActivity secActivity = new SecActivity();
        secActivity.setId(id);
        return secActivity;
    }
}
