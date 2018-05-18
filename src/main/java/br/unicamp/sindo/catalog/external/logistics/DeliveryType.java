package br.unicamp.sindo.catalog.external.logistics;

import java.io.Serializable;

public enum DeliveryType implements Serializable {
    PAC {
        @Override
        public String toString() {
            return "PAC";
        }
    },
    SEDEX {
        @Override
        public String toString() {
            return "SEDEX";
        }
    };
}
