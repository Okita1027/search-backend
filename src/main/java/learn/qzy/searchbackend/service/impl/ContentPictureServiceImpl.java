package learn.qzy.searchbackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rholder.retry.*;
import learn.qzy.searchbackend.exception.ThrowUtils;
import learn.qzy.searchbackend.model.entity.ContentPicture;
import learn.qzy.searchbackend.model.vo.ContentPictureVO;
import learn.qzy.searchbackend.service.ContentPictureService;
import learn.qzy.searchbackend.mapper.ContentPictureMapper;
import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static learn.qzy.searchbackend.constant.consist.RedisExpireTime.EXPIRE_TIME_ONE_HOUR;
import static learn.qzy.searchbackend.constant.enums.ErrorCodeEnum.RETRY_ERROR;

/**
 * @author qzy
 * @description 针对表【content_picture】的数据库操作Service实现
 * @createDate 2024-12-10 13:39:33
 */
@Service
public class ContentPictureServiceImpl extends ServiceImpl<ContentPictureMapper, ContentPicture>
        implements ContentPictureService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static Elements elements;

    @Override
    public boolean isExistsPicture(String title) {
        LambdaQueryWrapper<ContentPicture> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContentPicture::getTitle, title);
        long exists = this.count(wrapper);
        return exists > 0;
    }

    @Override
    public void deleteExistsPicture(String title) {
        LambdaQueryWrapper<ContentPicture> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContentPicture::getTitle, title);
        long exists = this.count(wrapper);
        if (exists > 0) {
            this.remove(wrapper);
        }
    }


    /**
     * 获取所有图片
     * 测试结果: MySQL->691ms  Redis->9ms
     * @param title 图片标题
     * @return 图片列表
     */
    @Override
    public Result<ContentPictureVO> getPictureList(String title) {
        // 1.从Redis查询是否存在该关键字的图片
        List<String> pictureStrList = redisTemplate.opsForList().range(title, 0, -1);
        // 返回图片地址列表
        if (pictureStrList != null && !pictureStrList.isEmpty()) {
            ArrayList<ContentPictureVO> pictureVOList = new ArrayList<>();
            for (String url : pictureStrList) {
                pictureVOList.add(new ContentPictureVO(url));
            }
            return ResultGenerator.genSuccessResult(pictureVOList);
        }
        // 2.从MySQL查询是否存在该关键字的图片
        LambdaQueryWrapper<ContentPicture> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContentPicture::getTitle, title);
        List<ContentPicture> pictureList = this.list(wrapper);
        // 写入Redis缓存、返回图片地址列表
        if (!pictureList.isEmpty()) {
            ArrayList<ContentPictureVO> pictureVOList = new ArrayList<>();
            for (ContentPicture picture : pictureList) {
                redisTemplate.opsForList().rightPush(title, picture.getPictureUrl());
                redisTemplate.expire(title, EXPIRE_TIME_ONE_HOUR, TimeUnit.SECONDS);
                pictureVOList.add(new ContentPictureVO(picture.getPictureUrl()));
            }
            return ResultGenerator.genSuccessResult(pictureVOList);
        }
        // 3.若Redis与MySQL均不存在对应的图片，则从unsplash抓取图片并存入MySQL、Redis
         Callable<Boolean> fetchDataTask = (() -> fetchData(title));
        // 定义重试策略
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfExceptionOfType(IOException.class)  // 抛出IOException则重试
                .withWaitStrategy(WaitStrategies.fixedWait(1, TimeUnit.SECONDS)) // 每次重试等待1秒
                .withStopStrategy(StopStrategies.stopAfterAttempt(3)) // 最多重试3次
                .build();
        try {
            retryer.call(fetchDataTask);
        } catch (ExecutionException | RetryException e) {
            ResultGenerator.genErrorResult(RETRY_ERROR.getCode(), RETRY_ERROR.getMessage());
        }

        ArrayList<ContentPictureVO> pictureVOList = new ArrayList<>();
        for (Element imageElement : elements) {
            String imageUrl = imageElement.attr("src");
            if (!imageUrl.startsWith("data:image") && !imageUrl.endsWith("h=32")) {
                ContentPicture picture = new ContentPicture();
                picture.setTitle(title);
                picture.setPictureUrl(imageUrl);
                this.save(picture);
                redisTemplate.opsForList().rightPush(title, imageUrl);
                pictureVOList.add(new ContentPictureVO(imageUrl));
            }
            redisTemplate.expire(title, EXPIRE_TIME_ONE_HOUR, TimeUnit.SECONDS);
        }
        // 4.返回数据
        return ResultGenerator.genSuccessResult(pictureVOList);
    }

    /**
     * 从unsplash获取图片数据
     * @param title 图片标题
     * @return Jsoup提取的Elements对象是否为空
     */
    private static Boolean fetchData(String title) throws IOException {
        String url = "https://unsplash.com/s/photos/" + title;
        Document doc = Jsoup.connect(url).get();
        // 选择所有具有 src 属性的 <img> 元素
        elements =  doc.select("img[src]");
        return !elements.isEmpty();
    }

    /*
    // 旧版本（不使用Redis）
    @Override
    public Result<ContentPicture> getPictureList(String title) {
        // 1.查询数据库是否存在图片
        LambdaQueryWrapper<ContentPicture> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContentPicture::getTitle, title);
        List<ContentPicture> list = this.list(wrapper);
        if (!list.isEmpty()) {
            return ResultGenerator.genSuccessResult(list);
        }
        // 2.抓取图片
        String url = "https://unsplash.com/s/photos/"+title;
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 选择所有具有 src 属性的 <img> 元素
        Elements imageElements = doc.select("img[src]");
        for (Element imageElement : imageElements) {
            String imageUrl = imageElement.attr("src");
            if (!imageUrl.startsWith("data:image") && !imageUrl.endsWith("h=32")) {
                ContentPicture picture = new ContentPicture();
                picture.setTitle(title);
                picture.setPictureUrl(imageUrl);
                this.save(picture);
            }
        }
        // 3.返回数据
        list = this.list(wrapper);
        return ResultGenerator.genSuccessResult(list);
    }*/

}
