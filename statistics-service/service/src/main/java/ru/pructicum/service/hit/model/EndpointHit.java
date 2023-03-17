package ru.pructicum.service.hit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "ENDPOINT_HIT")
@NoArgsConstructor
@AllArgsConstructor
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "APP")
    private String app;
    @Column(name = "URI")
    private String uri;
    @Column(name = "IP")
    private String ip;
    @Column(name = "CREATED")
    private LocalDateTime timestamp;
}
