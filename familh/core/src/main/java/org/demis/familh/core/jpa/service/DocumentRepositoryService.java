package org.demis.familh.core.jpa.service;

import org.demis.familh.core.jpa.entity.Document;
import org.demis.familh.core.jpa.repository.DocumentRepository;
import org.demis.familh.core.service.ModelNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service(value ="documentRepositoryService")
public class DocumentRepositoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentRepositoryService.class);

    @Resource(name = "documentRepository")
    private DocumentRepository documentRepository;

    @Transactional
    public Document create(Document created) {
        return documentRepository.save(created);
    }

    @Transactional
    public Document delete(Long id) throws ModelNotFoundException {
        Document deleted = documentRepository.findOne(id);

        if (deleted == null) {
            LOGGER.debug("No Document found with id: " + id);
            throw new ModelNotFoundException();
        }

        documentRepository.delete(deleted);
        return deleted;

    }

    @Transactional(readOnly = true)
    public List<Document> findAll() {
        return documentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Document> findPart(int page, int size) {
        return documentRepository.findAll(new PageRequest(page, size)).getContent();
    }


    @Transactional(readOnly = true)
    public Document findById(Long id) {
        return documentRepository.findOne(id);
    }

    @Transactional(rollbackFor = ModelNotFoundException.class)
    public Document update(Document updated) throws ModelNotFoundException {
        Document document = documentRepository.findOne(updated.getId());

        if (document == null) {
            LOGGER.debug("No Document found with id: " + updated.getId());
            throw new ModelNotFoundException();
        } else {
            documentRepository.save(updated);
        }

        return document;

    }
}
