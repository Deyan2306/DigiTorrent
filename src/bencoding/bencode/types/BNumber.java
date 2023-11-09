package bencoding.bencode.types;

import bencoding.bencode.BElement;

import java.util.concurrent.atomic.AtomicInteger;

public class BNumber implements BElement {

    public int value;

    public BNumber (final int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(final int value) {
        this.value = value;
    }

    @Override
    public String encode() {
        return "i" + this.value + "e";
    }

    public static BNumber read(final String encoded, final AtomicInteger index) {
        if (encoded.charAt(index.get()) == 'i') index.set(index.get() + 1);
        final int end = encoded.indexOf('e', index.get());
        final int value = Integer.parseInt(encoded.substring(index.get(), end));
        index.set(end + 1);
        return new BNumber(value);
    }
}
