package br.unicamp.sindo.catalog.external.logistics;

import java.io.Serializable;

public enum PackageType implements Serializable {
    BOX {
        @Override
        public String toString() {
            return "Caixa";
        }
    },
    ENVELOPE {
        @Override
        public String toString() {
            return "envelope";
        }
    };
}
