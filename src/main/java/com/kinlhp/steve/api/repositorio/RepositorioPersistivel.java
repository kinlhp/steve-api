package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Persistivel;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface RepositorioPersistivel<T extends Persistivel<PK>, PK extends Serializable>
		extends JpaRepository<T, PK>, Serializable {

	@Override
	@PreAuthorize(value = "hasAuthority('SISTEMA') and #oauth2.hasScope('escrita')")
	void delete(PK pk);

	@Override
	@PreAuthorize(value = "hasAuthority('SISTEMA') and #oauth2.hasScope('escrita')")
	void delete(T entity);

	@Override
	@PreAuthorize(value = "hasAuthority('SISTEMA') and #oauth2.hasScope('escrita')")
	void delete(Iterable<? extends T> entities);

	@Override
	@PreAuthorize(value = "hasAuthority('SISTEMA') and #oauth2.hasScope('escrita')")
	void deleteAll();

	@Override
	@PreAuthorize(value = "hasAuthority('SISTEMA') and #oauth2.hasScope('escrita')")
	void deleteAllInBatch();

	@Override
	@PreAuthorize(value = "hasAuthority('SISTEMA') and #oauth2.hasScope('escrita')")
	void deleteInBatch(Iterable<T> entities);

	@Override
	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('leitura')")
	List<T> findAll();

	@Override
	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('leitura')")
	<S extends T> List<S> findAll(Example<S> example);

	@Override
	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('leitura')")
	List<T> findAll(Iterable<PK> pks);

	@Override
	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('leitura')")
	Page<T> findAll(Pageable pageable);

	@Override
	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('leitura')")
	List<T> findAll(Sort sort);

	@Override
	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('leitura')")
	<S extends T> Page<S> findAll(Example<S> example, Pageable pageable);

	@Override
	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('leitura')")
	<S extends T> List<S> findAll(Example<S> example, Sort sort);

	@Override
	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('leitura')")
	T findOne(PK pk);

	@Override
	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('leitura')")
	<S extends T> S findOne(Example<S> example);

	@Override
	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('leitura')")
	T getOne(PK pk);

	@Override
	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('escrita')")
	<S extends T> S save(S entity);

	@Override
	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('escrita')")
	<S extends T> List<S> save(Iterable<S> entities);

	@Override
	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('escrita')")
	<S extends T> S saveAndFlush(S entity);
}
