package datahandling;

import java.util.ArrayList;
import java.util.List;

public class HashList<T> {

    private List<T> hashes;

    public HashList() {
        this.hashes = new ArrayList<T>();
    }

    public int getAllHashesCount() {
        return this.hashes.size();
    }

    public boolean addHash(T hash) {
        return this.hashes.add(hash);
    }

    public boolean removeHash(T hash) {
        return this.hashes.remove(hash);
    }

    public boolean removeHashAt(int index) {
        T removed = this.hashes.remove(index);
        return null != removed;
    }

}
