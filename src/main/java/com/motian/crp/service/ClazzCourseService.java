package com.motian.crp.service;

import com.motian.crp.dao.data.ClazzCourseData;
import com.motian.crp.dao.manager.ClazzCourseManager;
import com.motian.crp.utils.CrpServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
    public ClazzCourseService(ClazzCourseManager manager) {
        this.manager = manager;
    }

    @Transactional
    public void insert(String teacherId, String clazzCourseName) {
        manager.getByTeacherIdAndClazzCourseName(teacherId, clazzCourseName)
                .orElseGet(() ->
                        manager.save(new ClazzCourseData()
                                .setTeacherId(teacherId)
                                .setClazzCourseName(clazzCourseName)
                                .setClazzCourseId(CrpServiceUtils.getRandomNum(NUMBER_RANDOM_RANGE_BYTES)))
                );
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
        log.info("delete::id={}",id);
        manager.findById(id).ifPresent(manager::delete);
    }

    public ClazzCourseData getByClazzCourseId(long clazzCourseId) {
        return manager.getByClazzCourseId(clazzCourseId).orElse(null);
    }

    public ClazzCourseData getByTeacherIdAndClazzCourseName(String teacherId, String clazzCourseName) {
        return manager.getByTeacherIdAndClazzCourseName(teacherId, clazzCourseName).orElse(null);
    }

    public List<ClazzCourseData> listAllByTeacherId(String teacherId, int pageNumber, int pageSize) {
        return manager.findAll(PageRequest.of(pageNumber - 1, pageSize)).getContent()
                .stream().filter(o -> o.getTeacherId().equals(teacherId))
                .collect(Collectors.toList());
    }
}
