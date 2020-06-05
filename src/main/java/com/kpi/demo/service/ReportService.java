package com.kpi.demo.service;

import com.kpi.demo.dto.ReportDTO;
import com.kpi.demo.entity.Report;
import com.kpi.demo.entity.Room;
import com.kpi.demo.entity.User;
import com.kpi.demo.entity.enums.ReportStatus;
import com.kpi.demo.repository.ReportRepository;
import com.kpi.demo.repository.RoomRepository;
import com.kpi.demo.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public ReportService(ReportRepository reportRepository,
                         UserRepository userRepository,
                         RoomRepository roomRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    public Report createReport(User creator, Room room, ReportDTO reportDTO) {
        Report report = Report
                .builder()
                .creator(creator)
                .causer(userRepository.findById(reportDTO.getReportee()))
                .date(reportDTO.getDatetime())
                .comment(reportDTO.getComment())
                .image(reportDTO.getImage())
                .status(ReportStatus.IN_PROGRESS)
                .build();
        reportRepository.save(report);
        room.getReports().add(report);
        roomRepository.save(room);
        log.info("Report was saved. Report id : " + report.getId());
        return report;
    }

    public boolean isReportExist(User creator, Room room, ReportDTO reportDTO) {
        return reportRepository.findAll().stream()
                .anyMatch(report -> report.getDate().equals(reportDTO.getDatetime())
                        && report.getCreator().getId().equals(creator.getId()));
    }
}
