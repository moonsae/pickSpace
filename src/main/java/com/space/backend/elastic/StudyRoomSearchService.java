package com.space.backend.elastic;

import com.space.backend.entity.StudyRoomEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StudyRoomSearchService {

    private final StudyRoomSearchRepository studyRoomSearchRepository;

    public void indexStudyRoom(StudyRoomEntity entity){
        StudyRoomDocument doc = StudyRoomDocument.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .address(entity.getSpaceGroup().getAddress())
                .status(entity.getStatus())
                .avgRating(entity.getAvgRating())
                .reviewCount(entity.getReviewCount())
                .pricePerHour(entity.getPricePerHour())
                .build();
        studyRoomSearchRepository.save(doc);
    }
    public List<StudyRoomDocument> searchStudyRoom(String keyword){
        return studyRoomSearchRepository.findByNameContainingOrDescriptionContainingOrAddressContaining(keyword,keyword,keyword);
    }



}
