package REST.Api.Basics.document.api;

import REST.Api.Basics.document.Document;
import REST.Api.Basics.document.service.DocumentAppService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = "/api/documents", produces =
	{MediaType.APPLICATION_JSON_VALUE, ApiVersion.V1_JSON})
public class DocumentRestApiControllerV1 {
	private DocumentAppService documentService;

	public DocumentRestApiControllerV1(DocumentAppService documentService) {
		this.documentService = documentService;
	}

	@GetMapping(params = "title")
	public Stream<Document> findDocumentsByTitle(
		@RequestParam("title") String title) {
		return documentService.findDocumentsByTitle(title);
	}

	@DeleteMapping("/{id}")
	public void deleteDocument(@PathVariable("id") long number) {
		documentService.deleteDocument(number);
	}

	@PatchMapping("/{number}")
	public void updateDocument(@PathVariable long number,
				   @RequestBody Document newPartialDocument) {
		documentService.updateDocument(number, newPartialDocument);
	}

	@PutMapping("/{number}")
	public void replaceDocument(@PathVariable long number,
				    @RequestBody Document newDocument) {
		documentService.replaceDocument(number, newDocument);
	}

	@GetMapping
	public Stream<Document> getDocuments() {
		return documentService.getDocuments();
	}

	@GetMapping("/{number}/title")
	public Optional<String> getTitleOfDocument(@PathVariable long number) {
		return documentService.getTitleOfDocument(number);
	}

	@GetMapping("/{number}")
	public Optional<Document> getDocument(@PathVariable long number) {
		return documentService.getDocument(number);
	}

	@PostMapping
	public void addDocument(@RequestBody Document document) {
		documentService.addDocument(document);
	}

	@PostMapping(value = "/{documentNumber}/tags", consumes =
		MediaType.TEXT_PLAIN_VALUE)
	public void addTag(@PathVariable long documentNumber,
			   @RequestBody String tag) {
		documentService.addTag(documentNumber, tag);
	}
}
