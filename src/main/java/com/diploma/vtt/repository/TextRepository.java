package com.diploma.vtt.repository;

import com.diploma.vtt.model.Doc;
import org.springframework.data.repository.CrudRepository;

public interface TextRepository  extends CrudRepository<Doc, Integer> {
}
