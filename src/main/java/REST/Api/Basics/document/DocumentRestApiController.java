package REST.Api.Basics.document;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/api/documents")
public class DocumentRestApiController {

    private Collection<Document> documents = new ArrayList<>();

    @GetMapping
    public Iterable<Document> getDocument() {
        return documents;
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

}
