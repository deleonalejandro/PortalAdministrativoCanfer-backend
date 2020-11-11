package com.canfer.app.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.canfer.app.model.Proveedor;
import com.canfer.app.model.Usuario;
import com.canfer.app.model.Usuario.UsuarioProveedor;



public class UserPrincipal implements UserDetails {
	
	private Usuario usuario;
	
	public UserPrincipal(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		//Extract list of permissions
		this.usuario.getPermisosList().forEach(p -> {
			GrantedAuthority authority = new SimpleGrantedAuthority(p);
			authorities.add(authority);
		});
		
		//Extract role
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+this.usuario.getRol());
		authorities.add(authority);
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.usuario.getPassword();
	}

	@Override
	public String getUsername() {
		return this.usuario.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.usuario.getActivo();
	}
	
	public boolean isAdmin() {
		return this.usuario.getRol().equalsIgnoreCase("ADMIN");
	}
	
	public String getCorreo() {
		return this.usuario.getCorreo();
	}
	
	public Long getUserId() {
		return this.usuario.getIdUsuario();
	}
	
	public List<Long> getEmpresaIds() {
		return this.usuario.getEmpresasId();
	}
	
	public List<String> getEmpresas() {
		return this.usuario.getEmpresasNombre();
	}
	
	public List<String> getEmpresasRfc() {
		return this.usuario.getEmpresasRfc();
	}
	
	
	public String getName() {
		return this.usuario.getNombre() + "+" +this.usuario.getApellido();
	}
	

}
