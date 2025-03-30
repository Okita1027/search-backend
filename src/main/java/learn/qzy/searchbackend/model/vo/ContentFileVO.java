package learn.qzy.searchbackend.model.vo;

/**
 * @author qzy
 * @time 2025年3月27日 21:08 星期四
 * @title 文件VO类
 */
public class ContentFileVO {
    private String fileName;
    private String filePath;

    public ContentFileVO() {
    }

    public ContentFileVO(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "ContentFileVO{" +
                "fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
