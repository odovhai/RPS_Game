package com.rubanj.casino.rockpaperscissors.converter;

import java.util.List;
import java.util.stream.Collectors;

public interface EntityDtoConverter<E, D> {

    D toDto(E source);

    E toEntity(D source);

    default List<D> toDtoList(List<E> source) {
        return source.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    default List<E> toEntityList(List<D> source) {
        return source.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

}
