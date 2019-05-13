package com.motian.crp.service;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.motian.crp.dao.data.ClazzChapterData;
import com.motian.crp.dao.data.ResourceLibraryData;
import com.motian.crp.dao.manager.ResourceManager;
import com.motian.crp.utils.CrpServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.motian.crp.constant.CrpConst.ProjectStructure.DEFAULT_FILE_PATH;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */

@Slf4j
@Service
public class ResourceService {
    @Autowired
    private ResourceManager manager;
    @Autowired
    private ResourceUserInfoService service;
    @Autowired
    private ClazzChapterService clazzChapterService;
    @Autowired
    private StudyService studyService;

    @Transactional
    public ResourceLibraryData insert(String oldFileName, String newFileName,
                                      String filePath, String setResourceType,
                                      String committer) {
        return manager.save(new ResourceLibraryData()
                .setResourceName(oldFileName)
                .setResourceRealName(newFileName)
                .setCommitter(committer)
                .setResourceType(setResourceType)
                .setResourceUri(filePath)
        );
    }

    public Map<String, String> upload(HttpServletRequest request, HttpServletResponse response) {
        String filePath = StringUtils.EMPTY, oldFileName = StringUtils.EMPTY,
                newFileName = StringUtils.EMPTY;
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                //取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    //取得当前上传文件的文件名称
                    oldFileName = file.getOriginalFilename();
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if (oldFileName != null) {
                        if (!oldFileName.trim().equals("")) {
                            log.info("webUploadFile::fileName={}", oldFileName);
                            //重命名上传后的文件名  加入时间和随机字符
                            newFileName = CrpServiceUtils.getTime() +
                                    Long.toHexString(System.currentTimeMillis()) + "." + oldFileName;
                            //定义上传路径
                            try {
                                File fileDir = new File(DEFAULT_FILE_PATH);
                                if (!fileDir.exists()) { //如果不存在 则创建
                                    fileDir.mkdirs();
                                }
                                filePath = DEFAULT_FILE_PATH + newFileName;
                                //存文件
                                log.info("webUploadFile::newFileName={}", filePath);
                                File localFile = new File(filePath);
                                file.transferTo(localFile);
                            } catch (IllegalStateException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        insert(oldFileName, newFileName, filePath,
                CrpServiceUtils.getFileType(filePath), CrpServiceUtils.getUserId(request));
        return Maps.newHashMap();
    }

    @Transactional
    public void download(HttpServletRequest request, HttpServletResponse response,
                         long id, long clazzChapterId) throws IOException {
        ResourceLibraryData resourceLibraryData = manager.getOne(id);
        try {
            log.info("uri={},oldName={}", URLEncoder.encode(resourceLibraryData.getResourceUri(), "UTF-8"),
                    resourceLibraryData.getResourceName());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        File file = new File(resourceLibraryData.getResourceUri());

        if (file.exists()) {
            //设置响应头，控制浏览器下载该文件
            response.setHeader("content-disposition", "attachment;" +
                    "filename=" + URLEncoder.encode(resourceLibraryData.getResourceName(), "UTF-8"));
            //读取要下载的文件，保存到文件输入流
            FileInputStream in = new FileInputStream(file);
            //创建输出流
            OutputStream os = response.getOutputStream();
            //设置缓存区
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = in.read(bytes)) > 0) {
                os.write(bytes);
            }
        }

        ClazzChapterData clazzChapterData = clazzChapterService.getById(clazzChapterId);
        service.update(resourceLibraryData.getCommitter(), clazzChapterData.getClazzCourseId(),
                clazzChapterData.getId(), resourceLibraryData.getId());

        studyService.update(CrpServiceUtils.getUserId(request),
                resourceLibraryData.getCommitter(), clazzChapterData.getClazzCourseId(),
                clazzChapterData.getId(), resourceLibraryData.getId());
    }

    public void delete(long id) {
        Optional<ResourceLibraryData> resourceLibraryData = manager.findById(id);
        if (resourceLibraryData.isPresent()) {
            File file = new File(resourceLibraryData.get().getResourceUri());
            if (!file.exists())
                return;
            if (file.isFile()) {
                file.delete();
                manager.delete(resourceLibraryData.get());
            }
        }
    }

    public ResourceLibraryData get(long id) {
        return manager.getOne(id);
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

    public boolean update(String committer, long resourceId, String resourceName) {
        Optional<ResourceLibraryData> data = manager.getByCommitterAndResourceName(committer, resourceName);
        if (data.isPresent()) {
            return false;
        }
        ResourceLibraryData resourceLibraryData = manager.getOne(resourceId);
        resourceLibraryData.setResourceName(resourceName);
        manager.save(resourceLibraryData);
        return true;
    }

    public Map<Long, String> selectResource() {
        Map<Long, String> map = Maps.newHashMap();
        manager.findAll().forEach(o -> {
            map.put(o.getId(), o.getResourceName());
        });
        return map;
    }
}
