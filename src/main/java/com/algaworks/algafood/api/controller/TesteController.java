package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.infrastructure.repository.spec.RestauranteComFreteGratisSpec;
import com.algaworks.algafood.infrastructure.repository.spec.RestauranteComNomeSemelhanteSpec;
import com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/teste")
public class TesteController {

	private CozinhaRepository cozinhaRepository;
	private RestauranteRepository restauranteRepository;
	
	@GetMapping("/cozinhas/por-nome")
	public List<Cozinha> cozinhasPorNome(@RequestParam("nome") String nome) {
		return cozinhaRepository.findTodasByNomeContaining(nome);
	}
	
	@GetMapping("/cozinhas/unica-por-nome")
	public Optional<Cozinha> cozinhaPorNome(@RequestParam("nome") String nome) {
		return cozinhaRepository.findByNome(nome);
	}
	
	@GetMapping("/restaurantes/com-frete-gratis")
	public List<Restaurante> restauranteComFreteGratis(String nome){
		var comFreteGratis = new RestauranteComFreteGratisSpec();
		var comNomeSemelhante = new RestauranteComNomeSemelhanteSpec(nome);
		
		return restauranteRepository.findAll(comFreteGratis.and(comNomeSemelhante));
	}
	
	@GetMapping("/restaurantes/com-frete-gratis-2")
	public List<Restaurante> restauranteComFreteGratis2(String nome){
		
		return restauranteRepository.findAll(RestauranteSpecs.comFreteGratis()
				.and(RestauranteSpecs.comNomeSemelhante(nome)));
	}
	
	@GetMapping("/restaurantes/primeiro")
	public Optional<Restaurante> restaurantePrimeiro(String nome){
		
		return restauranteRepository.buscarPrimeiro();
	}
	
}