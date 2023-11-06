package bencoding;

import exceptions.InvalidTorrentFileException;
import exceptions.UnsupportedBencodeValue;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class DigiTorrentBencoder {

    public static void main(String[] args) throws IOException, InvalidTorrentFileException {
        String bencodedFile = bencodeFileToString(new File("/home/deyan/Documents/Coding/DigiTorrent/src/temp/sinkhole.torrent"));

        System.out.println((char) bencodedFile.charAt(5));
    }

    public static void handleBencodedString(String string) throws UnsupportedBencodeValue {
        char[] sequenceData = string.toCharArray();

        for (int index = 0; index < sequenceData.length; index++) {

            if (sequenceData[index] == 'i') { // Most probably we have a integer

            } else if (Character.isDigit(sequenceData[index])) { // Most probably we have a byte

            } else if (sequenceData[index] == 'l') { // Most probably we have a list

            } else if (sequenceData[index] == 'd') { // Most probably we have a dictionary

            } else {
                throw new UnsupportedBencodeValue("This character is not indicating anything");
            }

        }

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

    public static Integer bencodeInt(String sequence) {
        return Integer.valueOf(sequence.substring(1, sequence.length() - 1));
    }

    public static char[] bencodeBytes(String sequence) {
        return sequence.split(":")[1].toCharArray();
    }

}
