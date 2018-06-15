package com.kinlhp.steve.api.servico;

import com.kinlhp.steve.api.dominio.ContaReceber;
import com.kinlhp.steve.api.dominio.MovimentacaoContaReceber;
import com.kinlhp.steve.api.repositorio.RepositorioContaReceber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Comparator;

@Service
public class ServicoContaReceber implements Serializable {

	private static final long serialVersionUID = -6654327559563989841L;
	private final RepositorioContaReceber repositorio;

	public ServicoContaReceber(@Autowired RepositorioContaReceber repositorio) {
		this.repositorio = repositorio;
	}

	@Transactional
	public ContaReceber estornar(final BigInteger id) {
		final ContaReceber contaReceber = repositorio.findOne(id);
		// TODO: 6/14/18 implementar internacionalização
		Assert.notNull(contaReceber, "Recurso não encontrado");
		Assert.isTrue(!ContaReceber.Situacao.CANCELADO.equals(contaReceber.getSituacao()), "Conta a receber cancelado");
		final MovimentacaoContaReceber movimentacaoMaisRecente = contaReceber
				.getMovimentacoes().stream()
				.filter(p -> !p.isEstornado())
				.max(Comparator.comparing(MovimentacaoContaReceber::getDataCriacao))
				.orElseThrow(() -> new IllegalArgumentException("Conta a receber sem movimentação"));
		movimentacaoMaisRecente.setEstornado(true);
		contaReceber.setSituacao(contaReceber.hasMontantePago() ? ContaReceber.Situacao.AMORTIZADO : ContaReceber.Situacao.ABERTO);
		return repositorio.saveAndFlush(contaReceber);
	}
}
