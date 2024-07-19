package com.vuongle.imaginepg.domain.services;

import com.vuongle.imaginepg.application.commands.CreateDiscountCommand;
import com.vuongle.imaginepg.application.dto.store.DiscountDto;
import com.vuongle.imaginepg.application.queries.DiscountFilter;

public interface DiscountService extends
        BaseService<DiscountDto, CreateDiscountCommand>,
        BaseQueryService<DiscountDto, DiscountFilter> {
}
