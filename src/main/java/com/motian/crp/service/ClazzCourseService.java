package com.motian.crp.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.motian.crp.constant.CrpConst;
import com.motian.crp.dao.data.ClazzChapterData;
import com.motian.crp.dao.data.ClazzCourseData;
import com.motian.crp.dao.data.UserData;
import com.motian.crp.dao.manager.ClazzCourseManager;
import com.motian.crp.utils.CrpServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.motian.crp.constant.CrpConst.NUMBER_RANDOM_RANGE_BYTES;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@Slf4j
@Service
public class ClazzCourseService {
    private final ClazzCourseManager manager;

    @Autowired
    private ClazzChapterService clazzChapterService;
    @Autowired
    private StudentClazzCourseInfoService studentClazzCourseInfoService;
    @Autowired
    private QuestionBankService questionBankService;
    @Autowired
    private UserService userService;

    @Autowired
    public ClazzCourseService(ClazzCourseManager manager) {
        this.manager = manager;
    }


    @Transactional
    public boolean insert(String teacherId, String clazzCourseName, int galleryful) {
        if (manager.getByTeacherIdAndClazzCourseName(teacherId, clazzCourseName).isPresent()) {
            log.info("ClazzCourseService::isPresent");
            return false;
        }
        manager.save(new ClazzCourseData()
                .setTeacherId(teacherId)
                .setClazzCourseName(clazzCourseName)
                .setClazzCourseId(CrpServiceUtils.getRandomNum(NUMBER_RANDOM_RANGE_BYTES))
                .setGalleryful(galleryful)
        );
        return true;
    }


    public ClazzCourseData update(long id, String clazzCourseName) throws Exception {
        Optional<ClazzCourseData> data = manager.findById(id);
        if (!data.isPresent()) {
            throw new Exception("The ClazzCourseData does not exist. id=" + id);
        }
        if (StringUtils.isNotBlank(clazzCourseName)) {
            data.get().setClazzCourseName(clazzCourseName);
        }

        return manager.save(data.get());
    }

    public void delete(long id) {
        // 删除班课
        manager.findById(id).ifPresent(manager::delete);
        // 删除章节
        clazzChapterService.deleteByClazzCourseId(id);
        // 删除学生信息
        studentClazzCourseInfoService.deleteByClazzCourseId(id);
        // 删除挂载的问题
        // 删除挂载的资源
    }

    public ClazzCourseData getByClazzCourseId(long clazzCourseId) {
        return manager.getByClazzCourseId(clazzCourseId).orElse(null);
    }

    public ClazzCourseData getByTeacherIdAndClazzCourseName(String teacherId, String clazzCourseName) {
        return manager.getByTeacherIdAndClazzCourseName(teacherId, clazzCourseName).orElse(null);
    }

    public List<ClazzCourseData> listAllByTeacherId(
            String teacherId, long questionBankId, int pageNumber, int pageSize) {

        return manager.findAll(PageRequest.of(pageNumber - 1, pageSize)).getContent()
                .stream()
                .filter(o -> questionBankId < 0 || o.getId() == questionBankId)
                .filter(o -> o.getTeacherId().equals(teacherId))
                .collect(Collectors.toList());
    }

    public Map<Long, String> selectClazzCourse(String teacherId) {
        Map<Long, String> map = Maps.newHashMap();
        manager.getByTeacherId(teacherId).forEach(o -> {
            map.put(o.getClazzCourseId(), o.getClazzCourseName());
        });

        return map;
    }

    public List<Map<String, Object>> listAll(String clazzCourseId, String clazzCourseName,
                                             int pageNumber, int pageSize) {
        Set<Map<String, Object>> dataList = Sets.newHashSet();
        dataList.addAll(listByClazzCourseId(clazzCourseId, pageNumber, pageSize));
        dataList.addAll(listByClazzCourseName(clazzCourseName, pageNumber, pageSize));
        return new ArrayList<>(dataList);
    }

    private List<Map<String, Object>> listByClazzCourseId(String clazzCourseId, int pageNumber, int pageSize) {
        log.info("listAll::clazzCourseId={}", clazzCourseId);
        int ccId = -1;
        try {
            ccId = Integer.parseInt(clazzCourseId);
        } catch (NumberFormatException ignore) {
        }

        List<ClazzChapterData> dataList = Lists.newArrayList();
        int finalCcId = ccId;

        List<Map<String, Object>> data = Lists.newArrayList();
        manager.findAll(PageRequest.of(pageNumber - 1, pageSize)).getContent()
                .stream()
                .filter(o -> finalCcId < 0 || o.getClazzCourseId() == finalCcId)
                .forEach(o -> data.add(getMap(o)));
        return data;
    }

    private List<Map<String, Object>> listByClazzCourseName(String clazzCourseName, int pageNumber, int pageSize) {

        List<Map<String, Object>> data = Lists.newArrayList();
        manager.findAll(PageRequest.of(pageNumber - 1, pageSize)).getContent()
                .stream()
                .filter(o -> StringUtils.isBlank(clazzCourseName) || o.getClazzCourseName().equals(clazzCourseName))
                .forEach(o -> data.add(getMap(o)));
        return data;
    }

    private Map<String, Object> getMap(ClazzCourseData data) {
        Map<String, Object> tem = Maps.newHashMap();
        tem.put("id", data.getId());
        tem.put("clazzCourseId", data.getClazzCourseId());
        tem.put("clazzCourseName", data.getClazzCourseName());
        tem.put("registrationNumber", studentClazzCourseInfoService.findAll().size());
        tem.put("galleryful", data.getGalleryful());
        UserData userData = userService.getByAccountId(data.getTeacherId());
        tem.put("nickname", userData.getNickname());
        tem.put("addTime", data.getAddTime());
        tem.put("updateTime", data.getUpdateTime());
        return tem;
    }

    public boolean save(long courseId, String[] text) {
        ClazzCourseData clazzCourseData = manager.getOne(courseId);
        clazzCourseData.setAnswer(StringUtils.join(text, CrpConst.SEPARATOR));
        manager.save(clazzCourseData);
        return true;
    }

    public boolean updateQuestion(long id, String results) {
        ClazzCourseData clazzCourseData = manager.getOne(id);
        if (clazzCourseData.getQuestion().isEmpty()) {
            clazzCourseData.setQuestion(results);
            clazzCourseData.setAnswer("  ");
        } else {
            clazzCourseData.setQuestion(clazzCourseData.getQuestion() + CrpConst.SEPARATOR + results);
            clazzCourseData.setAnswer(clazzCourseData.getAnswer() +CrpConst.SEPARATOR + "  ");
        }
        manager.save(clazzCourseData);
        return true;
    }
}
