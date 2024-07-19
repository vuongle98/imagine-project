package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.application.commands.CreateTagCommand;
import com.vuongle.imaginepg.application.dto.TagDto;
import com.vuongle.imaginepg.application.exceptions.NoPermissionException;
import com.vuongle.imaginepg.application.queries.TagFilter;
import com.vuongle.imaginepg.domain.entities.Tag;
import com.vuongle.imaginepg.domain.repositories.BaseQueryRepository;
import com.vuongle.imaginepg.domain.repositories.BaseRepository;
import com.vuongle.imaginepg.domain.services.TagService;
import com.vuongle.imaginepg.infrastructure.specification.TagSpecifications;
import com.vuongle.imaginepg.shared.utils.Context;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import com.vuongle.imaginepg.shared.utils.Slugify;
import com.vuongle.imaginepg.shared.utils.ValidateResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    private final BaseQueryRepository<Tag> tagQueryRepository;
    private final BaseRepository<Tag> tagRepository;

    public TagServiceImpl(
            BaseQueryRepository<Tag> tagQueryRepository,
            BaseRepository<Tag> tagRepository
    ) {
        this.tagRepository = tagRepository;
        this.tagQueryRepository = tagQueryRepository;
    }

    @Override
    public TagDto getById(UUID id) {
        return getById(id, TagDto.class);
    }

    @Override
    public <R> R getById(UUID id, Class<R> classType) {

        Tag tag = tagQueryRepository.getById(id);

        // check permission
        if (!Context.hasModifyPermission() && !ValidateResource.isOwnResource(tag, Tag.class)) {
            throw new NoPermissionException("No permission");
        }

        return ObjectData.mapTo(tag, classType);
    }

    @Override
    public TagDto create(CreateTagCommand command) {

        Tag tag = ObjectData.mapTo(command, Tag.class);
        tag.setSlug(Slugify.toSlug(tag.getName()));
        tag.setUser(Context.getUser());

        tag = tagRepository.save(tag);
        return ObjectData.mapTo(tag, TagDto.class);
    }

    @Override
    public TagDto update(UUID id, CreateTagCommand command) {
        Tag tag = getById(id, Tag.class);

        if (Objects.nonNull(command.getName())) {
            tag.setName(command.getName());
            tag.setSlug(Slugify.toSlug(tag.getName()));
        }

        tag = tagRepository.save(tag);

        return ObjectData.mapTo(tag, TagDto.class);
    }

    @Override
    public void delete(UUID id, boolean force) {

        Tag tag = getById(id, Tag.class);

        if (force) {
            tagRepository.deleteById(id);
        } else {
            //
        }

        // recursive delete
    }

    @Override
    public Page<TagDto> getPageable(TagFilter filter, Pageable pageable) {
        Specification<Tag> specification = TagSpecifications.withFilter(filter);
        Page<Tag> tagPage = tagQueryRepository.findAll(specification, pageable);
        return tagPage.map(tag -> ObjectData.mapTo(tag, TagDto.class));
    }

    @Override
    public List<TagDto> getList(TagFilter filter) {
        Specification<Tag> specification = TagSpecifications.withFilter(filter);
        List<Tag> tagList = tagQueryRepository.findAll(specification);

        return ObjectData.mapListTo(tagList, TagDto.class);
    }
}
