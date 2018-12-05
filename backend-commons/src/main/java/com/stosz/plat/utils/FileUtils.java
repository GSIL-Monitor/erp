package com.stosz.plat.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

public class FileUtils {

    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static String readFile(String filePath) {

        if (filePath == null) {
            throw new RuntimeException("param is null");
        }
        File file = new File(filePath);
        if (file == null || file.exists() == false) {
            throw new RuntimeException("file not exists : " + filePath);
        }
        StringBuilder sb = new StringBuilder();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                // KLog.info(tempString);
                sb.append(tempString);
                sb.append("\r\n");
            }
            reader.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {}
            }
        }
        return sb.toString();
    }

    public static void writeFile(String filePath, String text) {
        logger.info("Start write file : " + filePath);
        Date start = new Date();

        FileWriter fw = null;
        try {

            fw = new FileWriter(filePath);
            fw.write(text);
            fw.close();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                fw.close();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        Date end = new Date();
        logger.info("End   write file : cost " + (end.getTime() - start.getTime()) / 1000 + "s");
    }

    public static void copy(File f1, File f2) throws Exception {
        int length = 2097152;
        try(
                FileInputStream in = new FileInputStream(f1);
                FileOutputStream out = new FileOutputStream(f2)
        ) {

            FileChannel inC = in.getChannel();
            FileChannel outC = out.getChannel();
            ByteBuffer b = null;
            while (true) {
                if (inC.position() == inC.size()) {
                    in.close();
                    out.close();
                    inC.close();
                    outC.close();
                    return;
                }
                if ((inC.size() - inC.position()) < length) {
                    length = (int) (inC.size() - inC.position());
                } else length = 2097152;
                b = ByteBuffer.allocateDirect(length);
                inC.read(b);
                b.flip();
                outC.write(b);
                outC.force(false);
            }


        }


    }
}
