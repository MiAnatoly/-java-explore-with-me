package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.HitDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class HitController {
    private final HitClient hitClient;

    @PostMapping(path = "/hit")
    public ResponseEntity<Object> add(@Validated @RequestBody HitDto hitDto) {
        log.info("Post statistic");
        return hitClient.add(hitDto);
    }

    @GetMapping(path = "/stats")
    public ResponseEntity<Object> find(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam List<String> uris,
            @RequestParam Boolean unique) {
        log.info("Get statics");
        return hitClient.find(start, end, uris, unique);
    }

}
