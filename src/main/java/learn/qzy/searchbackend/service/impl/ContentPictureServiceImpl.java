package learn.qzy.searchbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import learn.qzy.searchbackend.model.entity.ContentPicture;
import learn.qzy.searchbackend.service.ContentPictureService;
import learn.qzy.searchbackend.mapper.ContentPictureMapper;
import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
* @author qzy
* @description 针对表【content_picture】的数据库操作Service实现
* @createDate 2024-12-10 13:39:33
*/
@Service
public class ContentPictureServiceImpl extends ServiceImpl<ContentPictureMapper, ContentPicture>
    implements ContentPictureService{


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
        Elements imageElements = doc.select("img[src]"); // 选择所有具有 src 属性的 <img> 元素
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
    }


}




