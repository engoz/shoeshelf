package com.shoeshelf.util;

import com.shoeshelf.domain.BaseEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class Converter<D, E> {

    private final Function<D, E> fromDto;
    private final Function<E, D> fromEntity;

    public Converter(final Function<D, E> fromDto, final Function<E, D> fromEntity) {
        this.fromDto = fromDto;
        this.fromEntity = fromEntity;
    }

    public final E convertFromDto(final D dto) {
        return fromDto.apply(dto);
    }

    public final D convertFromEntity(final E entity) {
        return fromEntity.apply(entity);
    }

    public final List<E> createFromDtos(final Collection<D> dtos) {
        List<E> list = new ArrayList<>();
        for (D dto : dtos) {
            E baseEntity = convertFromDto(dto);
            list.add(baseEntity);
        }
        return list;
    }

    public final List<D> createFromEntities(final Collection<E> entities) {
        return entities.stream().map(this::convertFromEntity).collect(Collectors.toList());
    }
}
