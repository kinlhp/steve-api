package com.kinlhp.steve.api.recurso;

import com.kinlhp.steve.api.dominio.Pessoa;
import com.kinlhp.steve.api.servico.ServicoPessoa;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Path;

@RequestMapping
@RestController
public class RecursoPessoa implements Serializable {

    private final ServicoPessoa servico;

    public RecursoPessoa(@Autowired ServicoPessoa servico) {
        this.servico = servico;
    }

    @PostMapping(path = {"/api/v1/pessoas/filtro/pdf"}, produces = {MediaType.APPLICATION_PDF_VALUE})
    public @ResponseBody ResponseEntity<byte[]> filtrar(@RequestBody Pessoa pessoa) throws IOException {
        final Path path = servico.filtrar(pessoa);
        final InputStream file = new FileInputStream("/data/pessoas.pdf");
        return ResponseEntity.ok(IOUtils.toByteArray(file));
    }
}
