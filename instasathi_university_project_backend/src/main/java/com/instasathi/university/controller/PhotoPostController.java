package com.instasathi.university.controller;

import com.instasathi.university.models.CreatePhotoPostRequestDto;
import com.instasathi.university.models.PhotoPostResponseDto;
import com.instasathi.university.services.PhotoPostService;
import com.instasathi.university.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:4200")
public class PhotoPostController {

    private final PhotoPostService photoPostService;

    public PhotoPostController(PhotoPostService photoPostService) {
        this.photoPostService = photoPostService;
    }

    // ✅ CREATOR + ADMIN can create post
    @PreAuthorize("hasAnyRole('ROLE_CREATOR','ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PhotoPostResponseDto createPost(
            @RequestPart("data") @Valid CreatePhotoPostRequestDto data,
            @RequestPart("file") MultipartFile file
    ) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();

        return photoPostService.create(
                data,
                file,
                user.getId(),
                user.getUsername()
        );
    }

    // ✅ USER/CREATOR/ADMIN can view list
    @PreAuthorize("hasAnyRole('USER','CREATOR','ADMIN','MODERATOR')")
    @GetMapping
    public List<PhotoPostResponseDto> listPosts() {
        return photoPostService.listAll();
    }

    // ✅ USER/CREATOR/ADMIN can view details by id
    @PreAuthorize("hasAnyRole('USER','CREATOR','ADMIN','MODERATOR')")
    @GetMapping("/{id}")
    public PhotoPostResponseDto getPost(@PathVariable String id) {
        return photoPostService.getById(id);
    }
}
