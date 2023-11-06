package filehandling;

import datahandling.HashList;
import filehandling.entities.DigiTorrentFile;
import filehandling.entities.DigiTorrentPiece;

import java.util.List;

public record DigiTorrentFileHandler(
        String announce,
        DigiTorrentInfoDictionary info,
        List<DigiTorrentFile> files,
        Long length,
        String name,
        Long pieceLength,
        HashList<DigiTorrentPiece> pieces

) { }
