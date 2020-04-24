package com.dist.base.utils;

import cn.hutool.core.io.FileTypeUtil;
import org.apache.commons.io.FileUtils;
import org.apache.http.util.TextUtils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.*;

/**
 * java.io.file工具
 */
public class FileUtil {

    /**
     * 获取文件后缀
     *
     * @param file
     * @return
     */
    public static String getFileSuffix(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

    /**
     * 获取文件后缀
     *
     * @param fileName
     * @return 后缀不带.
     */
    public static String getFileSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

    /**
     * 删除文件后缀
     *
     * @param fileName
     * @return
     */
    public static String delFileNameSuffix(String fileName) {
        if (fileName == null) {
            return null;
        }
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    /**
     * 获取文件名【包括后缀】
     *
     * @param localFilePath 文件路径
     * @return
     */
    public static String getFileName(String localFilePath) {
        return new File(localFilePath).getName();
    }


    /**
     * 获取文件类型
     *
     * @param file
     * @return jpg、zip这样的文件类型
     */
    public static String getType(File file) {
        return FileTypeUtil.getType(file);
    }

    /**
     * 获取文件类型
     *
     * @param filePath 文件全路径
     * @return jpg、zip这样的文件类型
     */
    public static String getType(String filePath) {
        return getType(new File(filePath));
    }

    /**
     * 创建文件，如果目录不存在则创建目录
     *
     * @param localTempPath
     * @return
     */
    public static File touch(String localTempPath) {
        return cn.hutool.core.io.FileUtil.touch(localTempPath);
    }

    /**
     * 将二进制 转出 java.io.file
     *
     * @param srcFile
     * @param path
     */
    public static final boolean byte2File(byte[] srcFile, String path) {
        FileOutputStream out = null;
        String targetFolder = path.substring(0, path.lastIndexOf(File.separator));
        try {
            File folder = new File(targetFolder);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            out = new FileOutputStream(path);
            out.write(srcFile);
            out.flush();
        } catch (Exception e) {
            return false;
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * file 转 byte[]
     *
     * @param filePath
     * @return
     */
    public static byte[] File2byte(String filePath) {
        byte[] buffer = null;
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;

        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fis) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (null != bos) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buffer;
    }

    /**
     * InputStream 转 byte[]
     *
     * @param inStream
     * @return
     */
    public static byte[] readStream(InputStream inStream) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        try {
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outStream.toByteArray();
    }


    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static boolean copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            File newDir = new File(newPath.replace('\\', '/').substring(0,
                    newPath.replace('\\', '/').lastIndexOf("/")));
            if (!newDir.exists()) {
                newDir.mkdirs();
            }
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.flush();
                fs.close();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 根据文件路径检测文件的合法性
     *
     * @param filePath
     * @return
     */
    public static boolean checkFile(String filePath) {

        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                fis = new FileInputStream(file);
                if (fis.available() > 0) {
                    return true;
                } else {
                    file.delete();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 修改文件的后缀
     *
     * @param fileName      文件名
     * @param replaceSuffix 要修改的后缀名，不带.
     * @return
     */
    public static String replaceFileSuffix(String fileName, String replaceSuffix) {
        if (fileName.contains(".")) {
            int i = fileName.lastIndexOf(".");
            String substring = fileName.substring(0, i);
            fileName = substring + "." + replaceSuffix;
        }
        return fileName;
    }

    /**
     * 删除文件
     *
     * @param filePath
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isDirectory()) {
            deleteDir(filePath);
            return;
        }
        if (file.exists()) {
            file.delete();
        }
    }


    /**
     * 创建目录
     *
     * @param dirPath
     */
    public static void createDirs(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dirPath 将要删除的文件目录
     */
    public static void deleteDir(String dirPath) {
        try {
            FileUtils.deleteDirectory(new File(dirPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* 合并文件 */
    public static boolean mergeFiles(List<String> filePaths, String resultPath) {
        if (filePaths == null || filePaths.size() < 1 || TextUtils.isEmpty(resultPath)) {
            return false;
        }
        if (filePaths.size() == 1) {
            return new File(filePaths.get(0)).renameTo(new File(resultPath));
        }

        File[] files = new File[filePaths.size()];
        for (int i = 0; i < filePaths.size(); i++) {
            files[i] = new File(filePaths.get(0));
            if (TextUtils.isEmpty(filePaths.get(i)) || !files[i].exists() || !files[i].isFile()) {
                return false;
            }
        }

        File resultFile = new File(resultPath);

        try {
            FileChannel resultFileChannel = new FileOutputStream(resultFile, true).getChannel();
            for (int i = 0; i < filePaths.size(); i++) {
                FileChannel blk = new FileInputStream(files[i]).getChannel();
                resultFileChannel.transferFrom(blk, resultFileChannel.size(), blk.size());
                blk.close();
            }
            resultFileChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        for (int i = 0; i < filePaths.size(); i++) {
            files[i].delete();
        }

        return true;
    }

    public static boolean mergeFilesV2(List<String> filePaths, String resultPath) {

        Object[] fpaths = filePaths.toArray();

        if (fpaths == null || fpaths.length < 1 || TextUtils.isEmpty(resultPath)) {
            return false;
        }
        if (fpaths.length == 1) {
            return new File((String) fpaths[0]).renameTo(new File(resultPath));
        }

        File[] files = new File[fpaths.length];
        for (int i = 0; i < fpaths.length; i++) {
            files[i] = new File((String) fpaths[i]);
            if (TextUtils.isEmpty((CharSequence) fpaths[i]) || !files[i].exists() || !files[i].isFile()) {
                return false;
            }
        }

        File resultFile = new File(resultPath);

        try {
            int bufSize = 1024;
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(resultFile));
            byte[] buffer = new byte[bufSize];

            for (int i = 0; i < fpaths.length; i++) {
                BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(files[i]));
                int readcount;
                while ((readcount = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, readcount);
                }
                inputStream.close();
            }
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        for (int i = 0; i < fpaths.length; i++) {
            files[i].delete();
        }

        return true;
    }

    private FileUtil() {
    }

    /**
     * 根据文件路径检测文件的合法性
     *
     * @param filePath
     * @return
     */
    public static boolean legalFile(String filePath) {

        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                fis = new FileInputStream(file);
                if (fis.available() > 0) {
                    return true;
                } else {
                    file.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    /**
     * 根据文件夹路径检测合法性
     * @param filePath
     * @return
     */
    public static boolean legalFileDir(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists() && file.isDirectory() ) {
                return true;
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取当前文件夹下的文件信息
     */
    public static File[] ls(String path) {
        return cn.hutool.core.io.FileUtil.ls(path);
    }

    /**
     * 在指定目录下查找指定文件名对应的路径
     */
    public static String findFilePathByFilename(String rootPath, String fileName) {

        File[] children = FileUtil.ls(rootPath);
        Queue<File> queue = new LinkedList<>();
        if (children != null) {
            queue.addAll(Arrays.asList(children));
        }
        while (queue.size() != 0) {
            File file = queue.poll();
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    queue.addAll(Arrays.asList(files));
                }
            } else {
                if (Objects.equals(file.getName(), fileName)) {
                    return file.getAbsolutePath();
                }
            }
        }
        return null;
    }


    public static String findFilePathByFilename2(String rootPath, String fileName) {
        File[] children = FileUtil.ls(rootPath);
        if (children != null) {
            for (File child : children) {
                if (child.isDirectory()) {
                    String path = findFilePathByFilename2(child.getAbsolutePath(), fileName);
                    if (path != null && path.contains(fileName)) {
                        return path;
                    }
                } else {
                    if (Objects.equals(child.getName(), fileName)) {
                        return child.getAbsolutePath();
                    }
                }
            }
        }
        return null;

    }


    /**
     * 获取有效路径
     * @param fileDirPath
     * @return
     */
    public static String getEffectiveDirPath(String fileDirPath){
        File dir = new File(fileDirPath);
        if (!dir.exists() || !dir.isDirectory()) {
            return null;
        }
        File[] nodes = dir.listFiles();
        if (nodes != null && nodes.length == 1) {
            File node = nodes[0];
            if (node.getName().equals(dir.getName())) {
                return getEffectiveDirPath(node.getPath());
            }
        }
        return fileDirPath;
    }
}
