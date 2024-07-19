package com.vuongle.imaginepg.domain.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vuongle.imaginepg.application.commands.CreateConversationCommand;
import com.vuongle.imaginepg.application.dto.ConversationDto;
import com.vuongle.imaginepg.application.exceptions.DataNotFoundException;
import com.vuongle.imaginepg.application.exceptions.DataNotValidException;
import com.vuongle.imaginepg.application.exceptions.NoPermissionException;
import com.vuongle.imaginepg.application.exceptions.UserNotFoundException;
import com.vuongle.imaginepg.application.queries.ConversationFilter;
import com.vuongle.imaginepg.application.queries.UserConversationFilter;
import com.vuongle.imaginepg.domain.constants.ChatType;
import com.vuongle.imaginepg.domain.entities.Conversation;
import com.vuongle.imaginepg.domain.entities.User;
import com.vuongle.imaginepg.domain.entities.UserConversation;
import com.vuongle.imaginepg.domain.repositories.BaseQueryRepository;
import com.vuongle.imaginepg.domain.repositories.BaseRepository;
import com.vuongle.imaginepg.domain.services.ConversationService;
import com.vuongle.imaginepg.domain.services.UserConversationService;
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

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class ConversationServiceImpl implements ConversationService {

    private final BaseQueryRepository<Conversation> conversationQueryRepository;
    private final BaseRepository<Conversation> conversationRepository;

    private final UserConversationService userConversationService;
    private final BaseQueryRepository<User> userQueryRepository;

    private final ObjectMapper objectMapper;

    public ConversationServiceImpl(
            BaseQueryRepository<Conversation> conversationQueryRepository,
            BaseRepository<Conversation> conversationRepository,
            BaseQueryRepository<User> userQueryRepository,
            UserConversationService userConversationService,
            ObjectMapper objectMapper
    ) {
        this.conversationRepository = conversationRepository;
        this.conversationQueryRepository = conversationQueryRepository;
        this.userQueryRepository = userQueryRepository;
        this.objectMapper = objectMapper;
        this.userConversationService = userConversationService;
    }


    @Override
    public boolean addUserToGroupChat(UUID conversationId, UUID userId) {
        Conversation existed = getById(conversationId, Conversation.class);

        User participant = userQueryRepository.getById(userId);

        if (!existed.isGroupChat()) {
            if (existed.getParticipants().size() < 2) {
                existed.addParticipant(participant);
            } else throw new DataNotValidException("This conversation is not allowed to add more than 2 members");
        } else {
            existed.addParticipant(participant);
        }

        conversationRepository.save(existed);

        return true;
    }

    @Override
    public boolean removeUserFromGroupChat(UUID conversationId, UUID userId) {
        Conversation existed = getById(conversationId, Conversation.class);

        if (Context.getUser() == null) throw new UserNotFoundException("Not found current user");

        if (Objects.equals(Context.getUser().getId(), userId)) {
            throw new DataNotValidException("Cannot remove yourself");
        }

        existed.getParticipants().removeIf(p -> p.getId().equals(userId));

        conversationRepository.save(existed);

        return true;
    }

    @Override
    public Conversation getByFilter(ConversationFilter filter) {
        Specification<Conversation> specification = ConversationSpecifications.withFilter(filter);
        List<Conversation> conversations = conversationQueryRepository.findAll(specification);

        if (!conversations.isEmpty()) {
            return conversations.get(0);
        }

        return null;
    }

    @Override
    public Page<ConversationDto> getAllByCurrentUser(Pageable pageable) {

        User user = Context.getUser();

        if (Objects.isNull(user)) throw new UserNotFoundException("Not found current user");

        ConversationFilter filter = new ConversationFilter();
        filter.setEqualParticipantIds(List.of(user.getId()));

        return getPageable(filter, pageable);
    }

    @Override
    public ConversationDto getById(UUID id) {
        return getById(id, ConversationDto.class);
    }

    @Override
    public <R> R getById(UUID id, Class<R> classType) {
        Conversation conversation = conversationQueryRepository.getById(id);

        if (Objects.isNull(conversation)) throw new DataNotFoundException("Not found conversation has id " + id);

        User user = Context.getUser();

        if (Objects.isNull(user)) throw new UserNotFoundException("Not found current user");

        // check permission
        if (!Context.hasModifyPermission() && !conversation.hasParticipant(user.getId()) && !conversation.isOwner()) {
            throw new NoPermissionException("No permission");
        }

        UserConversationFilter settingFilter = new UserConversationFilter();
        settingFilter.setConversationId(conversation.getId());
        settingFilter.setUserId(user.getId());
        UserConversation settings = userConversationService.getByFilter(settingFilter, UserConversation.class);

        // TODO: get some latest message?

        if (settings != null) {
            conversation.setSettings(settings);
        }

        return ObjectData.mapTo(conversation, classType);
    }

    @Override
    public ConversationDto create(CreateConversationCommand command) {

        if (Context.getUser() == null) throw new UserNotFoundException("Current user not found");

        if (!command.isGroupChat()) {
            if (command.getAddParticipants().size() > 2) {
                throw new DataNotValidException("Conversation is not allowed add more than 2 members");
            }

            command.getAddParticipants().add(Context.getUser().getId());

            // find existed conversation (not group, and has two participants)
            ConversationFilter filter = new ConversationFilter();
            filter.setGroupChat(false);
            filter.setType(ChatType.PRIVATE);
            filter.setEqualParticipantIds(command.getAddParticipants());
            Conversation existed = getByFilter(filter);

            if (Objects.nonNull(existed)) return ObjectData.mapTo(existed, ConversationDto.class);
        }

        Conversation conversation = objectMapper.convertValue(command, Conversation.class);

        // find participants
        List<User> users = findParticipants(command.getAddParticipants());
        conversation.setParticipants(users);
        conversation.setUser(Context.getUser());

        // save conversation before save conversation settings
        conversation = conversationRepository.save(conversation);

        // create default conversation for user
        userConversationService.create(Context.getUser(), conversation);

        return ObjectData.mapTo(conversation, ConversationDto.class);
    }

    @Override
    public ConversationDto update(UUID id, CreateConversationCommand command) {

        Conversation existed = getById(id, Conversation.class);

        if (Objects.nonNull(command.getAddParticipants())) {

            List<User> users = findParticipants(command.getAddParticipants());

            existed.addListParticipants(users);
        }

        if (!command.isGroupChat()) {
            if (existed.getParticipants().size() > 2) {
                throw new DataNotValidException("Conversation is not allowed add more than 2 members");
            }
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

        Conversation conversation = getById(id, Conversation.class);

        if (force) {
            conversationRepository.deleteById(id);
            return;
        }

        conversation.setDeletedAt(Instant.now());
        conversationRepository.save(conversation);
    }

    @Override
    public Page<ConversationDto> getPageable(ConversationFilter filter, Pageable pageable) {
        Specification<Conversation> specification = ConversationSpecifications.withFilter(filter);

        Page<Conversation> conversationPage = conversationQueryRepository.findAll(specification, pageable);
        return conversationPage.map(c -> ObjectData.mapTo(c, ConversationDto.class));
    }

    @Override
    public List<ConversationDto> getList(ConversationFilter filter) {
        Specification<Conversation> specification = ConversationSpecifications.withFilter(filter);

        return ObjectData.mapListTo(conversationQueryRepository.findAll(specification), ConversationDto.class);
    }

    private List<User> findParticipants(List<UUID> participantIds) {
        Specification<User> userSpecification = UserSpecifications.inIds(participantIds);
        return userQueryRepository.findAll(userSpecification);
    }
}
