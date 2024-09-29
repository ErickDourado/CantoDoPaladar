package com.canto.paladar.mapper;

import com.canto.paladar.dto.endereco.EnderecoRequest;
import com.canto.paladar.dto.endereco.EnderecoResponse;
import com.canto.paladar.entity.Endereco;
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
public interface EnderecoMapper {

    Endereco toEntity(EnderecoRequest request);

    Endereco toEntity(EnderecoRequest request, @MappingTarget Endereco endereco);

    EnderecoResponse toResponse(Endereco entity);

    List<EnderecoResponse> toResponseSet(List<Endereco> entities);

}
