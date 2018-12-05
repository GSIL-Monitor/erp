package com.stosz.tms.model.aramex;

import java.io.Serializable;

public class Attachment implements Serializable{
    private String FileName;
    private String FileExtension;
    private byte[] FileContents;

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getFileExtension() {
        return FileExtension;
    }

    public void setFileExtension(String fileExtension) {
        FileExtension = fileExtension;
    }

    public byte[] getFileContents() {
        return FileContents;
    }

    public void setFileContents(byte[] fileContents) {
        FileContents = fileContents;
    }
}
