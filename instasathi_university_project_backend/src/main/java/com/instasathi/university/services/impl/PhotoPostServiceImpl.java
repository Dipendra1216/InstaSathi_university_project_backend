package com.instasathi.university.services.impl;

import com.instasathi.university.models.CreatePhotoPostRequestDto;
import com.instasathi.university.models.PhotoPost;
import com.instasathi.university.models.PhotoPostResponseDto;
import com.instasathi.university.repository.PhotoPostRepository;
import com.instasathi.university.services.FileStorageService;
import com.instasathi.university.services.PhotoPostService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhotoPostServiceImpl implements PhotoPostService {
    private final PhotoPostRepository repo;
    private final FileStorageService storage;

    @Value("${app.upload.public-base}")
    private String publicBase;

    public PhotoPostServiceImpl(PhotoPostRepository repo, FileStorageService storage) {
        this.repo = repo;
        this.storage = storage;
    }

    @Override
    public PhotoPostResponseDto create(CreatePhotoPostRequestDto req, MultipartFile file, String creatorId, String creatorName) throws IOException {
        String fileName = storage.saveImage(file);

        PhotoPost post = new PhotoPost();
        post.setTitle(req.getTitle());
        post.setCaption(req.getCaption());
        post.setLocation(req.getLocation());

        post.setCreatorId(creatorId);
        post.setCreatorName(creatorName);

        post.setImagePath(fileName);
        post.setTags(splitComma(req.getTags()));
        post.setPeople(splitComma(req.getPeople()));

        PhotoPost saved = repo.save(post);
        return toResponse(saved);
    }

    @Override
    public List<PhotoPostResponseDto> listAll() {
        return repo.findAllByOrderByUploadDateDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public PhotoPostResponseDto getById(String id) {
        PhotoPost post = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        return toResponse(post);
    }

    private List<String> splitComma(String s) {
        if (s == null || s.trim().isEmpty()) return List.of();
        return Arrays.stream(s.split(","))
                .map(String::trim)
                .filter(x -> !x.isEmpty())
                .collect(Collectors.toList());
    }

    private PhotoPostResponseDto toResponse(PhotoPost p) {
        PhotoPostResponseDto r = new PhotoPostResponseDto();
        r.id = p.getId();
        r.title = p.getTitle();
        r.caption = p.getCaption();
        r.location = p.getLocation();
        r.creatorId = p.getCreatorId();
        r.creatorName = p.getCreatorName();
        r.imageUrl = publicBase + "/" + p.getImagePath();
        r.uploadDate = p.getUploadDate();
        r.likes = p.getLikes();
        r.views = p.getViews();
        r.rating = p.getRating();
        r.tags = p.getTags();
        r.people = p.getPeople();
        r.comments = List.of();
        return r;
    }
}
