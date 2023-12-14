package pl.edu.pb.wi.mmm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pb.wi.mmm.entity.RequestLog;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {
}