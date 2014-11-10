package org.demis.familh.web.converter;

import org.demis.familh.core.jpa.entity.Document;
import org.demis.familh.web.dto.DTOWeb;
import org.demis.familh.web.dto.DocumentDTOWeb;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service(value = "documentConverterWeb")
public class DocumentConverterWeb extends GenericConverterWeb<Document, DocumentDTOWeb> {

    public DocumentConverterWeb() {
        super(Document.class, DocumentDTOWeb.class);
    }

    protected void updateModelFields(Document document, DocumentDTOWeb documentDTO) {
    }

    protected void updateDTOFields(DocumentDTOWeb documentDTO, Document document) {
    }

    public void generateHref(DTOWeb dto, HttpServletRequest request) {
        super.generateHref(dto, request);
    }

}
