package com.motian.crp.service;

import com.google.common.collect.Sets;
import com.motian.crp.constant.DataType;
import com.motian.crp.dao.data.ResourceLibraryData;
import com.motian.crp.dao.manager.ResourceManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */

@Slf4j
@Service
public class ResourceService {
    @Autowired
    private ResourceManager manager;

    @Transactional
    public void insert(String resourceName, String committer,
                       DataType.ResourceType resourceType, String resourceUri) {
        manager.save(new ResourceLibraryData()
                .setResourceName(resourceName)
                .setCommitter(committer)
                .setResourceType(resourceType)
                .setResourceUri(resourceUri)
        );
    }

    @Transactional
    public void download(long id) {

    }

    public void delete(long id) {
        manager.findById(id).ifPresent((o) -> manager.delete(o));
    }


    public List<ResourceLibraryData> listAll(String committer, String resourceId,
                                             String resourceName, int pageNumber, int pageSize) {
        Set<ResourceLibraryData> dataList = Sets.newHashSet();
        dataList.addAll(listByResourceId(committer, resourceId, pageNumber, pageSize));
        dataList.addAll(listByResourceName(committer, resourceName, pageNumber, pageSize));
        return new ArrayList<>(dataList);
    }

    private List<ResourceLibraryData> listByResourceId(
            String committer, String resourceId, int pageNumber, int pageSize) {
        log.info("listAll::resourceId={}", resourceId);
        int ccId = -1;
        try {
            ccId = Integer.parseInt(resourceId);
        } catch (NumberFormatException ignore) {
        }

        int finalCcId = ccId;

        return manager.getByCommitter(committer, PageRequest.of(pageNumber - 1, pageSize))
                .stream()
                .filter(o -> o.getId() == finalCcId)
                .collect(Collectors.toList());
    }

    private List<ResourceLibraryData> listByResourceName(
            String committer, String resourceName, int pageNumber, int pageSize) {

        return manager.getByCommitter(committer, PageRequest.of(pageNumber - 1, pageSize))
                .stream()
                .filter(o -> StringUtils.isBlank(resourceName) || o.getResourceName().equals(resourceName))
                .collect(Collectors.toList());
    }

}
