package ru.pructicum.service.hit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.ViewStats;
import ru.pructicum.service.hit.service.HitService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class HitController {
    private final HitService hitService;

    @PostMapping(path = "/hit")
    public void add(@Validated @RequestBody HitDto hitDto) {
        hitService.add(hitDto);
    }

    @GetMapping(path = "/stats")
    public List<ViewStats> find(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("Get statics service controller");
        return hitService.find(start, end, uris, unique);
    }
}
