package com.vuongle.imaginepg.infrastructure.specification;

import com.vuongle.imaginepg.application.queries.FileFilter;
import com.vuongle.imaginepg.domain.constants.FileType;
import com.vuongle.imaginepg.domain.entities.File;
import com.vuongle.imaginepg.shared.utils.SqlUtil;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileSpecifications {

    private static final List<String> IMAGE_FILE_TYPES = List.of("jpg", "png", "jpeg", "gif", "bmp", "svg");

    private static final List<String> VIDEO_FILE_TYPES = List.of("mp4", "mkv", "flv");

    private static final List<String> COMPRESS_FILE_TYPES = List.of("zip", "rar", "7z", "gz");

    private static final List<String> DOCUMENT_FILE_TYPES = List.of("pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx");

    private static final List<String> AUDIO_FILE_TYPES = List.of("mp3", "wav");

    public static Specification<File> withFilter(FileFilter filter) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(filter.getLikeName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), SqlUtil.getLikePattern(filter.getLikeName())));
            }

            if (StringUtils.isNotBlank(filter.getExtension())) {
                predicates.add(criteriaBuilder.equal(root.get("extension"), filter.getExtension()));
            }

            if (Objects.nonNull(filter.getFileType())) {

                switch (filter.getFileType()) {
                    case AUDIO -> predicates.add(root.get("extension").in(AUDIO_FILE_TYPES));
                    case VIDEO -> predicates.add(root.get("extension").in(VIDEO_FILE_TYPES));
                    case COMPRESS -> predicates.add(root.get("extension").in(COMPRESS_FILE_TYPES));
                    case DOCUMENT -> predicates.add(root.get("extension").in(DOCUMENT_FILE_TYPES));
                    default -> predicates.add(root.get("extension").in(IMAGE_FILE_TYPES));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static Specification<File> likeName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("filename"), SqlUtil.getLikePattern(name));
    }

    private static Specification<File> extension(String ext) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("extension"), ext);
    }

    private static Specification<File> isType(FileType type) {
        switch (type) {
            case AUDIO -> {
                return (root, query, criteriaBuilder) -> root.get("extension").in(AUDIO_FILE_TYPES);
            }
//            case IMAGE -> {
//                return (root, query, criteriaBuilder) -> root.get("extension").in(IMAGE_FILE_TYPES);
//            }
            case VIDEO -> {
                return (root, query, criteriaBuilder) -> root.get("extension").in(VIDEO_FILE_TYPES);
            }
            case COMPRESS -> {
                return (root, query, criteriaBuilder) -> root.get("extension").in(COMPRESS_FILE_TYPES);
            }
            case DOCUMENT -> {
                return (root, query, criteriaBuilder) -> root.get("extension").in(DOCUMENT_FILE_TYPES);
            }
            default -> {
                return (root, query, criteriaBuilder) -> root.get("extension").in(IMAGE_FILE_TYPES);
            }
        }
    }
}
