package bencoding.bencode;

import bencoding.bencode.types.BList;
import bencoding.bencode.types.BMap;
import bencoding.bencode.types.BNumber;
import bencoding.bencode.types.BString;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Big thanks to Francis Mallochboe
 * */

public class BReader {

    /**
     * @param encoded the string to decode
     * @return an array of Bencode elements, decoded from the @param encoded
     */
    public static BElement[] read(final String encoded) {
        final AtomicInteger index = new AtomicInteger(0);
        final List<BElement> elements = new ArrayList<BElement>();
        while (index.get() != encoded.length()) {
            elements.add(read(encoded, index));
        }
        return elements.toArray(new BElement[elements.size()]);
    }

    public static BElement read(final String encoded, final AtomicInteger index) {
        switch (encoded.charAt(index.get())) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return BString.read(encoded, index);
            case 'i':
                return BNumber.read(encoded, index);
            case 'l':
                return BList.read(encoded, index);
            case 'd':
                return BMap.read(encoded, index);
        }
        throw new RuntimeException("Failed to identify type{" + encoded.charAt(index.get()) + "}");
    }

}
