package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.application.commands.CreateUserConversationCommand;
import com.vuongle.imaginepg.application.dto.UserConversationDto;
import com.vuongle.imaginepg.application.queries.UserConversationFilter;
import com.vuongle.imaginepg.domain.entities.Conversation;
import com.vuongle.imaginepg.domain.entities.User;
import com.vuongle.imaginepg.domain.entities.UserConversation;
import com.vuongle.imaginepg.domain.repositories.BaseQueryRepository;
import com.vuongle.imaginepg.domain.repositories.BaseRepository;
import com.vuongle.imaginepg.domain.services.UserConversationService;
import com.vuongle.imaginepg.infrastructure.specification.UserConversationSpecifications;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserConversationServiceImpl implements UserConversationService {

    private final BaseQueryRepository<UserConversation> userConversationQueryRepository;
    private final BaseRepository<UserConversation> userConversationRepository;

    public UserConversationServiceImpl(
            BaseQueryRepository<UserConversation> userConversationQueryRepository,
            BaseRepository<UserConversation> userConversationRepository
    ) {
        this.userConversationRepository = userConversationRepository;
        this.userConversationQueryRepository  = userConversationQueryRepository;
    }

    @Override
    public UserConversationDto getById(UUID id) {
        return null;
    }

    @Override
    public <R> R getById(UUID id, Class<R> classType) {
        return null;
    }

    @Override
    public UserConversationDto create(CreateUserConversationCommand command) {

        return null;
    }

    @Override
    public UserConversationDto update(UUID id, CreateUserConversationCommand command) {
        return null;
    }

    @Override
    public void delete(UUID id, boolean force) {

    }

    @Override
    public Page<UserConversationDto> getPageable(UserConversationFilter filter, Pageable pageable) {
        return null;
    }

    @Override
    public List<UserConversationDto> getList(UserConversationFilter filter) {
        return null;
    }

    @Override
    @Transactional
    public UserConversation create(User user, Conversation conversation) {
        UserConversation userConversation = new UserConversation(user, conversation);
        userConversation = userConversationRepository.save(userConversation);

        for (User participant : conversation.getParticipants()) {
            if (!participant.equals(user)) {
                UserConversation participantUserConversation = new UserConversation(participant, conversation);
                userConversationRepository.save(participantUserConversation);
            }
        }

        return userConversation;
    }

    @Override
    public <T> T getByFilter(UserConversationFilter filter, Class<T> classType) {
        Specification<UserConversation> specification = UserConversationSpecifications.withFilter(filter);
        List<UserConversation> conversations = userConversationQueryRepository.findAll(specification);

        if (!conversations.isEmpty()) {
            return ObjectData.mapTo(conversations.get(0), classType);
        }

        return null;
    }

    @Override
    public UserConversationDto getByFilter(UserConversationFilter filter) {
        return getByFilter(filter, UserConversationDto.class);
    }
}
