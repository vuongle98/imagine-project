package com.vuongle.imaginepg.infrastructure.specification;

import com.vuongle.imaginepg.application.queries.FileFilter;
import com.vuongle.imaginepg.domain.entities.File;
import com.vuongle.imaginepg.shared.utils.SqlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class FileSpecifications {

    public static Specification<File> withFilter(FileFilter filter) {
        Specification<File> specification = Specification.where(null);

        if (StringUtils.isNotBlank(filter.getLikeName())) {
            specification.and(likeName(filter.getLikeName()));
        }

        if (StringUtils.isNotBlank(filter.getExtension())) {
            specification.and(extension(filter.getExtension()));
        }

        return specification;
    }

    private static Specification<File> likeName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("filename"), SqlUtil.getLikePattern(name));
    }

    private static Specification<File> extension(String ext) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("extension"), ext);
    }
}
