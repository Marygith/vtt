package com.diploma.vtt.repository;

import com.diploma.vtt.model.VideoEntity;
import org.springframework.data.repository.CrudRepository;

public interface VideoRepository extends CrudRepository<VideoEntity, Long> {
}
