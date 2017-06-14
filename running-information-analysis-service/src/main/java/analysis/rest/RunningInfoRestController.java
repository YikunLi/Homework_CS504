package analysis.rest;

import analysis.domain.RunningInfo;
import analysis.domain.RunningInfoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lykly on 2017/6/11.
 */
@RestController
public class RunningInfoRestController {

    private RunningInfoRepository runningInfoRepository;

    @Autowired
    public RunningInfoRestController(RunningInfoRepository runningInfoRepository) {
        this.runningInfoRepository = runningInfoRepository;
    }

    private List<RunningInfo> findRunningInfosOrderByHeartRate(int page, int size) {
        Sort sort = new Sort(Sort.Direction.DESC, "heartRate");
        Pageable pageable = new PageRequest(page, size, sort);
        Page<RunningInfo> result = this.runningInfoRepository.findAll(pageable);
        return result.getContent();
    }

    @RequestMapping(value = "/running/search")
    public List<ObjectNode> findRunningInfosOrderByHealthWarningLevel(@RequestParam(name = "page") int page,
                                                                      @RequestParam(name = "size") int size) {
        List<RunningInfo> runningInfos = this.findRunningInfosOrderByHeartRate(page, size);
        List<ObjectNode> filterInfos = new ArrayList<ObjectNode>();
        if (runningInfos == null || runningInfos.size() == 0) {
            return filterInfos;
        }
        for (RunningInfo runningInfo : runningInfos) {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("runningId", runningInfo.getRunningId());
            objectNode.put("totalRunningTime", runningInfo.getTotalRunningTime());
            objectNode.put("heartRate", runningInfo.getHeartRate());
            if (runningInfo.getUserInfo() != null) {
                objectNode.put("userId", runningInfo.getUserInfo().getId());
                objectNode.put("userName", runningInfo.getUserInfo().getUsername());
                objectNode.put("userAddress", runningInfo.getUserInfo().getAddress());
            }
            objectNode.put("healthWarningLevel", runningInfo.getHealthWarningLevel());
            filterInfos.add(objectNode);
        }
        return filterInfos;
    }

    @RequestMapping(value = "/running/search", method = RequestMethod.GET)
    public List<ObjectNode> findRunningInfosOrderByHealthWarningLevel(@RequestParam(name = "page") int page) {
        return this.findRunningInfosOrderByHealthWarningLevel(page, 2);
    }

    @RequestMapping(value = "/running/delete", method = RequestMethod.POST)
    public void deleteRunningInfo(@RequestParam(name = "id") String runnningId) {
        this.runningInfoRepository.deleteByRunningId(runnningId);
    }

//    @RequestMapping(path = "insert", method = RequestMethod.POST)
//    public void insertRunningInfo(RunningInfo runningInfo) {
//        runningInfo.setHeartRate(this.randomHeartRate());
//        this.runningInfoRepository.save(runningInfo);
//    }

    @RequestMapping(value = "/running/update", method = RequestMethod.POST)
    public void insertRunningInfos(@RequestBody List<RunningInfo> runningInfos) {
        if (runningInfos == null || runningInfos.size() == 0) {
            return;
        }
        for (RunningInfo info : runningInfos) {
            info.setHeartRate(this.randomHeartRate());
        }
        this.runningInfoRepository.save(runningInfos);
    }

    private int randomHeartRate() {
        return (int) (Math.random() * (200 - 60) + 60);
    }
}
