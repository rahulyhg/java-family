package org.demis.familh.core.service;

import org.demis.familh.core.elasticsearch.service.DocumentESService;
import org.demis.familh.core.jpa.entity.Document;
import org.demis.familh.core.jpa.service.DocumentRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("documentService")
public class DocumentService {

    @Autowired
    @Qualifier("documentRepositoryService")
    private DocumentRepositoryService repositorySevice;

    @Autowired
    @Qualifier ("documentESService")
    private DocumentESService elasticSearchService;

    @Transactional
    public Document create(Document created) {
        Document document = repositorySevice.create(created);
        elasticSearchService.create(document);
        return document;
    }

    @Transactional
    public Document delete(Long id) throws ModelNotFoundException {
        Document document = repositorySevice.delete(id);
        elasticSearchService.delete(id);
        return document;
    }

    @Transactional
    public List<Document> findAll() {
        return repositorySevice.findAll();
    }

    @Transactional
    public List<Document> findPart(int page, int size) {
        return repositorySevice.findPart(page, size);
    }

    @Transactional
    public Document findById(Long id) {
        return repositorySevice.findById(id);
    }

    @Transactional
    public Document update(Document updated) throws ModelNotFoundException {
        Document document = repositorySevice.update(updated);
        elasticSearchService.update(document);
        return document;
    }
}
