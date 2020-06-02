package com.yuyuedao.yydwechat.entity;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class nnn {

    public static void main(String [] args) throws IOException {

        String result="{\"Code\":100,\"Message\":\"成功\",\"Data\":{\"AdminShopManagerLowerList\":[{\"UId\":159,\"UserName\":\"安玺\uD83C\uDF52\",\"NickName\":\"安玺\uD83C\uDF52\",\"Avatar\":\"https://wx.qlogo.cn/mmopen/vi_32/Vzm9ziaaIRKj8niapTz2qicQ8RD4E6Q9boLZQT3kZeKsRibI9kKXHHYY20udJnlrSR7oiaCOIy3JEUubVsVedKk5nbA/132\",\"Mobile\":\"15625120213\",\"CreateTime\":\"2020-04-11T12:46:46.95\",\"CreateTimeStr\":\"2020-04-11 12:46:46\",\"Source\":1,\"SourceStr\":\"扫码客户\",\"Manager\":\"Chu Wut Man\",\"Mediation\":\"智仁\"},{\"UId\":151,\"UserName\":\"FWY\",\"NickName\":\"FWY\",\"Avatar\":\"https://wx.qlogo.cn/mmopen/vi_32/bY7HDvGj5jJF8EzloqTiajN6YrKIicCcvJZwMxy3pXD9Z3m15C7LFx6P22134hsj2xUia1oXXiadusVytlTOlM4Jhg/132\",\"Mobile\":\"13265967196\",\"CreateTime\":\"2020-04-08T15:54:56.303\",\"CreateTimeStr\":\"2020-04-08 15:54:56\",\"Source\":1,\"SourceStr\":\"扫码客户\",\"Manager\":\"Chu Wut Man\",\"Mediation\":\"智仁\"},{\"UId\":150,\"UserName\":\"楚云\uD83C\uDF38\",\"NickName\":\"楚云\uD83C\uDF38\",\"Avatar\":\"https://wx.qlogo.cn/mmopen/vi_32/OdWL2XaxdJWlTCWibichUEdDAECdg0DOo9kz4PZUfdDYQnFD0QFNoCeicKwDWWb08hYLKnOsO40Nr4GOTm72icGT1w/132\",\"Mobile\":\"15802032843\",\"CreateTime\":\"2020-04-08T15:47:02.01\",\"CreateTimeStr\":\"2020-04-08 15:47:02\",\"Source\":1,\"SourceStr\":\"扫码客户\",\"Manager\":\"Chu Wut Man\",\"Mediation\":\"智仁\"},{\"UId\":149,\"UserName\":\"Anna安娜\",\"NickName\":\"Anna安娜\",\"Avatar\":\"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKQkicH54ODehhsLVeJjpHr7SYYSibDvmN8Wu5KulbatSuFHQANOxKZ695eMjyINtetYm9BrLFFqMfQ/132\",\"Mobile\":\"13808833532\",\"CreateTime\":\"2020-04-08T15:34:22.583\",\"CreateTimeStr\":\"2020-04-08 15:34:22\",\"Source\":1,\"SourceStr\":\"扫码客户\",\"Manager\":\"Chu Wut Man\",\"Mediation\":\"智仁\"}],\"Total\":44}}";
        Map<String,Object> jsonToMap = JSONObject.parseObject(result);

        jsonToMap.get("Data");

        String s= jsonToMap.get("Data").toString();

        Map<String,Object> jsonToMap1 = JSONObject.parseObject(s);


        jsonToMap1.get("AdminShopManagerLowerList");
        System.out.println(jsonToMap1.get("AdminShopManagerLowerList"));

        List<Map> dd=JSONObject.parseArray(jsonToMap1.get("AdminShopManagerLowerList").toString(),Map.class);






        //String b=jsonToMap1.get("AdminShopManagerLowerList").toString();

        //创建工作薄对象
        HSSFWorkbook workbook=new HSSFWorkbook();//这里也可以设置sheet的Name
        //创建工作表对象
        HSSFSheet sheet = workbook.createSheet();

        Iterator iterator = dd.iterator();


        HSSFRow row = sheet.createRow(0);//设置第一行，从零开始
        int i=0;

        while(iterator.hasNext()){
            Object o = iterator.next();
            if(o instanceof Map){


                workbook.setSheetName(0,"sheet的Name");//设置sheet的Name
                Map m = (Map) o;
                System.out.println(m);
                Iterator iter = m.entrySet().iterator();
                int first_j=0;
                while (iter.hasNext()) {

                    Map.Entry entry = (Map.Entry) iter.next();
                    String[] key_value = entry.toString().split("=");
                    System.out.println(key_value[1]);
                    //sheet.createRow(first_j);

                    row.createCell(first_j++).setCellValue(key_value[1]);

                    first_j++;
                }
                i++;
                row =sheet.createRow(i);
            }
        }








//        //创建工作薄对象
//        HSSFWorkbook workbook=new HSSFWorkbook();//这里也可以设置sheet的Name
//        //创建工作表对象
//        HSSFSheet sheet = workbook.createSheet();
//        //创建工作表的行
//        HSSFRow row = sheet.createRow(0);//设置第一行，从零开始
//        row.createCell(2).setCellValue("aaaaaaaaaaaa");
//        row.createCell(1).setCellValue("aaaaaaaaaaaa");//第一行第三列为aaaaaaaaaaaa
//        row.createCell(0).setCellValue(new Date());//第一行第一列为日期
//        workbook.setSheetName(0,"sheet的Name");//设置sheet的Name
//
        //文档输出
        FileOutputStream out = new FileOutputStream("D:/aaa.xls");
        workbook.write(out);



    }


}
