package com.canfer.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.canfer.app.model.Usuario;
import com.canfer.app.repository.UsuarioRepository;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {
	//We give the encoder
	private PasswordEncoder passwordEncoder;
	
	//We take the users from our database using its repository
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	//Definition from the constructor class.
	public UserPrincipalDetailsService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//Load the user using the repository
		Usuario usuario = this.usuarioRepository.findByUsername(username);
		
		//Return the UserPrincipal object which implements the UserDetails class.
		return new UserPrincipal(usuario);	
	}

}
