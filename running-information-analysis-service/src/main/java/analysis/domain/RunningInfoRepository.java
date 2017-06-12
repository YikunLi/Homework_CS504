package analysis.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lykly on 2017/6/11.
 */
@Repository
public interface RunningInfoRepository extends JpaRepository<RunningInfo, String> {

    Page<RunningInfo> findAll(Pageable pageable);

    void deleteByRunningId(@Param("runningId") String runningId);

}
