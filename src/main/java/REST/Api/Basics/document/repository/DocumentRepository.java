package REST.Api.Basics.document.repository;

import REST.Api.Basics.document.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface DocumentRepository extends JpaRepository<Document, Long> {
	Collection<Document> findByTitle(String title);
}
