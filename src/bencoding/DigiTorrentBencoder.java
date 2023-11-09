package bencoding;

import bencoding.bencode.BElement;
import bencoding.bencode.BReader;
import exceptions.InvalidTorrentFileException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DigiTorrentBencoder {

    // TODO: Throws some awful exception...
    private final static String TEMP_FILE_URI = "/home/deyan/Documents/Coding/DigiTorrent/src/temp/sinkhole.torrent";
    private static int index;
    private static int ins;
    private static Long pieceLength;
    
    public static void main(String[] args) throws IOException, InvalidTorrentFileException {
        final BElement[] elements3 = BReader.read("i523e5:abcdel4:spam4:eggsed3:cow3:moo4:spam4:eggse");
        System.out.println(Arrays.toString(elements3));
    }

    public static Object decode(byte[] data) {
        List<Object> bencodedData = new ArrayList<>();

        while (data.length > 0) {

            // It's a single object, fix it
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
            str.append(chars[index++]); // TODO: It throws index out of bounds here...
        }

        return str.toString();
    }

    private static Long decodeInteger(char[] chars) {
        index++; // Skip 'i'
        ins++;
        int endIndex = index;
        while (chars[endIndex] != 'e') {
            endIndex++;
        }
        ins--;
        String intStr = new String(chars, index, endIndex - index);
        index = endIndex; // move the pointer to the end index
        index++; // Skip the 'e'

        return Long.parseLong(intStr);
    }

    private static List<Object> decodeList(char[] chars) {
        index++; // Skip 'l'
        ins++;
        List<Object> list = new ArrayList<>();
        while (chars[index] != 'e') {
            Object value = decodeValue(chars);
            list.add(value);
        }
        ins--;
        index++; //skip 'e'
        return list;
    }

    private static Map<String, Object> decodeDictionary(char[] chars) {
        index++; // Skip 'd'
        ins++;
        Map<String, Object> dict = new LinkedHashMap<>();
        while (chars[index] != 'e') {

            String key = decodeString(chars);
            Object value;

            if ("pieces".equals(key)) {
                value = decodePieces(chars, dict);
            } else {
                value = decodeValue(chars);
            }

            dict.put(key, value);
        }
        ins--;
        index++; // Skip 'e'
        return dict;
    }

    private static Object decodePieces(char[] chars, Map<String, Object> dict) {
        // Get the array length
        int length = 0;
        while (Character.isDigit(chars[index])) {
            length = length * 10 + (chars[index] - '0');
            index++;
        }
        if (chars[index] != ':') {
            return null;
        }
        index++;

        // Get a copy of the array
        int start = index;
        int end = chars.length - ins;
        long pieceLength = (long) dict.get("piece length");
        char[] extractedPieces = getPiecesString(chars, start, end);
        int singleHashLength = 20;

        List<char[]> pieces = new ArrayList<>();

        for (int index = 0; index < extractedPieces.length; index += singleHashLength) {
            char[] piecesHash = Arrays.copyOfRange(extractedPieces, index, index + singleHashLength);
            pieces.add(piecesHash);
        }

        return pieces;
    }

    private static char[] getPiecesString(char[] chars, int start, int end) {
        char[] extracted = new char[end - start];

        for (int i = 0; i < end - start; i++) {
            extracted[i] = chars[index + i];
        }

        return extracted;
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
