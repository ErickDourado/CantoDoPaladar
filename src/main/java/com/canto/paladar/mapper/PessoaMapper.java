package com.canto.paladar.mapper;

import com.canto.paladar.dto.PessoaRequest;
import com.canto.paladar.dto.PessoaResponse;
import com.canto.paladar.entity.Pessoa;
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
public interface PessoaMapper {

    Pessoa toEntity(PessoaRequest request);

    Pessoa toEntity(PessoaRequest request, @MappingTarget Pessoa entity);

    PessoaResponse toResponse(Pessoa entity);

    List<PessoaResponse> toResponseList(List<Pessoa> entities);

}
