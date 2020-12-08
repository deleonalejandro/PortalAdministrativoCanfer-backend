package com.canfer.app.repository;

import com.canfer.app.model.Archivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ArchivoBaseRepository<T extends Archivo> extends JpaRepository<T, Long> {

}
