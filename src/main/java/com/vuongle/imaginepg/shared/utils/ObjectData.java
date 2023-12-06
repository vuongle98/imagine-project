package com.vuongle.imaginepg.shared.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class ObjectData {

    public static <T> T mapTo(Object o, Class<T> classType) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(o, classType);
    }

    public static <T, S> List<T> mapListTo(List<S> objectList, Class<T> classType) {
        return objectList.stream().map(o -> mapTo(o, classType)).collect(Collectors.toList());
    }
}
