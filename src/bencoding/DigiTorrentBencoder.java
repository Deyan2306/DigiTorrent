package bencoding;

import exceptions.InvalidTorrentFileException;
import exceptions.UnsupportedBencodeValue;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DigiTorrentBencoder {

    // Pretty much static atp
    private final static String TEMP_FILE_URI = "/home/deyan/Documents/Coding/DigiTorrent/src/temp/sinkhole.torrent";


    public static void main(String[] args) throws IOException, InvalidTorrentFileException, UnsupportedBencodeValue {
        String bencodedFile = bencodeFileToString(new File(TEMP_FILE_URI));
        Object bencodedData = decode(bencodedFile.getBytes());

        System.out.println(bencodedData);
    }

    public static Object decode(byte[] data) {
        return decode(new String(data, StandardCharsets.UTF_8));
    }

    public static Object decode(String s) {
        char[] characterData = s.toCharArray();
        int index = 0;
        return decodeValue(characterData, index);
    }

    private static Object decodeValue(char[] chars, int index) {
        char currentCharacter = chars[index];
        if (Character.isDigit(currentCharacter)) {
            return decodeString(chars, index);
        } else if (currentCharacter == 'i') {
            return decodeInteger(chars, index);
        } else if (currentCharacter == 'l') {
            return decodeList(chars, index);
        } else if (currentCharacter == 'd') {
            return decodeDictionary(chars, index);
        }

        return null;
    }

    private static String decodeString(char[] chars, int index) {
        int length = 0;
        while (Character.isDigit(chars[index])) {
            length = length * 10 + (chars[index] - '0');
            index++;
        }
        if (chars[index] != ':') {
            return null;
        }
        index++;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < length; i++) {
            str.append(chars[index + i]);
        }
        return str.toString();
    }

    private static Long decodeInteger(char[] chars, int index) {
        index++; // Skip 'i'
        int endIndex = index;
        while (chars[endIndex] != 'e') {
            endIndex++;
        }
        String intStr = new String(chars, index, endIndex - index);
        return Long.parseLong(intStr);
    }

    private static List<Object> decodeList(char[] chars, int index) {
        index++; // Skip 'l'
        List<Object> list = new ArrayList<>();
        while (chars[index] != 'e') {
            Object value = decodeValue(chars, index);
            list.add(value);
            index += value.toString().length();
        }
        return list;
    }

    private static Map<String, Object> decodeDictionary(char[] chars, int index) {
        index++; // Skip 'd'
        Map<String, Object> dict = new LinkedHashMap<>();
        while (chars[index] != 'e') {
            String key = decodeString(chars, index);
            index += key.length() + 2; // +2 to skip ':'
            Object value = decodeValue(chars, index);
            dict.put(key, value);
            index += value.toString().length();
        }
        return dict;
    }


    public static String bencodeFileToString(File file) throws IOException, InvalidTorrentFileException {
        FileInputStream stream = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        byte[] data = new byte[(int) file.length()];
        int bytesRead = stream.read(data);

        if (bytesRead == -1) {
            throw new InvalidTorrentFileException("The provided file is not existing");
        }

        String torrentContent = new String(data, StandardCharsets.UTF_8);

        reader.close();

        return torrentContent;
    }

}
