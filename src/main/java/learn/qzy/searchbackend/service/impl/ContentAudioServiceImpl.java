package learn.qzy.searchbackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import learn.qzy.searchbackend.model.entity.ContentAudio;
import learn.qzy.searchbackend.model.vo.ContentAudioVO;
import learn.qzy.searchbackend.service.ContentAudioService;
import learn.qzy.searchbackend.mapper.ContentAudioMapper;
import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Administrator
 * @description 针对表【content_file】的数据库操作Service实现
 * @createDate 2025-03-27 21:02:55
 */
@Service
public class ContentAudioServiceImpl extends ServiceImpl<ContentAudioMapper, ContentAudio>
        implements ContentAudioService {

    @Override
    public Result<ContentAudioVO> getAudioList(String fileName) {
        LambdaQueryWrapper<ContentAudio> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ContentAudio::getFileName, fileName)
                .eq(ContentAudio::getIsDeleted, 0);
        List<ContentAudio> audioList = list(wrapper);
        List<ContentAudioVO> audioVOList = BeanUtil.copyToList(audioList, ContentAudioVO.class);
        return ResultGenerator.genSuccessResult(audioVOList);
    }

    @Override
    public Result deleteAudio(String fileName) {
        LambdaQueryWrapper<ContentAudio> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContentAudio::getFileName, fileName);
        boolean remove = this.remove(wrapper);
        return remove ? ResultGenerator.genSuccessResult("删除成功") : ResultGenerator.genFailResult("删除失败");
    }

    @Override
    public Result<List<ContentAudio>> getAudioListAll() {
        return ResultGenerator.genSuccessResult(this.list());
    }


    public void uploadFile(MultipartFile file) throws Exception {
        // 添加文件保存逻辑，例如保存到本地磁盘或云存储
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            // 示例：保存到本地磁盘
            Path path = Paths.get("uploads/" + file.getOriginalFilename());
            Files.write(path, bytes);
        }
    }
}
