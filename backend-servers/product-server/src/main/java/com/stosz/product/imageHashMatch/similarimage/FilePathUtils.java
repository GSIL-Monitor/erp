package com.stosz.product.imageHashMatch.similarimage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取到指定文件夹下的所有文件名
 *
 * @author Xiongchenyang
 * 2017/8/11
 */
public class FilePathUtils {

    /**
     * 获取当前文件夹下的所有路径
     *
     * @param path
     * @return
     */
    public static List<String> getFileName(String path) {
        List<String> fileNameList = new ArrayList<String>();
        File f = new File(path);
        if (!f.exists()) {
            //该文件夹下不存在文件
            return null;
        }

        File fa[] = f.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            if (!fs.isDirectory()) {
                fileNameList.add(fs.getPath());
            }
        }
        return fileNameList;
    }

    /**
     * 获取一个文件夹下所有子文件夹的文件
     *
     * @param path 文件夹位置
     * @param list 保存文件路径的list
     * @return 文件路径的list
     */
    public static List<String> getAllFiles(String path, List<String> list) {
        File f = new File(path);
        getAllFiles(f, 0, list);
        return list;
    }


    public static void getAllFiles(File dir, int level, List<String> list) {
        level++;
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                //这里面用了递归的算法
                getAllFiles(files[i], level, list);
            } else {
                list.add(files[i].getPath());
            }
        }
    }


    public static void main(String[] args) {
        String path = "D:\\workspace\\erp-productcenter\\erp-productcenter\\erp-product-server\\userfiles\\1\\images";

    }

}
