package com.kinlhp.steve.api.servico;

import com.kinlhp.steve.api.dominio.ContaPagar;
import com.kinlhp.steve.api.dominio.MovimentacaoContaPagar;
import com.kinlhp.steve.api.repositorio.RepositorioContaPagar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Comparator;

@Service
public class ServicoContaPagar implements Serializable {

	private static final long serialVersionUID = -6200177286746505902L;
	private final RepositorioContaPagar repositorio;

	public ServicoContaPagar(@Autowired RepositorioContaPagar repositorio) {
		this.repositorio = repositorio;
	}

	@Transactional
	public ContaPagar estornar(final BigInteger id) {
		final ContaPagar contaPagar = repositorio.findOne(id);
		// TODO: 6/14/18 implementar internacionalização
		Assert.notNull(contaPagar, "Recurso não encontrado");
		Assert.isTrue(!ContaPagar.Situacao.CANCELADO.equals(contaPagar.getSituacao()), "Conta a pagar cancelado");
		final MovimentacaoContaPagar movimentacaoMaisRecente = contaPagar
				.getMovimentacoes().stream()
				.filter(p -> !p.isEstornado())
				.max(Comparator.comparing(MovimentacaoContaPagar::getDataCriacao))
				.orElseThrow(() -> new IllegalArgumentException("Conta a pagar sem movimentação"));
		movimentacaoMaisRecente.setEstornado(true);
		contaPagar.setSituacao(contaPagar.hasMontantePago() ? ContaPagar.Situacao.AMORTIZADO : ContaPagar.Situacao.ABERTO);
		return repositorio.saveAndFlush(contaPagar);
	}
}
