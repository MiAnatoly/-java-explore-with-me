package ru.pructicum.service.hit;

import ru.pructicum.service.hit.model.EndpointHit;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.dto.HitDto;

import java.time.LocalDateTime;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HitMapper {

    public static EndpointHit toHit(LocalDateTime created, HitDto hitDto) {
        return new EndpointHit(
                null,
                hitDto.getApp(),
                hitDto.getUri(),
                hitDto.getIp(),
                created
        );
    }
}
