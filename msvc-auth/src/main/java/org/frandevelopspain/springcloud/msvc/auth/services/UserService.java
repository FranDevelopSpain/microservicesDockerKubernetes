package org.frandevelopspain.springcloud.msvc.auth.services;

import org.frandevelopspain.springcloud.msvc.auth.models.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private WebClient.Builder client;

    private Logger log = LoggerFactory.getLogger(UserService.class);

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {

            Usuario usuario = client
                    .build()
                    .get()
                    .uri("http://msvc-users/login", uri -> uri.queryParam("email", email).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(Usuario.class)
                    .block();

            log.info("Usuario login: " + usuario.getEmail());
            log.info("Usuario nombre: " + usuario.getNombre());
            log.info("Password: " + usuario.getPassword());

            return new User(email, usuario.getPassword(), true, true, true, true,
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        } catch (RuntimeException e) {
            String error = "Error en el login, no existe el usuario " + email +
                    " en el sistema";
            log.error(error);
            log.error(e.getMessage());
            throw new UsernameNotFoundException(error);
        }
    }
}
