import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    private final static String TORRENT_URI = "/home/deyan/Documents/Coding/DigiTorrent/src/temp/sinkhole.torrent";

    public static void main(String[] args) {
        System.out.println(" == Wellcome to DigiTorrent == ");

        File torrentFile = new File(TORRENT_URI);

        try {
            FileInputStream inputStream = new FileInputStream(torrentFile);
            byte[] data = new byte[(int) torrentFile.length()];
            int bytesRead = inputStream.read(data);

            if (bytesRead != -1) {
                String torrentContents = new String(data, "UTF-8");
                System.out.println(torrentContents);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}