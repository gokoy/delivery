package com.gokoy.delivery.domain.store.dto;

import com.gokoy.delivery.domain.store.domain.StoreType;
import com.gokoy.delivery.global.config.validation.ValidEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StoreRequestForTypeRequest {
    @ValidEnum(enumClass = StoreType.class)
    private String storeType;
}
