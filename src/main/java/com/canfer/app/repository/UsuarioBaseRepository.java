package com.canfer.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import com.canfer.app.model.Usuario;

@NoRepositoryBean
public interface UsuarioBaseRepository<T extends Usuario> extends JpaRepository<T, Long>{
	
	// method which extracts a user by the username
	T findByUsername(String username);
}


