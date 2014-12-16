package org.demis.familh.core.elasticsearch.service;

import org.demis.familh.core.dto.DocumentDTO;
import org.demis.familh.core.elasticsearch.converter.DocumentConverter;
import org.demis.familh.core.jpa.entity.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value = "documentESService")
public class DocumentESService extends ESService<Document, DocumentDTO> {

    public static final String DOCUMENT_MAPPING = "document";

    @Autowired
    @Qualifier("documentConverterES")
    private DocumentConverter documentConverter;

    @Override
    protected DocumentConverter getConverter() {
        return documentConverter;
    }

    @Override
    protected String getMapping() {
        return DOCUMENT_MAPPING;
    }

    @Override
    protected Class<DocumentDTO> getDTOClass() {
        return DocumentDTO.class;
    }

}
