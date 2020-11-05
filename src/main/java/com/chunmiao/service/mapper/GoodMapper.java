package com.chunmiao.service.mapper;


import com.chunmiao.domain.*;
import com.chunmiao.service.dto.GoodDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Good} and its DTO {@link GoodDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GoodMapper extends EntityMapper<GoodDTO, Good> {



    default Good fromId(Long id) {
        if (id == null) {
            return null;
        }
        Good good = new Good();
        good.setId(id);
        return good;
    }
}
