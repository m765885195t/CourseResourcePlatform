package com.motian.crp.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.motian.crp.dao.data.ClazzChapterResourcesData;
import com.motian.crp.dao.data.ClazzCourseData;
import com.motian.crp.dao.data.StudentClazzCourseInfoData;
import com.motian.crp.dao.manager.ClazzChapterManager;
import com.motian.crp.dao.manager.ClazzCourseManager;
import com.motian.crp.dao.manager.StudentClazzCourseInfoManager;
import com.motian.crp.dao.manager.UserManager;
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

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@Slf4j
@Service
public class StudentClazzCourseInfoService {
    private final ClazzCourseManager clazzCourseManager;
    private final StudentClazzCourseInfoManager studentClazzCourseInfoManager;
    private final UserManager userManager;
    @Autowired
    private ClazzChapterManager clazzChapterManager;
    @Autowired
    private ClazzChapterResourcesService clazzChapterResourcesService;
    @Autowired
    private ResourceService resourceService;

    @Autowired
    public StudentClazzCourseInfoService(
            StudentClazzCourseInfoManager studentClazzCourseInfoManager,
            ClazzCourseManager clazzCourseManager,
            UserManager userManager) {
        this.clazzCourseManager = clazzCourseManager;
        this.studentClazzCourseInfoManager = studentClazzCourseInfoManager;
        this.userManager = userManager;
    }

    @Transactional
    public int insert(String studentId, long clazzCourseId) throws Exception {
        Optional<ClazzCourseData> clazzCourseData = clazzCourseManager
                .getByClazzCourseId(clazzCourseId);
        if (!clazzCourseData.isPresent()) {
            throw new Exception("The ClazzCourseData does not exist. clazzCourseId=" + clazzCourseId);
        }

        Optional<StudentClazzCourseInfoData> studentClazzCourseInfoData =
                studentClazzCourseInfoManager.getByStudentIdAndClazzCourseId(studentId, clazzCourseId);
        if (studentClazzCourseInfoData.isPresent()) {
            return 1;
        }

        int gealleryful = studentClazzCourseInfoManager.getByClazzCourseId(clazzCourseId).size();
        if (clazzCourseData.get().getGalleryful() < gealleryful) {
            return 0;
        }

        studentClazzCourseInfoManager.save(new StudentClazzCourseInfoData()
                .setStudentId(studentId)
                .setClazzCourseId(clazzCourseId)
                .setSequence(gealleryful + 1)
                .setTeacherName(userManager.getByAccountId(clazzCourseData.get().getTeacherId()).get().getNickname())
                .setTeacherId(clazzCourseData.get().getTeacherId())
                .setClazzCourseName(clazzCourseData.get().getClazzCourseName())
        );
        return 2;
    }


    public StudentClazzCourseInfoData update(long id) throws Exception {
        Optional<StudentClazzCourseInfoData> data = studentClazzCourseInfoManager.findById(id);
        if (!data.isPresent()) {
            throw new Exception("The StudentClazzCourseInfoData does not exist. id=" + id);
        }

        return studentClazzCourseInfoManager.save(data.get());
    }

    public void delete(long id) {
        studentClazzCourseInfoManager.findById(id).ifPresent(studentClazzCourseInfoManager::delete);
    }

    public List<Map<String, Object>> get(long id) {
        StudentClazzCourseInfoData studentClazzCourseInfoData = studentClazzCourseInfoManager.getOne(id);
        List<Map<String, Object>> data = Lists.newArrayList();
        clazzChapterManager.getByClazzCourseId(studentClazzCourseInfoData.getClazzCourseId())
                .forEach(o -> {
                    Map<String, Object> model = Maps.newHashMap();
                    model.put("id", String.valueOf(o.getId()));
                    Optional<ClazzChapterResourcesData> clazzChapterResourcesData =
                            clazzChapterResourcesService.getByClazzChapterIdAndVideoIs(
                                    o.getId(), false);
                    if (clazzChapterResourcesData.isPresent()) {
                        model.put("domResourceExist", Boolean.TRUE);
                        model.put("domResourceId", resourceService.get(clazzChapterResourcesData.get().getResourceLibraryId()).getId());
                    } else {
                        model.put("domResourceExist", Boolean.FALSE);
                    }
                    clazzChapterResourcesData = clazzChapterResourcesService
                            .getByClazzChapterIdAndVideoIs(o.getId(), true);
                    if (clazzChapterResourcesData.isPresent()) {
                        model.put("videoResourceExist", Boolean.TRUE);
                        model.put("videoResourceId", resourceService.get(clazzChapterResourcesData.get().getResourceLibraryId()).getId());
                    } else {
                        model.put("videoResourceExist", Boolean.FALSE);
                    }

                    model.put("clazzCourseName", clazzCourseManager.getByClazzCourseId(o.getClazzCourseId()).get().getClazzCourseName());
                    model.put("clazzChapterName", o.getClazzChapterName());
                    model.put("clazzChapterId", o.getId());
                    model.put("joinNumber", String.valueOf(studentClazzCourseInfoManager.getByClazzCourseId(o.getClazzCourseId()).size()));
                    model.put("joinTime", String.valueOf(o.getAddTime()));
                    data.add(model);
                });
        return data;
    }


    public List<StudentClazzCourseInfoData> listAllByTeacherId(String userId, String clazzCourseName, String clazzCourseId, int pageNumber, int pageSize) {
        Set<StudentClazzCourseInfoData> dataList = Sets.newHashSet();
        dataList.addAll(listByTeacherIdAndClazzCourseId(userId, clazzCourseId, pageNumber, pageSize));
        dataList.addAll(listByTeacherIdAndClazzCourseName(userId, clazzCourseName, pageNumber, pageSize));
        log.info("dataList::dataList={}", dataList);
        return new ArrayList<>(dataList);
    }

    private List<StudentClazzCourseInfoData> listByTeacherIdAndClazzCourseId(
            String userId, String clazzCourseId, int pageNumber, int pageSize) {
        log.info("listAll::clazzCourseId={}", clazzCourseId);
        int ccId = -1;
        try {
            ccId = Integer.parseInt(clazzCourseId);
        } catch (NumberFormatException ignore) {
        }

        int finalCcId = ccId;
        return studentClazzCourseInfoManager.getByTeacherId(userId, PageRequest.of(pageNumber - 1, pageSize))
                .stream()
                .filter(o -> finalCcId < 0 || o.getClazzCourseId() == finalCcId)
                .collect(Collectors.toList());
    }

    private List<StudentClazzCourseInfoData> listByTeacherIdAndClazzCourseName(
            String userId, String clazzCourseName, int pageNumber, int pageSize) {
        return studentClazzCourseInfoManager.getByTeacherId(userId, PageRequest.of(pageNumber - 1, pageSize))
                .stream()
                .filter(o -> StringUtils.isBlank(clazzCourseName) || o.getClazzCourseName().equals(clazzCourseName))
                .collect(Collectors.toList());
    }

    public List<StudentClazzCourseInfoData> listAll(String userId, String clazzCourseId,
                                                    String clazzCourseName, int pageNumber, int pageSize) {

        Set<StudentClazzCourseInfoData> dataList = Sets.newHashSet();
        dataList.addAll(listByClazzCourseId(userId, clazzCourseId, pageNumber, pageSize));
        dataList.addAll(listByClazzCourseName(userId, clazzCourseName, pageNumber, pageSize));
        return new ArrayList<>(dataList);
    }

    private List<StudentClazzCourseInfoData> listByClazzCourseId(
            String userId, String clazzCourseId, int pageNumber, int pageSize) {
        log.info("listAll::clazzCourseId={}", clazzCourseId);
        int ccId = -1;
        try {
            ccId = Integer.parseInt(clazzCourseId);
        } catch (NumberFormatException ignore) {
        }

        int finalCcId = ccId;
        return studentClazzCourseInfoManager.getByStudentId(userId, PageRequest.of(pageNumber - 1, pageSize))
                .stream()
                .filter(o -> finalCcId < 0 || o.getClazzCourseId() == finalCcId)
                .collect(Collectors.toList());
    }

    private List<StudentClazzCourseInfoData> listByClazzCourseName(
            String userId, String clazzCourseName, int pageNumber, int pageSize) {


        return studentClazzCourseInfoManager.getByStudentId(userId, PageRequest.of(pageNumber - 1, pageSize))
                .stream()
                .filter(o -> StringUtils.isBlank(clazzCourseName) || o.getClazzCourseName().equals(clazzCourseName))
                .collect(Collectors.toList());
    }

    void deleteByClazzCourseId(long clazzCourseId) {
        studentClazzCourseInfoManager.deleteByClazzCourseId(clazzCourseId);
    }

    List<StudentClazzCourseInfoData> findAll() {
        return studentClazzCourseInfoManager.findAll();
    }
}
