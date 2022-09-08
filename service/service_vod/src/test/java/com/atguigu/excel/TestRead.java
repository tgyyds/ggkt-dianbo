package com.atguigu.excel;

import com.alibaba.excel.EasyExcel;

public class TestRead {

    public static void main(String[] args) {
        //设置文件名称和路径
        String fileName = "D:\\ggkt.xlsx";
        //调用方法读操作
        EasyExcel.read(fileName,User.class,new ExcelListener()).sheet().doRead();
    }
}
