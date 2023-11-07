package filehandling;

import datahandling.HashList;
import filehandling.entities.DigiTorrentFile;
import filehandling.entities.DigiTorrentPiece;

import java.util.List;

public class DigiTorrentFileDataHolder{
    private String announce;
    private DigiTorrentInfoDictionary info;
    private List<DigiTorrentFile> files;
    private Long length;
    private String name;
    private Long pieceLength;
    private HashList<DigiTorrentPiece> pieces;

    public DigiTorrentFileDataHolder() { }

    public DigiTorrentFileDataHolder(String announce,
                                     DigiTorrentInfoDictionary info,
                                     List<DigiTorrentFile> files,
                                     Long length,
                                     String name,
                                     Long pieceLength,
                                     HashList<DigiTorrentPiece> pieces) {
        this.announce = announce;
        this.info = info;
        this.files = files;
        this.length = length;
        this.name = name;
        this.pieceLength = pieceLength;
        this.pieces = pieces;
    }

    public DigiTorrentFileDataHolder setAnnounce(String announce) {
        this.announce = announce;
        return this;
    }

    public DigiTorrentFileDataHolder setInfo(DigiTorrentInfoDictionary info) {
        this.info = info;
        return this;
    }

    public DigiTorrentFileDataHolder setFiles(List<DigiTorrentFile> files) {
        this.files = files;
        return this;
    }

    public DigiTorrentFileDataHolder setLength(Long length) {
        this.length = length;
        return this;
    }

    public DigiTorrentFileDataHolder setName(String name) {
        this.name = name;
        return this;
    }

    public DigiTorrentFileDataHolder setPieceLength(Long pieceLength) {
        this.pieceLength = pieceLength;
        return this;
    }

    public DigiTorrentFileDataHolder setPieces(HashList<DigiTorrentPiece> pieces) {
        this.pieces = pieces;
        return this;
    }

    public String getAnnounce() {
        return announce;
    }
    public DigiTorrentInfoDictionary getInfo() {
        return info;
    }
    public List<DigiTorrentFile> getFiles() {
        return files;
    }
    public Long getLength() {
        return length;
    }
    public String getName() {
        return name;
    }
    public Long getPieceLength() {
        return pieceLength;
    }
    public HashList<DigiTorrentPiece> getPieces() {
        return pieces;
    }
}
