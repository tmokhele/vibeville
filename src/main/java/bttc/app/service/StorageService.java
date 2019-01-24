package bttc.app.service;

import bttc.app.model.FileUpload;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface StorageService {
  boolean  uploadFiles(List<FileUpload> fileUploads) throws IOException;
  Map<String, String> getFiles(String documentType) throws IOException;
}
