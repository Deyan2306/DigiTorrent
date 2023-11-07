package bencoding.entity;

import java.util.List;
import java.util.Map;

public class DigiTorrentBencodedDTO {
    public List<Long> integers;
    public List<byte[]> bytes;
    public List<List<Object>> lists;
    public List<Map<String, Object>> dictionaries;

    public DigiTorrentBencodedDTO(List<Long> integers, List<byte[]> bytes, List<List<Object>> lists, List<Map<String, Object>> dictionaries) {
        this.integers = integers;
        this.bytes = bytes;
        this.lists = lists;
        this.dictionaries = dictionaries;
    }

    public  DigiTorrentBencodedDTO() { }

    public boolean addInteger(Long integer) {
        return this.getIntegers().add(integer);
    }

    //public boolean addByte() f life

    public List<Long> getIntegers() {
        return integers;
    }

    public DigiTorrentBencodedDTO setIntegers(List<Long> integers) {
        this.integers = integers;
        return this;
    }

    public List<byte[]> getBytes() {
        return bytes;
    }

    public DigiTorrentBencodedDTO setBytes(List<byte[]> bytes) {
        this.bytes = bytes;
        return this;
    }

    public List<List<Object>> getLists() {
        return lists;
    }

    public DigiTorrentBencodedDTO setLists(List<List<Object>> lists) {
        this.lists = lists;
        return this;
    }

    public List<Map<String, Object>> getDictionaries() {
        return dictionaries;
    }

    public DigiTorrentBencodedDTO setDictionaries(List<Map<String, Object>> dictionaries) {
        this.dictionaries = dictionaries;
        return this;
    }
}
