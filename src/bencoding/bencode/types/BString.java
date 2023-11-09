package bencoding.bencode.types;

import bencoding.bencode.BElement;

import java.util.concurrent.atomic.AtomicInteger;

public class BString implements BElement {

    public String value;

    public BString(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public String encode() {
        return value.length() + ":" + value;
    }

    /**
     * @param encoded the string we are decoding
     * @param index the index to read from
     */
    public static BString read(final String encoded, AtomicInteger index) {
        final int colonIndex = encoded.indexOf(':', index.get());
        final int length = Character.getNumericValue(encoded.charAt(index.get()));
        index.set(colonIndex + 1);
        final String value = encoded.substring(index.get(), index.get() + length);
        index.set(index.get() + length);
        return new BString(value);
    }
}
