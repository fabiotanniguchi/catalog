package br.unicamp.sindo.catalog.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Mapper {

    private static ObjectMapper mapper = buildObjectMapper();

    private static ObjectMapper buildObjectMapper() {
        ObjectMapper m = new ObjectMapper();
        return m;
    }

    public static ObjectMapper getInstance() {
        return mapper;
    }
}
