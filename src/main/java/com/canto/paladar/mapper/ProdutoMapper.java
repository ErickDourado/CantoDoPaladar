package com.canto.paladar.mapper;

import com.canto.paladar.dto.produto.ProdutoRequest;
import com.canto.paladar.dto.produto.ProdutoResponse;
import com.canto.paladar.entity.Produto;
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
public interface ProdutoMapper {

    Produto toEntity(ProdutoRequest request);

    Produto toEntity(ProdutoRequest request, @MappingTarget Produto entity);

    ProdutoResponse toResponse(Produto entity);

    List<ProdutoResponse> toResponseList(List<Produto> entities);

}
