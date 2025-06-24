package com.space.backend.elastic;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface StudyRoomSearchRepository extends ElasticsearchRepository<StudyRoomDocument,Long> {

    List<StudyRoomDocument> findByNameContainingOrAddressContaining(String keyword1, String keyword2);

    List<StudyRoomDocument> findByNameContainingOrDescriptionContainingOrAddressContaining(String name, String description, String address);

    List<StudyRoomDocument> findByNameContaining(String name);
    List<StudyRoomDocument> findByAddressContaining(String address);
    List<StudyRoomDocument> findByDescriptionContaining(String description);

}
