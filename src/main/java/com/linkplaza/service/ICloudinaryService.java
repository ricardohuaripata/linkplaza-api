package com.linkplaza.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface ICloudinaryService {
    Map upload(MultipartFile multipartFile, Map uploadParams) throws IOException;

    Map delete(String id) throws IOException;
}
