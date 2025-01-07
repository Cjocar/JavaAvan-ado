package com.javanauta.aprendendospring.controller;

import com.javanauta.aprendendospring.business.UsuarioService;
import com.javanauta.aprendendospring.controller.dtos.UsuarioDTO;
import com.javanauta.aprendendospring.infrastructure.entity.Usuario;
import com.javanauta.aprendendospring.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    @PostMapping
    public ResponseEntity<Usuario> salvaUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuario))  ;
    }

    //Metodo para gerar o login
    //Verificar o password e a senha
    //Vai gerar um token
    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTO usuarioDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDto.getEmail(),
                        usuarioDto.getSenha())
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());

    }

        @GetMapping
         public ResponseEntity<Usuario> buscarUsuarioPorEmail(@RequestParam("email") String email){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
        }
        @DeleteMapping("/{email}")
    public ResponseEntity<Void>deletaUsuarioPorEmail(@PathVariable String email){
        usuarioService.deletaUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
        }
    }

