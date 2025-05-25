package com.indiana.service.inventory.provider.application.mapper;

import com.indiana.service.inventory.provider.application.dto.ProviderDto;
import java.util.List;
import org.springframework.stereotype.Component;
import com.indiana.service.inventory.provider.domain.Provider;

@Component
public class ProviderMapper {

  public ProviderDto toDto(final Provider entity) {
    if (entity == null) {
      return null;
    }
    return ProviderDto.builder().id(entity.getId())
        .name(entity.getName())
        .address(entity.getAddress())
        .build();
  }

  public List<ProviderDto> toDto(final List<Provider> list) {
    if (list == null) {
      return null;
    }
    return list.stream().map(this::toDto).toList();
  }

  public Provider toEntity(final ProviderDto dto) {
    if (dto == null) {
      return null;
    }
    final Provider item = new Provider();
    item.setId(dto.id());
    item.setName(dto.name());
    item.setAddress(dto.address());
    return item;
  }
}
