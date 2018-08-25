package com.kinlhp.steve.api.servico;

import com.kinlhp.steve.api.dominio.Pessoa;
import com.kinlhp.steve.api.repositorio.RepositorioPessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;

@Service
public class ServicoPessoa implements Serializable {

    private final RepositorioPessoa repositorio;
    private final ServicoPdf servicoPdf;

    public ServicoPessoa(@Autowired RepositorioPessoa repositorio, @Autowired ServicoPdf servicoPdf) {
        this.repositorio = repositorio;
        this.servicoPdf = servicoPdf;
    }

    public Path filtrar(final Pessoa pessoa) {
        final List<Pessoa> pessoas = repositorio.findAll(Example.of(pessoa));
        final String html = gerarHtml(pessoas);
        return servicoPdf.gerarPdf(html);
    }

    private String gerarHtml(List<Pessoa> pessoas) {
        final StringBuilder sb = new StringBuilder("<html>")
                .append("\n <head>")
                .append("\n  <meta charset=\"UTF-8\">")
                .append("\n </head>")
                .append("\n <table style=\"width:100%;border:1px solid #000;border-collapse:collapse border=1 cellpadding=6\">")
                .append("\n  <tbody>")
                .append("\n   <tr>")
                .append("\n    <td colspan=\"4\" style=\"text-align:center\"><strong>ConTorno Tornearia Mecanica</strong></td>")
                .append("\n   </tr>")
                .append("\n   <tr>")
                .append("\n    <td colspan=\"4\" style=\"text-align:center\"><strong>CNPJ 95.142.024/0001-91</strong></td>")
                .append("\n   </tr>")
                .append("\n   <tr>")
                .append("\n    <td colspan=\"4\" style=\"text-align:center\"><strong>Rua Pompeia, 627</strong></td>")
                .append("\n   </tr>")
                .append("\n   <tr>")
                .append("\n    <td colspan=\"4\" style=\"text-align:center\"><strong>(044) 3018-8103</strong></td>")
                .append("\n   </tr>")
                .append("\n  </tbody>")
                .append("\n </table>")
                .append("\n <br></br>")
                .append("\n <table style=\"width:100%;border:1px solid #cccccc;border-collapse:collapse;border:none\">")
                .append("\n  <thead>")
                .append("\n   <tr>")
                .append("\n    <th style=\"width:5%;border:1px solid #cccccc\">Código</th>")
                .append("\n    <th style=\"width:30%;border:1px solid #cccccc\">Nome/Razão Social</th>")
                .append("\n    <th style=\"width:35%;border:1px solid #cccccc\">Sobrenome/Fantasia</th>")
                .append("\n    <th style=\"width:10%;border:1px solid #cccccc\">CNPJ/CPF</th>")
                .append("\n    <th style=\"width:15%;border:1px solid #cccccc\">Abertura/Nascimento</th>")
                .append("\n    <th style=\"width:5%;border:1px solid #cccccc\">Situacao</th>")
                .append("\n   </tr>")
                .append("\n  </thead>")
                .append("\n  <tbody>");
        for (Pessoa p : pessoas) {
            sb.append("\n   <tr>")
                    .append("\n    <td style=\"border:1px solid #cccccc;text-align:right\">" + p.getId() + "</td>")
                    .append("\n    <td style=\"border:1px solid #cccccc\">" + p.getNomeRazao() + "</td>")
                    .append("\n    <td style=\"border:1px solid #cccccc\">" + p.getFantasiaSobrenome() + "</td>")
                    .append("\n    <td style=\"border:1px solid #cccccc;text-align:right\">" + p.getCnpjCpf() + "</td>")
                    .append("\n    <td style=\"border:1px solid #cccccc;text-align:center\">XPTO</td>")
                    .append("\n    <td style=\"border:1px solid #cccccc\">" + p.getSituacao().getDescricao() + "</td>")
                    .append("\n   </tr>");
        }
        sb.append("\n  </tbody>")
                .append("\n  <tfoot>")
                .append("\n   <tr>")
                .append("\n    <th colspan=\"3\" style=\"text-align:right\">Total:" + pessoas.size() + "</th>")
                .append("\n    <td style=\"text-align:right\"></td>")
                .append("\n   </tr>")
                .append("\n  </tfoot>")
                .append("\n </table>")
                .append("\n</html>");
        return sb.toString();
    }
}
