package com.canfer.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.canfer.app.model.Usuario;
import com.canfer.app.model.Usuario.UsuarioCanfer;
import com.canfer.app.repository.UsuarioRepository;

@Service
@PropertySource("classpath:mail.properties")
public class UserPrincipalDetailsService implements UserDetailsService {

	//We take the users from our database using its repository
	@Autowired
	private UsuarioRepository usuarioRepository;
	private String user;
	private String pass;
	
	
	//Definition from the constructor class.
	public UserPrincipalDetailsService(UsuarioRepository usuarioRepository, @Value("${user}") String user, @Value("${pass}") String pass) {
		this.usuarioRepository = usuarioRepository;
		this.user = user;
		this.pass = pass;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		if (username.equalsIgnoreCase(user)) {
			//we create the super user once
			if (usuarioRepository.findByUsername(user) != null) {
				return new UserPrincipal(usuarioRepository.findByUsername(user));
			} 
			//we obtain data from properties file
			UsuarioCanfer userCanfer = new UsuarioCanfer(user, passwordEncoder().encode(pass), "NA", "NA", "NA", "ADMIN", "");
			usuarioRepository.save(userCanfer);
			return new UserPrincipal(userCanfer);
		} else {
			//Load the user using the repository
			Usuario usuario = this.usuarioRepository.findByUsername(username);
			
			if (usuario == null) {
				throw new UsernameNotFoundException("El usuario no esta registrado.");
			}
			
			//Return the UserPrincipal object which implements the UserDetails class.
			return new UserPrincipal(usuario);	
		}
	}
	
	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
