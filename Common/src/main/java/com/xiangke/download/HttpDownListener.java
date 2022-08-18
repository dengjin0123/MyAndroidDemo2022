package com.xiangke.download;

import java.io.File;

/**
 * @Date:2022/3/23 15:03
 * @Author:dengjin
 * @Name:
 */
public interface HttpDownListener {
    /**
     * @param file 下载成功后的文件
     */
    void onDownloadSuccess(File file);

    /**
     * @param progress 下载进度
     */
    void onDownloading(int progress);

    /**
     * @param e 下载异常信息
     */
    void onDownloadFailed(Exception e);

}
