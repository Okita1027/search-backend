package learn.qzy.searchbackend.es;

import learn.qzy.searchbackend.esdao.ContentArticleRepository;
import learn.qzy.searchbackend.model.entity.ContentArticle;
import learn.qzy.searchbackend.model.es.ESContentArticle;
import learn.qzy.searchbackend.service.ContentArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SyncTest {
    @Autowired
    private ContentArticleService articleService;

    @Autowired
    private ContentArticleRepository articleRepository;

    @Test
    void deleteTest() {
        articleRepository.deleteById("o726v5YB9wiFtZ0r0oRn");
    }

    /**
     * 通过标题匹配，同步MySQL和ES数据的ID
     */
    @Test
    void syncESDataIdsWithMySQLIds() {
        System.out.println("开始通过标题同步MySQL和ES的文档ID...");

        // 1. 获取MySQL中的所有文章数据
        List<ContentArticle> mysqlArticles = articleService.list();
        System.out.println("从MySQL获取到 " + mysqlArticles.size() + " 条文章记录");

        int successCount = 0;
        int failCount = 0;
        int skipCount = 0;

        for (ContentArticle mysqlArticle : mysqlArticles) {
            try {
                String title = mysqlArticle.getTitle();

                // 跳过标题为空的记录
                if (title == null || title.trim().isEmpty()) {
                    System.out.println("跳过ID为 " + mysqlArticle.getId() + " 的记录，因为标题为空");
                    skipCount++;
                    continue;
                }

                // 2. 通过标题在ES中查找匹配的记录
                ESContentArticle matchedESArticle = articleRepository.findByTitle(title);

                if (matchedESArticle != null) {
                    // 如果ES文档ID与MySQL中的ID已经相同，则跳过
                    if (mysqlArticle.getId().equals(matchedESArticle.getId())) {
                        System.out.println("跳过文章 '" + title + "'，ID已经同步");
                        skipCount++;
                        continue;
                    }

                    // 3. 找到匹配记录，更新ES中的文档ID为MySQL中的ID
                    System.out.println("找到标题匹配：'" + title + "', MySQL ID=" + mysqlArticle.getId()
                            + ", ES ID=" + matchedESArticle.getId());

                    // 删除旧的ES文档
                    articleRepository.deleteById(String.valueOf(matchedESArticle.getId()));

                    // 创建新的ES文档，使用MySQL的ID
                    ESContentArticle newESArticle = new ESContentArticle();
                    newESArticle.setId(mysqlArticle.getId());
                    newESArticle.setTitle(mysqlArticle.getTitle());
                    newESArticle.setContent(mysqlArticle.getContent());
                    articleRepository.save(newESArticle);

                    System.out.println("已将ES中标题为 '" + title + "' 的文档ID从 " + matchedESArticle.getId()
                            + " 更新为 " + mysqlArticle.getId());
                    successCount++;
                } else {
                    System.out.println("未找到标题匹配的ES记录：'" + title + "', MySQL ID=" + mysqlArticle.getId());

                    // 4. 如果没有找到匹配记录，创建新记录
                    ESContentArticle newESArticle = new ESContentArticle();
                    newESArticle.setId(mysqlArticle.getId());
                    newESArticle.setTitle(mysqlArticle.getTitle());
                    newESArticle.setContent(mysqlArticle.getContent());
                    articleRepository.save(newESArticle);

                    System.out.println("已为MySQL记录创建新的ES文档，标题='" + title + "', ID=" + mysqlArticle.getId());
                    successCount++;
                }
            } catch (Exception e) {
                System.err.println("处理文章时出错：ID=" + mysqlArticle.getId() + ", 错误：" + e.getMessage());
                e.printStackTrace();
                failCount++;
            }
        }

        System.out.println("同步完成：成功 " + successCount + " 条，跳过 " + skipCount + " 条，失败 " + failCount + " 条");
    }
}