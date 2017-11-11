package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Persistivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.rest.core.annotation.RestResource;

import java.io.Serializable;

@NoRepositoryBean
public interface RepositorioPersistivel<T extends Persistivel<PK>, PK extends Serializable>
		extends JpaRepository<T, PK>, Serializable {

	@Override
	@RestResource(exported = false)
	void delete(PK pk);

	@Override
	@RestResource(exported = false)
	void delete(T entity);

	@Override
	@RestResource(exported = false)
	void delete(Iterable<? extends T> entities);

	@Override
	@RestResource(exported = false)
	void deleteAll();

	@Override
	@RestResource(exported = false)
	void deleteAllInBatch();

	@Override
	@RestResource(exported = false)
	void deleteInBatch(Iterable<T> entities);
}
