package hpscore.tools;
/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/19
 * Time: 22:39
 */

import hpscore.domain.FileInfo;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 *@ClassName: FileUtil
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/6/19 22:39
 **/
public class FileUtil {

    /**
     * @Author
     * @Description 测试文件读取
     * @Date 19:58 2018/9/5
     * @Param [args]
     * @return void
     **/
    public static void main(String[] args) throws IOException {

        String encoding = System.getProperty("file.encoding");//获取编码
        System.out.println(encoding);

        List<FileInfo> fileInfoList = readFileInfo(".");//调用下面方法，读取当前项目中的.xls文件
        for (FileInfo fileInfo: fileInfoList){
            System.out.println("name = "+fileInfo.getName());
            System.out.println("size = "+fileInfo.getSize());
            System.out.println("updatetime = "+fileInfo.getUpdateTime());
        }
    }
    // 判断文件是否存在
    public static boolean FileExists(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println("file exists");
            return true;
        } else {
            System.out.println("file not exists");
            return false;
        }
    }

    public static List<String> readfiles(String filepath) {
        //获取系统编码
        String encoding = System.getProperty("file.encoding");
        System.out.println("encoding = "+encoding);
        List<String> fileList = new ArrayList<>();
        try {
            File file = new File(new String(filepath.getBytes(encoding),"UTF-8"));
            if (file.isDirectory()) {
                System.out.println("文件夹");
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {

                        String pattern = ".*\\.xls";
                        String fileName =readfile.getName();
                        int index = fileName.lastIndexOf('\\');
                        fileName = fileName.substring(index+1);
                        boolean isMatch = Pattern.matches(pattern, fileName);
                        if(isMatch){
                            String fileName2 = new String(fileName.getBytes(encoding),"UTF-8");
                            fileList.add(fileName2);
                        }

                    }
                }
            }
        }
        catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return fileList;
    }

    /**
     * @Author haien
     * @Description 读取项目中的Excel表格，提取表格名称、大小和更新时间
     * @Date 10:08 2018/7/25
     * @Param [filepath]
     * @return java.util.List<hpscore.domain.FileInfo>
     **/
    public static List<FileInfo> readFileInfo(String filepath) throws IOException {
        //获取系统编码
        String encoding = System.getProperty("file.encoding"); //System.getProperty("xxx")：获取系统某种属性；file.encoding是每个程序的main入口的那个.java文件的保存编码
        List<FileInfo> fileList = new ArrayList<>();

        try {
            File file = new File(new String(filepath.getBytes(encoding),"UTF-8")); //new String("xxx".getBytes("编码方式"),"编码方式"):将xxx按某种编码（一般是原编码）转为字节码再构造为其他他=编码方式的字符串，主要是为了防止乱码
            if (file.isDirectory()) {
                System.out.println("文件夹");
                //获取子目录
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {

                        String pattern = ".*\\.xls"; //.表示一个任意的字符；*表示前面的字符可以出现0次或多次；\\是转义字符
                        String fileName =readfile.getName();
                        int index = fileName.lastIndexOf('\\'); //fileName中最后一次出现\\的第一个字符的索引
                        fileName = fileName.substring(index+1); //获得后面的子串，这里是获得\\后面的filelist[i]
                        boolean isMatch = Pattern.matches(pattern, fileName); //验证文件名是否为xxx.xls
                        if(isMatch){
                            String fileName2 = new String(fileName.getBytes(encoding),"UTF-8");
                            //获得将要操作的文件
                            Path path = Paths.get(fileName2);
                            //获取访问基本属性的BasicFileAttributeView
                            BasicFileAttributeView basicView = Files.getFileAttributeView(
                                    path, BasicFileAttributeView.class);
                            //获取访问基本属性的BasucFileAttributes
                            BasicFileAttributes basicAttribs = basicView.readAttributes();
                            //最后修改时间
                            Date date =new Date(basicAttribs.lastModifiedTime().toMillis());
                            Format format = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
                            String updateTime = format.format(date);
                            //文件大小
                            long size = basicAttribs.size();
                            fileList.add(new FileInfo(fileName2,size,updateTime));
                        }

                    }
                }
            }
        }
        catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return fileList;
    }
}
