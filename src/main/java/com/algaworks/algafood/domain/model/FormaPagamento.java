package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class FormaPagamento {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FORMAPGTO_GENERATOR")
    @SequenceGenerator(name = "FORMAPGTO_GENERATOR", sequenceName = "S_FORMAPGTO", allocationSize = 1)
	private Long id;
	
	@Column(nullable = false)
	private String descricao;

}
