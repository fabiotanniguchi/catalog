package br.unicamp.sindo.catalog.utils.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class ETaggedResponseEntity {

    public static <T extends VersionableDTO> ResponseEntity<T> ok(T dto, String etag) {
        MultiValueMap<String, String> headers = HeaderBuilder.init().eTag(dto.version()).assemble();

        if (dto.version().equals(etag)) {
            return new ResponseEntity<>(headers, HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<T>(dto, headers, HttpStatus.OK);
    }

    public static <T extends VersionableDTO> ResponseEntity<Void> created(T dto) {
        MultiValueMap<String, String> headers = HeaderBuilder.init().eTag(dto.version()).location(dto.getId()).assemble();
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    public static <T extends VersionableDTO> ResponseEntity<Void> updated(T dto) {
        MultiValueMap<String, String> headers = HeaderBuilder.init().eTag(dto.version()).location(dto.getId()).assemble();
        return new ResponseEntity<Void>(headers, HttpStatus.NO_CONTENT);
    }
}
