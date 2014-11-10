package org.demis.familh.core.jpa.repository;

import org.demis.familh.core.jpa.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("documentRepository")
public interface DocumentRepository extends JpaRepository<Document, Long> {
}
