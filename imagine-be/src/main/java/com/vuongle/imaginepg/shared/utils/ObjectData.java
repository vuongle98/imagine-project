package com.vuongle.imaginepg.shared.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ObjectData {

    public static <T> T mapTo(Object o, Class<T> classType) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setDeepCopyEnabled(false);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.getConfiguration().setPreferNestedProperties(false);
        return modelMapper.map(o, classType);
    }

    public static <T, S> List<T> mapListTo(List<S> objectList, Class<T> classType) {
        return objectList.stream().map(o -> mapTo(o, classType)).collect(Collectors.toList());
    }
}
