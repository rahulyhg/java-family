package org.demis.familh.core.elasticsearch.converter;

import org.demis.familh.core.dto.DocumentDTO;
import org.demis.familh.core.jpa.entity.Document;
import org.springframework.stereotype.Service;

@Service(value = "documentConverterES")
public class DocumentConverter extends GenericConverter<Document, DocumentDTO> {

    public DocumentConverter() {
        super(Document.class, DocumentDTO.class);
    }

    @Override
    protected void updateModelFields(Document document, DocumentDTO documentDTO) {
    }

    @Override
    protected void updateDTOFields(DocumentDTO documentDTO, Document document) {
    }
}
