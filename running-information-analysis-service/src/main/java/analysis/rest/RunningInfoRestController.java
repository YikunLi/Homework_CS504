package analysis.rest;

import analysis.domain.RunningInfo;
import analysis.domain.RunningInfoRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lykly on 2017/6/11.
 */
@RestController
@RequestMapping(path = "runningInfos")
public class RunningInfoRestController {

    private RunningInfoRepository runningInfoRepository;

    @Autowired
    public RunningInfoRestController(RunningInfoRepository runningInfoRepository) {
        this.runningInfoRepository = runningInfoRepository;
    }

    public List<RunningInfo> findRunningInfosOrderByHeartRate(int page, int size) {
        Sort sort = new Sort(Sort.Direction.DESC, "heartRate");
        Pageable pageable = new PageRequest(page, size, sort);
        Page<RunningInfo> result = this.runningInfoRepository.findAll(pageable);
        return result.getContent();
    }

    @RequestMapping(path = "search")
    public List<JSONObject> findRunningInfosOrderByHealthWarningLevel(int page, int size) {
        List<RunningInfo> runningInfos = this.findRunningInfosOrderByHeartRate(page, size);
        List<JSONObject> filterInfos = new ArrayList<JSONObject>();
        if (runningInfos == null || runningInfos.size() == 0) {
            return filterInfos;
        }
        for (RunningInfo runningInfo : runningInfos) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("runningId", runningInfo.getRunningId());
                jsonObject.put("totalRunningTime", runningInfo.getTotalRunningTime());
                jsonObject.put("heartRate", runningInfo.getHeartRate());
                if (runningInfo.getUserInfo() != null) {
                    jsonObject.put("userId", runningInfo.getUserInfo().getId());
                    jsonObject.put("userName", runningInfo.getUserInfo().getUsername());
                    jsonObject.put("userAddress", runningInfo.getUserInfo().getAddress());
                }
                jsonObject.put("healthWarningLevel", runningInfo.getHealthWarningLevel());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            filterInfos.add(jsonObject);
        }
        return filterInfos;
    }

    @RequestMapping(path = "search", method = RequestMethod.GET)
    public List<JSONObject> findRunningInfosOrderByHealthWarningLevel(int page) {
        return this.findRunningInfosOrderByHealthWarningLevel(page, 2);
    }

    @RequestMapping(path = "delete", method = RequestMethod.POST)
    public void deleteRunningInfo(String runnningId) {
        this.runningInfoRepository.deleteByRunningId(runnningId);
    }

//    @RequestMapping(path = "insert", method = RequestMethod.POST)
//    public void insertRunningInfo(RunningInfo runningInfo) {
//        runningInfo.setHeartRate(this.randomHeartRate());
//        this.runningInfoRepository.save(runningInfo);
//    }

    @RequestMapping(path = "insert", method = RequestMethod.POST)
    public void insertRunningInfos(List<RunningInfo> runningInfos) {
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
