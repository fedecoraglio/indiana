package com.indiana.service.inventory.provider.application.dto;

import java.io.Serializable;
import lombok.Builder;

@Builder
public record ProviderDto(Long id, String name, String address) implements Serializable {

}
