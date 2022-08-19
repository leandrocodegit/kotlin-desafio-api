package com.cliente.api.service

import com.cliente.api.entity.Usuario
import com.cliente.api.enuns.CodigoErro
import com.cliente.api.enuns.StatusUsuario
import com.cliente.api.exceptions.EntityResponseException
import com.cliente.api.repository.UsuarioRepository
import com.cliente.api.util.BuscaEndereco
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*


@Service
class UsuarioService(
    val usuarioRepository: UsuarioRepository) {

    fun buscarUsuarioById(id: Long): Usuario{
        var  optUsuario: Optional<Usuario> = usuarioRepository.findById(id)
            return optUsuario.orElseThrow{EntityResponseException("Nenhum usuario foi encontrado", CodigoErro.NOT_FOUND)}
    }
    fun buscarUsuarioByEmail(email: String): Usuario{
        var  optUsuario: Optional<Usuario> = usuarioRepository.findByEmail(email)
        return  optUsuario.orElseThrow{EntityResponseException("Nenhum usuario encontrado", CodigoErro.NOT_FOUND)}
    }

    fun listAllUsuario(): List<Usuario>{
       return usuarioRepository.findAll()
    }
    fun listAllUsuario(pageable: Pageable): Page<Usuario> {
        return usuarioRepository.findAll(pageable)
    }

    fun saveUsuario(usuario: Usuario): Usuario{
            var  optUsuario: Optional<Usuario> = usuarioRepository.findByEmail(usuario.email)
         if(optUsuario.isPresent) throw EntityResponseException("Email ${usuario.email} ja existe na base de dados", CodigoErro.DUPLICATE)
        return usuarioRepository.save(usuario)
    }

    fun updateStatusUsuario(id: Long, statusUsuario: StatusUsuario): Usuario{
       var user: Usuario = buscarUsuarioById(id)
        if(statusUsuario.status == user.isActive)
        when(user.isActive){
            StatusUsuario.ATIVO.status -> throw EntityResponseException("Usuario ja esta ativo", CodigoErro.ACTIVE)
            StatusUsuario.INATIVO.status -> throw EntityResponseException("Usuario ja esta inativo", CodigoErro.INACTIVE)
        }
        user.isActive = statusUsuario.status
        return usuarioRepository.save(user)
    }

}