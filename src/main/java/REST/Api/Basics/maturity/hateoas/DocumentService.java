package REST.Api.Basics.maturity.hateoas;

import REST.Api.Basics.document.Document;
import REST.Api.Basics.maturity.util.DataFixtureUtils;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController("documentServiceL3")
@RequestMapping("/api/maturity/l3/documents")
public class DocumentService {
    private List<Document> documents = DataFixtureUtils.initDocuments();

    @GetMapping
    public Resources<Resource<Document>> getAllDocuments() {
        Resources<Resource<Document>> resources = new Resources<>(
                documents.stream().map(this::mapToResource)
                .collect(Collectors.toList()));
                addDocsLink(resources, "self");
                addFindDocsLink(resources, "docsByTitleAndNumber", null, null);
                return resources;
    }

    @GetMapping("/{number}")
    public Resource<Document> getDocument(
            @PathVariable("number") long number) {
        return documents.stream()
                .filter(document -> number == document.getNumber())
                .findAny().map(this::mapToResource).orElse(null);
    }

    @GetMapping(params = {"title", "number"})
    public ResponseEntity<Resources<Resource<Document>>> getDocumentsByTitleAndNumber(
            @RequestParam("title") String title,
            @RequestParam("number") Long number) {
        Resources<Resource<Document>> resources = new Resources<>(
                documents.stream().filter(document -> title
                        .equals(document
                                .getTitle()) && number == document
                        .getNumber()).map(this::mapToResource)
                        .collect(Collectors.toList()));
        addDocsLink(resources, "allDocs");
        addFindDocsLink(resources, "self", title, number);
        return ResponseEntity.ok()
                .header("Cache-Control", "max-age" + "=3600")
                .body(resources);
    }

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String getAllTitles() {
        return documents.stream().map(Document::getTitle)
                .reduce((acc, curr) -> String.join(",", acc, curr))
                .orElse("");
    }

    private Resource<Document> mapToResource(Document document) {
        Resource<Document> documentResource = new Resource<>(document);
        documentResource.add(linkTo(methodOn(DocumentService.class)
                .getDocument(document.getNumber())).withSelfRel());
        return documentResource;
    }

    private void addFindDocsLink(Resources<Resource<Document>> resources,
                                 String rel, String title, Long number) {
        resources.add(linkTo(methodOn(DocumentService.class)
                .getDocumentsByTitleAndNumber(title, number))
                .withRel(rel));
    }

    private void addDocsLink(Resources<Resource<Document>> resources,
                             String rel) {
        resources.add(linkTo(DocumentService.class).withRel(rel));
    }
}
