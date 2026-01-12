package com.instasathi.university.services;

import com.instasathi.university.models.CreatePhotoPostRequestDto;
import com.instasathi.university.models.PhotoPostResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PhotoPostService {
    PhotoPostResponseDto create(CreatePhotoPostRequestDto req, MultipartFile file, String creatorId, String creatorName) throws IOException;

    List<PhotoPostResponseDto> listAll();

    PhotoPostResponseDto getById(String id);
}
