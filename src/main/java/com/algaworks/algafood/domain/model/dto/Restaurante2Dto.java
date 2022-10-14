package com.algaworks.algafood.domain.model.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Restaurante2Dto {

	private Long id;
	private String nome;
	private BigDecimal taxaFrete;
}
