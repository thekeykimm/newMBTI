package MBTIDTO;

import java.util.ArrayList;

public class MBTIUser {
    // 1. 필드
    private int uNo;
    private String uName;
    private String mbti1;
    private String mbti2;
    private String date;

    // 3. 메소드
    public int getuNo() {
        return uNo;
    }

    public void setuNo(int uNo) {
        this.uNo = uNo;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getMbti1() {
        return mbti1;
    }

    public void setMbti1(String mbti1) {
        this.mbti1 = mbti1;
    }

    public String getMbti2() {
        return mbti2;
    }

    public void setMbti2(String mbti2) {
        this.mbti2 = mbti2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}