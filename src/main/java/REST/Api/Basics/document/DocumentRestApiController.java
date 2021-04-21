package REST.Api.Basics.document;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/documents")
public class DocumentRestApiController {

    private Collection<Document> documents = new ArrayList<>();

    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable("id") long number) {
        documents.removeIf(document -> document.getNumber() == number);
    }

    @PatchMapping("/{number}")
    public void updateDocument(@PathVariable long number,
                               @RequestBody Document newPartialDOcument) {
        findDocumentByNumber(number).ifPresent(document -> {
            if (newPartialDOcument.getTitle() != null) {
                document.setTitle(newPartialDOcument.getTitle());
            }
            if (newPartialDOcument.getTags() != null) {
                document.setTags(newPartialDOcument.getTags());
            }
        });
    }

    @PostMapping("/{number}")
    public void replaceDocument(@PathVariable long number,
                                @RequestBody Document newDocument) {
        findDocumentByNumber(number).ifPresent(document -> {
            document.setTitle(newDocument.getTitle());
            document.setTags(newDocument.getTags());
        });

    }

    @GetMapping
    public Iterable<Document> getDocument() {
        return documents;
    }

    @GetMapping("/{number}/title")
    public Optional<String> getTitleOfDocument(@PathVariable long number) {
        return documents.stream()
                .filter(document -> document.getNumber() == number)
                .findAny().map(Document::getTitle);
    }

    @GetMapping("/{number}")
    public Optional<Document> getDOcument(@PathVariable long number) {
        return documents.stream()
                .filter(document -> document.getNumber() == number).findAny();

    }

    @PostMapping
    public void addDocument(@RequestBody Document document) {
        documents.add(document);
    }

    @PostMapping(value = "/{documentNumber}/tags", consumes = MediaType.TEXT_PLAIN_VALUE)
    public void addTag(@PathVariable long documentNumber, @RequestBody String tag) {
        documents.stream()
                .filter(doc -> doc.getNumber() == documentNumber)
                .findAny()
                .ifPresent(document -> document.getTags().add(tag));
    }

    private Optional<Document> findDocumentByNumber(long number) {
        return documents.stream()
                .filter(document -> document.getNumber() == number)
                .findAny();
    }

}
