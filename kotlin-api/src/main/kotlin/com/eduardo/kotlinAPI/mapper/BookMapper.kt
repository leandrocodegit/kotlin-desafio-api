package com.eduardo.kotlinAPI.mapper

import com.eduardo.kotlinAPI.controller.request.BookRequest
import com.eduardo.kotlinAPI.entity.Book
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.ReportingPolicy

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface BookMapper {

    @Mappings(
        Mapping(target = "status", expression = "java(BookStatus.ATIVO)")
    )
    fun toEntity(request: BookRequest): Book
}