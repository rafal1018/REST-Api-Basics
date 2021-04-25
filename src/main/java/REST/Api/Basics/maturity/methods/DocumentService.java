package REST.Api.Basics.maturity.methods;

import REST.Api.Basics.document.Document;
import REST.Api.Basics.maturity.util.DataFixtureUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController("documentServiceL2")
@RequestMapping("/api/maturity/l2/documents")
public class DocumentService {

    private List<Document> documents =
            DataFixtureUtils.initDocuments();

    @GetMapping(params = {"title", "number"})
    public ResponseEntity<List<Document>> getAllDocuments(@RequestParam("title") String title, @RequestParam("number") long number) {
        return ResponseEntity.ok().header("Cache-control", "max-age" +
                "=3600").body(documents.stream().filter(document -> title
                .equals(document
                        .getTitle()) && number == document
                .getNumber()).collect(Collectors.toList()));
    }

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String getAllTitles() {
        return documents.stream().map(Document::getTitle).reduce((acc
                , curr) -> String.join(",", acc, curr))
                .orElse("");
    }

    @PostMapping
    public void addDocument(@RequestBody Document document) {
        documents.add(document);
    }

    @DeleteMapping("/{number}")
    public ResponseEntity<?> removeDocument(@PathVariable("number") long number) {
        boolean anyElementRemoved = documents
                .removeIf(document -> document.getNumber() == number);
        if (anyElementRemoved) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
