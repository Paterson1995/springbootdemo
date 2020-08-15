package com.demo.springbootdemo.util;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchAndEnumDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch=new CountDownLatch(6);
        for (int i = 1; i <=6 ; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+" 国被灭");
                countDownLatch.countDown();
            }, CountryEnum.list(i).getRetMsg()).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+" 秦国一统华夏");
    }

}

enum CountryEnum {
    ONE(1, "齐"), TWO(2, "楚"), THREE(3, "燕"), FOUR(4, "赵"), FIVE(5, "魏"), SIX(6, "韩");
    private Integer retCode;
    private String retMsg;

    CountryEnum(Integer retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public Integer getRetCode() {
        return retCode;
    }

    public void setRetCode(Integer retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public static CountryEnum list(int idx) {
        CountryEnum[] countryEnums = CountryEnum.values();
        for (CountryEnum countryEnum : countryEnums) {
            if (idx==countryEnum.getRetCode())
                return countryEnum;
        }
        return null;
    }
}
