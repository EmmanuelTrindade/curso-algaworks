package com.algaworks.algafood.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean //diz que ela não será considerada para ser instanciada como repository
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID>{

	Optional<T> buscarPrimeiro();
}
