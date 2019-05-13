package com.motian.crp.utils;

import com.motian.crp.dao.data.UserData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.motian.crp.constant.CrpConst.DEFAULT_RANDOM_RANGE_BYTES;
import static com.motian.crp.constant.CrpConst.StatusField.USER_INFO;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@Slf4j
public class CrpServiceUtils {
    private static final HashMap<String, String> mFileTypes = new HashMap<String, String>();

    static {
        mFileTypes.put("FFD8FFE1", "jpg");
        mFileTypes.put("89504E47", "png");
        mFileTypes.put("47494638 ", "gif");
        mFileTypes.put("49492A00", "tif");
        mFileTypes.put("424D", "bmp");
        mFileTypes.put("41433130", "dwg");
        mFileTypes.put("38425053 ", "psd");
        mFileTypes.put("7B5C727466", "rtf");
        mFileTypes.put("3C3F786D6C", "xml");
        mFileTypes.put("68746D6C3E ", "html");
        mFileTypes.put("44656C69766572792D646174", "eml");
        mFileTypes.put("CFAD12FEC5FD746F ", "dbx");
        mFileTypes.put("2142444E", "pst");
        mFileTypes.put("D0CF11E0", "xls/doc");
        mFileTypes.put("5374616E64617264204A", "mdb");
        mFileTypes.put("FF575043", "wpd");
        mFileTypes.put("252150532D41646F6265", "eps/ps");
        mFileTypes.put("255044462D312E", "pdf");
        mFileTypes.put("E3828596", "pwl");
        mFileTypes.put("504B0304", "zip");
        mFileTypes.put("52617221", "rar");
        mFileTypes.put("57415645", "wav");
        mFileTypes.put("41564920", "avi");
        mFileTypes.put("2E7261FD", "ram");
        mFileTypes.put("2E524D46", "rm");
        mFileTypes.put("000001BA", "mpg");
        mFileTypes.put("000001B3", "mpg");
        mFileTypes.put("6D6F6F76", "mov");
        mFileTypes.put("3026B2758E66CF11", "asf");
        mFileTypes.put("4D546864", "mid");
        mFileTypes.put("FFD8FFE000104A464946", "jpg"); //JPEG (jpg)
        mFileTypes.put("89504E470D0A1A0A0000", "png"); //PNG (png)
        mFileTypes.put("47494638396126026F01", "gif"); //GIF (gif)
        mFileTypes.put("49492A00227105008037", "tif"); //TIFF (tif)
        mFileTypes.put("424D228C010000000000", "bmp"); //16色位图(bmp)
        mFileTypes.put("424D8240090000000000", "bmp"); //24位位图(bmp)
        mFileTypes.put("424D8E1B030000000000", "bmp"); //256色位图(bmp)
        mFileTypes.put("41433130313500000000", "dwg"); //CAD (dwg)
        mFileTypes.put("3C21444F435459504520", "html"); //HTML (html)
        mFileTypes.put("3C21646F637479706520", "htm"); //HTM (htm)
        mFileTypes.put("48544D4C207B0D0A0942", "css"); //css
        mFileTypes.put("696B2E71623D696B2E71", "js"); //js
        mFileTypes.put("7B5C727466315C616E73", "rtf"); //Rich Text Format (rtf)
        mFileTypes.put("38425053000100000000", "psd"); //Photoshop (psd)
        mFileTypes.put("46726F6D3A203D3F6762", "eml"); //Email [Outlook Express 6] (eml)
        mFileTypes.put("D0CF11E0A1B11AE10000", "doc"); //MS Excel 注意：word、msi 和 excel的文件头一样
        mFileTypes.put("255044462D312E350D0A", "pdf"); //Adobe Acrobat (pdf)
        mFileTypes.put("2E524D46000000120001", "rmvb"); //rmvb/rm相同
        mFileTypes.put("464C5601050000000900", "flv"); //flv与f4v相同
        mFileTypes.put("0000001C667479706D70", "mp4"); //00000020667479706d70
        mFileTypes.put("49443303000000002176", "mp3");
        mFileTypes.put("000001BA210001000180", "mpg"); //
        mFileTypes.put("3026B2758E66CF11A6D9", "wmv"); //wmv与asf相同
        mFileTypes.put("52494646E27807005741", "wav"); //Wave (wav)
        mFileTypes.put("52494646D07D60074156", "avi");
        mFileTypes.put("4D546864000000060001", "mid"); //MIDI (mid)
        mFileTypes.put("504B0304140000000800", "zip");
        mFileTypes.put("526172211A0700CF9073", "rar");
        mFileTypes.put("235468697320636F6E66", "ini");
        mFileTypes.put("504B03040A0000000000", "jar");
        mFileTypes.put("4D5A9000030000000400", "exe");//可执行文件
        mFileTypes.put("3C25402070616765206C", "jsp");//jsp文件
        mFileTypes.put("4D616E69666573742D56", "mf");//MF文件
        mFileTypes.put("3C3F786D6C2076657273", "xml");//xml文件
        mFileTypes.put("494E5345525420494E54", "sql");//xml文件
        mFileTypes.put("7061636B616765207765", "java");//java文件
        mFileTypes.put("406563686F206F66660D", "bat");//bat文件
        mFileTypes.put("1F8B0800000000000000", "gz");//gz文件
        mFileTypes.put("6C6F67346A2E726F6F74", "properties");//bat文件
        mFileTypes.put("CAFEBABE0000002E0041", "class");//bat文件
        mFileTypes.put("49545346030000006000", "chm");//bat文件
        mFileTypes.put("04000000010000001300", "mxp");//bat文件
        mFileTypes.put("504B0304140006000800", "docx");//docx文件
        mFileTypes.put("6431303A637265617465", "torrent");
        mFileTypes.put("3C68746D6C20786D6C6E", "htm");//猎聘、智联简历。htm
        mFileTypes.put("46726F6D3A3CD3C920CD", "mht");//51job简历。mht
        mFileTypes.put("CFAD12FEC5FD746F", "dbx"); //Outlook Express (dbx)
        mFileTypes.put("AC9EBD8F", "qdf"); //Quicken (qdf)
    }


    public static String getFileType(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1);


//        log.info("filePath={}", filePath);
//        String value = StringUtils.EMPTY;
//        try (FileInputStream is = new FileInputStream(filePath)) {
//            byte[] b = new byte[4];
//            is.read(b, 0, b.length);
//            value = bytesToHexString(b);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        log.info("value={}", value);
//        return mFileTypes.get(value.toUpperCase());
    }

    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (byte aSrc : src) {
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
            hv = Integer.toHexString(aSrc & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }

    public static boolean isVideo(String fileType) {
        return fileType.equals("mp4") || fileType.equals("avi");
    }

    public static final Map<Integer, Integer> ValueMap = new HashMap<Integer, Integer>() {
        {
            put(0, 50);
            put(1, 100);
            put(2, 150);
            put(3, 200);
        }
    };

    public static long generateId() {
        return getRandomNum(DEFAULT_RANDOM_RANGE_BYTES) + (System.currentTimeMillis() << DEFAULT_RANDOM_RANGE_BYTES);
    }

    public static long getRandomNum(int bit) {
        long rangeMaxValue = 1 << DEFAULT_RANDOM_RANGE_BYTES;
        return UUID.randomUUID().hashCode() & (rangeMaxValue - 1) + rangeMaxValue;
    }

    public static String getUserId(HttpServletRequest request) {
        String userID = StringUtils.EMPTY;
        UserData userData = (UserData) request.getSession().getAttribute(USER_INFO);
        if (userData != null) {
            userID = userData.getAccountId();
        }
        log.info("getUserId::userID={}", userID);

        return userID;
    }

    public static String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        return formatter.format(new Date());
    }

    public static int getIndex(List<Long> data, long value) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == value) {
                return i + 1;
            }
        }
        return -1;
    }
}
