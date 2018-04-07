package br.unicamp.sindo.catalog.utils.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.List;

public class PageableResponseEntity<T> {

    public static <T> ResponseEntity<List<T>> ok(List<T> results, int page, int pageSize) {
        return ok(results, page, pageSize, null);
    }

    public static <T> ResponseEntity<List<T>> ok(List<T> results, int page, int pageSize, MultiValueMap<String, String> additionalHeaders) {
        HeaderBuilder builder = HeaderBuilder.init().page(page);

        if (page >= 2) {
            builder.previousPage(page - 1);
        }

        if (results.size() > pageSize) {
            builder.nextPage(page + 1);
        }

        MultiValueMap<String, String> headers = builder.assemble();

        if (additionalHeaders != null) {
            headers.addAll(additionalHeaders);
        }

        return new ResponseEntity<List<T>>(results.subList(0, results.size() > pageSize ? pageSize : results.size()), headers, HttpStatus.OK);

    }
}
