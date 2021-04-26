package REST.Api.Basics.document.service;

import REST.Api.Basics.document.Document;
import REST.Api.Basics.document.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class DocumentAppService {

	private DocumentRepository documentRepository;

	public DocumentAppService(DocumentRepository documentRepository) {
		this.documentRepository = documentRepository;
	}

	public Stream<Document> findDocumentsByTitle(String title) {
		return documentRepository.findByTitle(title).stream();
	}

	public void deleteDocument(long number) {
		documentRepository.deleteById(number);
	}

	public void updateDocument(long number, Document newPartialDocument) {
		findDocumentByNumber(number).ifPresent(document -> {
			if (newPartialDocument.getTitle() != null) {
				document.setTitle(
					newPartialDocument.getTitle());
			}
			if (newPartialDocument.getTags() != null) {
				document.setTags(newPartialDocument.getTags());
			}
			documentRepository.save(document);
		});
	}

	public void replaceDocument(long number, Document newDocument) {
		newDocument.setNumber(number);
		documentRepository.save(newDocument);
	}

	public Stream<Document> getDocuments() {
		return documentRepository.findAll().stream();
	}

	public Optional<String> getTitleOfDocument(long number) {
		return findDocumentByNumber(number).map(Document::getTitle);
	}

	public Optional<Document> getDocument(long number) {
		return findDocumentByNumber(number);
	}

	public Document addDocument(Document document) {
		return documentRepository.save(document);
	}

	public void addTag(long documentNumber, String tag) {
		findDocumentByNumber(documentNumber).ifPresent(document -> {
			document.getTags().add(tag);
			documentRepository.save(document);
		});
	}

	private Optional<Document> findDocumentByNumber(long number) {
		return documentRepository.findById(number);
	}
}
