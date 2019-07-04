package cn.lqcnb.blog;



import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {

//        DateFormat newDateFormat = DateUtils.getNewDateFormat(DateUtils.newFormat);
//        Date date = DateUtils.parseDateNewFormat(new Date().toString());
//        String dateString = DateUtils.getDateString(new Date());
//
//        String dateString1 = DateUtils.getDateString(new Date(), DateUtils.getNewDateFormat(DateUtils.newFormat));

//        DateUtils.parseDate(new Date().toString())

//        Date date=new Date();
//        SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");//yyyyMMdd，格式
//        String str=format.format(date);

//        System.out.println(DateUtils.getEmailDate(new Date()));
//        System.out.println(DateUtils. getNewFormatDateString(new Date()));
//        System.out.println(DateUtils.getSimpleDate(new Date()));
//        DateUtils.getSimpleDate(new Date());
        try {
            Date date = DateUtils.parseDate(new Date().toLocaleString(), "yyyy-MM-dd HH:mm:ss");
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
