package bencoding;

import exceptions.InvalidTorrentFileException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DigiTorrentBencoder {

    // TODO: Throws some awful exception...
    private final static String TEMP_FILE_URI = "/home/deyan/Documents/Coding/DigiTorrent/src/temp/sinkhole.torrent";
    private static int index;
    
    public static void main(String[] args) throws IOException, InvalidTorrentFileException {
        String bencodedFile = bencodeFileToString(new File(TEMP_FILE_URI));
        Object bencodedData = decode(bencodedFile.getBytes());

        // TODO: Implement a data structure, which could hold all of the information in the bencoded data

        System.out.println(bencodedData);
    }

    public static Object decode(byte[] data) {
        List<Object> bencodedData = new ArrayList<>();

        while (data.length > 0) {
            bencodedData.add(decode(new String(data, StandardCharsets.UTF_8)));
            data = Arrays.copyOfRange(data, index, data.length);
        }

        return bencodedData;
    }

    public static Object decode(String s) {
        char[] characterData = s.toCharArray();
        index = 0;

        return decodeValue(characterData);
    }

    private static Object decodeValue(char[] chars) {
        char currentCharacter = chars[index];

        if (Character.isDigit(currentCharacter)) {
            return decodeString(chars);
        } else if (currentCharacter == 'i') {
            return decodeInteger(chars);
        } else if (currentCharacter == 'l') {
            return decodeList(chars);
        } else if (currentCharacter == 'd') {
            return decodeDictionary(chars);
        }

        return null;
    }

    private static String decodeString(char[] chars) {
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
            str.append(chars[index++]);
        }

        return str.toString();
    }

    private static Long decodeInteger(char[] chars) {
        index++; // Skip 'i'
        int endIndex = index;
        while (chars[endIndex] != 'e') {
            endIndex++;
        }

        String intStr = new String(chars, index, endIndex - index);
        index = endIndex; // move the pointer to the end index
        index++; // Skip the 'e'

        return Long.parseLong(intStr);
    }

    private static List<Object> decodeList(char[] chars) {
        index++; // Skip 'l'
        List<Object> list = new ArrayList<>();
        while (chars[index] != 'e') {
            Object value = decodeValue(chars);
            list.add(value);
            // index += value.toString().length(); TODO: Remove if it is working
        }
        index++; //skip 'e'
        return list;
    }

    private static Map<String, Object> decodeDictionary(char[] chars) {
        index++; // Skip 'd'
        Map<String, Object> dict = new LinkedHashMap<>();
        while (chars[index] != 'e') {

            String key = decodeString(chars);

            Object value = decodeValue(chars);
            dict.put(key, value);
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
