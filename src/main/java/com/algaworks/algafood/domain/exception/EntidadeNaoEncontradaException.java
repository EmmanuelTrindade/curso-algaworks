package com.algaworks.algafood.domain.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntidadeNaoEncontradaException extends NegocioException  {

	private static final long serialVersionUID = 1L;
	

	public EntidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
}
