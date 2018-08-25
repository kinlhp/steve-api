package com.kinlhp.steve.api.servico;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class ServicoPdf implements Serializable {

    public Path gerarPdf(String html) {
        try {
            final Path path = new File("/data/pessoas.pdf").toPath();
            final PdfWriter writer = new PdfWriter(Files.newOutputStream(path));
            final PdfDocument pdf = new PdfDocument(writer);
            final Document document = new Document(pdf);
            final List<IElement> elements = HtmlConverter.convertToElements(html);
            for (IElement element : elements) {
                document.add((IBlockElement) element);
            }
            document.close();
            return path;
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível criar arquivo PDF", e);
        }
    }
}
