package com.cliente.api.controller.controllers

import com.cliente.api.controller.request.UsuarioRequest
import com.cliente.api.controller.response.UsuarioResponse
import com.cliente.api.entity.Usuario
import com.cliente.api.enuns.StatusUsuario
import com.cliente.api.mapper.UsuarioMapper
import com.cliente.api.service.UsuarioService
import com.cliente.api.util.BuscaEndereco
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/users")
class UsuarioController(private val usuarioService: UsuarioService,
                        private val usuarioMapper: UsuarioMapper) {

    @GetMapping
    fun listAllUsuario():  ResponseEntity<List<UsuarioResponse>>{
        var listResponseUsuario: List<UsuarioResponse> = usuarioService
            .listAllUsuario()
            .stream()
            .map {u -> usuarioMapper.toResponse(u)}
            .collect(Collectors.toList())
        return ResponseEntity.ok(listResponseUsuario)
    }
     @GetMapping("/{id}")
    fun buscarUsuarioId(@PathVariable id: Long): ResponseEntity<UsuarioResponse>{
        return ResponseEntity.ok(usuarioMapper.toResponse(usuarioService.buscarUsuarioById(id)))
    }

    @PostMapping
    fun savesUsuario(@RequestBody @Valid usuarioRequest: UsuarioRequest): ResponseEntity<UsuarioResponse> {
            var usuario =  usuarioMapper.toEntity(usuarioRequest)
            usuario.enderecos = listOf(BuscaEndereco().findEndereco(usuarioRequest.endereco))
            usuarioService.saveUsuario(usuario)
            return ResponseEntity.ok(usuarioMapper.toResponse(usuario))
    }

    @PatchMapping("/{id}/desactive")
    fun desactiveUsuario(@PathVariable id: Long):ResponseEntity<UsuarioResponse>{
        return ResponseEntity.ok(usuarioMapper.toResponse(usuarioService.updateStatusUsuario(id,StatusUsuario.INATIVO)))
    }

    @PatchMapping("/{id}/active")
    fun activeUsuario(@PathVariable id: Long):ResponseEntity<UsuarioResponse>{
        return ResponseEntity.ok(usuarioMapper.toResponse(usuarioService.updateStatusUsuario(id,StatusUsuario.ATIVO)))
    }
}