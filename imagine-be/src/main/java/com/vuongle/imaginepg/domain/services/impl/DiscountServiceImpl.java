package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.application.commands.CreateDiscountCommand;
import com.vuongle.imaginepg.application.dto.store.DiscountDto;
import com.vuongle.imaginepg.application.exceptions.DataNotFoundException;
import com.vuongle.imaginepg.application.queries.DiscountFilter;
import com.vuongle.imaginepg.domain.entities.store.Discount;
import com.vuongle.imaginepg.domain.repositories.BaseQueryRepository;
import com.vuongle.imaginepg.domain.repositories.BaseRepository;
import com.vuongle.imaginepg.domain.services.DiscountService;
import com.vuongle.imaginepg.infrastructure.specification.DiscountSpecifications;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class DiscountServiceImpl implements DiscountService {

    private final BaseRepository<Discount> discountRepository;
    private final BaseQueryRepository<Discount> discountQueryRepository;

    public DiscountServiceImpl(
            BaseRepository<Discount> discountRepository,
            BaseQueryRepository<Discount> discountQueryRepository
    ) {
        this.discountRepository = discountRepository;
        this.discountQueryRepository = discountQueryRepository;
    }

    public <R> Optional<R> findById(UUID id, Class<R> classType) {
        return Optional.ofNullable(ObjectData.mapTo(discountQueryRepository.getById(id), classType));
    }

    @Override
    public DiscountDto getById(UUID id) {
        return findById(id, DiscountDto.class).orElseThrow(() -> new DataNotFoundException("Can not find discount with id: " + id));
    }

    @Override
    public <R> R getById(UUID id, Class<R> classType) {
        return findById(id, classType).orElseThrow(() -> new DataNotFoundException("Can not find discount with id: " + id));
    }

    @Override
    public Page<DiscountDto> getPageable(DiscountFilter filter, Pageable pageable) {
        Specification<Discount> specifications = DiscountSpecifications.withFilter(filter);

        Page<Discount> discountPage = discountQueryRepository.findAll(specifications, pageable);
        return discountPage.map(o -> ObjectData.mapTo(o, DiscountDto.class));
    }

    @Override
    public List<DiscountDto> getList(DiscountFilter filter) {
        return List.of();
    }

    @Override
    public DiscountDto create(CreateDiscountCommand command) {

        Discount discount = ObjectData.mapTo(command, Discount.class);

        discount = discountRepository.save(discount);

        return ObjectData.mapTo(discount, DiscountDto.class);
    }

    @Override
    public DiscountDto update(UUID id, CreateDiscountCommand command) {

        Discount discount = discountQueryRepository.getById(id);

        if (Objects.nonNull(command.getName())) {
            discount.setName(command.getName());
        }

        if (command.getValue() > 0) {
            discount.setValue(command.getValue());
        }

        if (Objects.nonNull(command.getUnit())) {
            discount.setUnit(command.getUnit());
        }

        return ObjectData.mapTo(discountRepository.save(discount), DiscountDto.class);
    }

    @Override
    public void delete(UUID id, boolean force) {
        discountRepository.deleteById(id);
    }
}
