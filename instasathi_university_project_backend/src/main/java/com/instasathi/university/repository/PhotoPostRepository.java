package com.instasathi.university.repository;

import com.instasathi.university.models.PhotoPost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PhotoPostRepository extends MongoRepository<PhotoPost, String> {
    List<PhotoPost> findAllByOrderByUploadDateDesc();
}
