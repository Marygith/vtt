package com.diploma.vtt.repository;

import com.diploma.vtt.model.TextEntity;
import org.springframework.data.repository.CrudRepository;

public interface TextRepository  extends CrudRepository<TextEntity, Integer> {
}
