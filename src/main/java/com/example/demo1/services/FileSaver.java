package com.example.demo1.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileSaver {
    File saveTextFile(List<String> lines) throws IOException;
    File saveImageFile(javafx.scene.image.WritableImage image) throws IOException;
}
