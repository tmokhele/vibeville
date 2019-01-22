package bttc.app.service;

import bttc.app.model.FileUpload;

import java.io.IOException;
import java.util.List;

public interface StorageService {
  boolean  uploadFiles(List<FileUpload> fileUploads) throws IOException;
}
