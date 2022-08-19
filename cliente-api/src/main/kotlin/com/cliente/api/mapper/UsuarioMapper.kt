package com.cliente.api.mapper

import com.cliente.api.controller.request.UsuarioRequest
import com.cliente.api.controller.response.UsuarioResponse
import com.cliente.api.entity.Usuario
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.ReportingPolicy

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UsuarioMapper {

     @Mappings(
          Mapping(target = "usuarioRequest.isActive", expression = "java(Boolean.TRUE)"),
          Mapping(target = "enderecoRequest.isDelivery", expression = "java(Boolean.TRUE)"),
          Mapping(target = "enderecos", ignore = true)
     )
     fun toEntity(request: UsuarioRequest): Usuario
     fun toResponse(usuario: Usuario): UsuarioResponse

}