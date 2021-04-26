package REST.Api.Basics.document.api.dto;

import REST.Api.Basics.document.Document;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {
	public Document fromDto(AddDocumentDto command) {
		Document document = new Document();
		document.setTitle(command.getTitle());
		return document;
	}

	public DocumentDto toDto(Document document) {
		DocumentDto documentDto = new DocumentDto();
		documentDto.setId(document.getNumber());
		documentDto.setTitle(document.getTitle());
		return documentDto;
	}
}
