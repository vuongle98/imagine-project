package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.application.commands.CreateConversationCommand;
import com.vuongle.imaginepg.application.dto.ConversationDto;
import com.vuongle.imaginepg.application.queries.ConversationFilter;
import com.vuongle.imaginepg.application.queries.UserFilter;
import com.vuongle.imaginepg.domain.entities.Conversation;
import com.vuongle.imaginepg.domain.entities.User;
import com.vuongle.imaginepg.domain.repositories.BaseRepository;
import com.vuongle.imaginepg.domain.services.ConversationService;
import com.vuongle.imaginepg.infrastructure.specification.ConversationSpecifications;
import com.vuongle.imaginepg.infrastructure.specification.UserSpecifications;
import com.vuongle.imaginepg.shared.utils.Context;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ConversationServiceImpl implements ConversationService {

    private final BaseRepository<Conversation> conversationRepository;
    private final BaseRepository<User> userRepository;

    public ConversationServiceImpl(
            BaseRepository<Conversation> conversationRepository,
            BaseRepository<User> userRepository
    ) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }


    @Override
    public void addUserToGroupChat(UUID conversationId, UUID userId) {

    }

    @Override
    public ConversationDto getById(UUID id) {
        return ObjectData.mapTo(conversationRepository.getById(id), ConversationDto.class);
    }

    @Override
    public ConversationDto create(CreateConversationCommand command) {

        Conversation conversation = ObjectData.mapTo(command, Conversation.class);

        // find participants
        List<User> users = findParticipants(command.getAddParticipants());
        conversation.setParticipants(users);
        conversation.setUser(Context.getUser());

        conversation = conversationRepository.save(conversation);

        return ObjectData.mapTo(conversation, ConversationDto.class);
    }

    @Override
    public ConversationDto update(UUID id, CreateConversationCommand command) {

        Conversation existed = conversationRepository.getById(id);

        if (Objects.nonNull(command.getAddParticipants())) {

            List<User> users = findParticipants(command.getAddParticipants());

            existed.addListParticipants(users);
        }

        if (Objects.nonNull(command.getRemoveParticipants())) {
            existed.removeParticipants(command.getRemoveParticipants());
        }

        if (Objects.nonNull(command.getTitle())) {
            existed.setTitle(command.getTitle());
        }

        existed.setAllowJoinRequests(command.isAllowJoinRequests());

        existed.setAllowMessageEditing(command.isAllowMessageEditing());

        existed = conversationRepository.save(existed);

        return ObjectData.mapTo(existed, ConversationDto.class);
    }

    @Override
    public void delete(UUID id, boolean force) {
        if (force) {
            conversationRepository.deleteById(id);
        }
    }

    @Override
    public Page<ConversationDto> getAll(ConversationFilter filter, Pageable pageable) {
        Specification<Conversation> specification = ConversationSpecifications.withFilter(filter);

        Page<Conversation> conversationPage = conversationRepository.findAll(specification, pageable);
        return conversationPage.map(c -> ObjectData.mapTo(c, ConversationDto.class));
    }

    @Override
    public List<ConversationDto> getAll(ConversationFilter filter) {
        Specification<Conversation> specification = ConversationSpecifications.withFilter(filter);

        return ObjectData.mapListTo(conversationRepository.findAll(specification), ConversationDto.class);
    }

    private List<User> findParticipants(List<UUID> participantIds) {
        UserFilter userFilter = new UserFilter();
        userFilter.setInIds(participantIds);
        Specification<User> userSpecification = UserSpecifications.withFilter(userFilter);
        return userRepository.findAll(userSpecification);
    }
}
