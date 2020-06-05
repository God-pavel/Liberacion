package com.kpi.demo.entity;

import com.kpi.demo.entity.enums.ReportStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator", nullable = false)
    private User creator;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "causer", nullable = false)
    private User causer;
    @Column(name = "comment")
    private String comment;
    @Column(name = "date", nullable = false)
    private LocalDateTime date;
    @Column(name = "image")
    private String image;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ReportStatus status;
}
