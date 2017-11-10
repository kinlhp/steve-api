package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Persistivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface RepositorioPersistivel<T extends Persistivel<ID>, ID extends Serializable>
		extends JpaRepository<T, ID>, Serializable {
}
