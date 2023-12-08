package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.application.commands.CreateTagCommand;
import com.vuongle.imaginepg.application.dto.TagDto;
import com.vuongle.imaginepg.application.queries.TagFilter;
import com.vuongle.imaginepg.domain.entities.Tag;
import com.vuongle.imaginepg.domain.repositories.BaseRepository;
import com.vuongle.imaginepg.domain.services.TagService;
import com.vuongle.imaginepg.infrastructure.specification.TagSpecifications;
import com.vuongle.imaginepg.shared.utils.Context;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import com.vuongle.imaginepg.shared.utils.Slugify;
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

    private final BaseRepository<Tag> tagRepository;

    public TagServiceImpl(
            BaseRepository<Tag> tagRepository
    ) {
        this.tagRepository = tagRepository;
    }

    @Override
    public TagDto getById(UUID id) {
        return ObjectData.mapTo(tagRepository.getById(id), TagDto.class);
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
        Tag tag = tagRepository.getById(id);

        if (Objects.nonNull(command.getName())) {
            tag.setName(command.getName());
            tag.setSlug(Slugify.toSlug(tag.getName()));
        }

        tag = tagRepository.save(tag);

        return ObjectData.mapTo(tag, TagDto.class);
    }

    @Override
    public void delete(UUID id, boolean force) {

        if (force) {
            tagRepository.deleteById(id);
        }
    }

    @Override
    public Page<TagDto> getAll(TagFilter filter, Pageable pageable) {
        Specification<Tag> specification = TagSpecifications.withFilter(filter);
        Page<Tag> tagPage = tagRepository.findAll(specification, pageable);
        return tagPage.map(tag -> ObjectData.mapTo(tag, TagDto.class));
    }

    @Override
    public List<TagDto> getAll(TagFilter filter) {
        Specification<Tag> specification = TagSpecifications.withFilter(filter);
        List<Tag> tagList = tagRepository.findAll(specification);

        return ObjectData.mapListTo(tagList, TagDto.class);
    }
}
