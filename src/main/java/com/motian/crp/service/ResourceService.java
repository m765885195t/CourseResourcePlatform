package com.motian.crp.service;

import com.motian.crp.dao.data.ResourceLibraryData;
import com.motian.crp.dao.manager.ResourceManager;
import com.motian.crp.utils.CrpServiceUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */

@Service
public class ResourceService {
    @Autowired
    private ResourceManager manager;

    @Transactional
    public void insert(String teacherId, String studentId, long clazzCourseId,
                       long learningTime) {

    }


    public ResourceLibraryData update(long id, String teacherId, String studentId, long clazzCourseId,
                                      long learningTime)
            throws Exception {
        Optional<ResourceLibraryData> data = manager.findById(id);
        if (!data.isPresent()) {
            throw new Exception("The user does not exist. id=" + id);
        }


        return manager.save(data.get());
    }

    public void delete(long id) {
        manager.findById(id).ifPresent((o) -> manager.delete(o));
    }



    public List<ResourceLibraryData> listAll(int pageNumber, int pageSize) {
        return manager.findAll(PageRequest.of(pageNumber - 1, pageSize)).getContent();
    }
}
