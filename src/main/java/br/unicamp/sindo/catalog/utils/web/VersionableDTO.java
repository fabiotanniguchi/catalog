package br.unicamp.sindo.catalog.utils.web;

import java.util.UUID;

public abstract class VersionableDTO {

    public abstract UUID getId();

    public abstract String version();
}
