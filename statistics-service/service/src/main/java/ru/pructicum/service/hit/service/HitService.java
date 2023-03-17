package ru.pructicum.service.hit.service;

import ru.practicum.dto.HitDto;
import ru.practicum.dto.ViewStats;


import java.util.List;

public interface HitService {
    void add(HitDto hitDto);

    List<ViewStats> find(String start, String end, List<String> uris, Boolean unique);
}
