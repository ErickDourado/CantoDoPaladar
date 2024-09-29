package com.canto.paladar.mapper;

import com.canto.paladar.dto.pedido.PedidoRequest;
import com.canto.paladar.dto.pedido.PedidoResponse;
import com.canto.paladar.entity.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        nullValueCheckStrategy = ALWAYS
)
public interface PedidoMapper {

    Pedido toEntity(PedidoRequest request);

    Pedido toEntity(PedidoRequest request, @MappingTarget Pedido entity);

    PedidoResponse toResponse(Pedido entity);

    List<PedidoResponse> toResponseList(List<Pedido> entities);

}
