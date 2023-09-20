package MBTIDAO;

import MBTIDTO.MBTIUser;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MBTISQL {
    Scanner sc = new Scanner(System.in);
    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;
    boolean run2 = true;
    boolean run3 = true;

    // 1. DB 접속 메소드
    public void connect() {
        con = DBConnection.DBConnect();
    }

    // 2. 번호 생성 메소드
    public int genNo() {
        int uNo = 0;
        String sql = "SELECT MAX(UNO) FROM MUSER";
        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                uNo = rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return uNo;
    }

    // 3-1. 1~12 랜덤 배열 발생 메소드
    public List<Integer> randArray() {
        List<Integer> list = new ArrayList<Integer>();
        int a = 0;
        boolean run = true;
        while (run) {
            a = (int) (((Math.random()) * 12) + 1);
            list.add(a);
            if (list.size() == 12) {
                run = false;
            }
        }
        run = true;
        while (run) {
            int cnt = 0;
            for (int i = 0; i < list.size() - 1; i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    boolean run2 = true;
                    while (run2) {
                        if (list.get(i) == list.get(j)) {
                            cnt++;
                            a = (int) (((Math.random() * 12) + 1));
                            list.set(j, a);
                        } else {
                            run2 = false;
                        }
                    }
                }
            }
            if (cnt == 0) {
                run = false;
            }
        }
        return list;
    }

    // 3-2. 질문 메소드
    public String questions(int i) {
        String ques = null;
        String sql = "SELECT QDESC FROM QUESTIONS WHERE QNO = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, i);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("\n----------------------------------------------------------------");
                ques = rs.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ques;
    }

    // 3-3. MBTI 유형 분류 메소드
    public String classify(List<Integer> list) {
        String newType = "";
        ArrayList<Integer> uType = new ArrayList<Integer>();
        String ans = null;
        int cntEI = 0;
        int cntNS = 0;
        int cntFT = 0;
        int cntPJ = 0;
        boolean run = true;
        int j = 0;

        while (run) {
            if (j == list.size()) {
                run = false;
                break;
            }
            System.out.println(questions(list.get(j)));
            System.out.print("답변선택 (1 또는 2) >> ");
            ans = sc.next();
            switch (ans) {
                case "1":
                    if ((list.get(j) == 1 || list.get(j) == 2 || list.get(j) == 3)) {
                        cntEI++;
                        j++;
                    } else if ((list.get(j) == 4 || list.get(j) == 5 || list.get(j) == 6)) {
                        cntNS++;
                        j++;
                    } else if ((list.get(j) == 7 || list.get(j) == 8 || list.get(j) == 9)) {
                        cntFT++;
                        j++;
                    } else if ((list.get(j) == 10 || list.get(j) == 11 || list.get(j) == 12)) {
                        cntPJ++;
                        j++;
                    }
                    break;
                case "2":
                    j++;
                    break;
                default:
                    System.out.println("1 또는 2 중에 입력하세요...");
                    break;
            }
        }

        uType.add(cntEI);
        uType.add(cntNS);
        uType.add(cntFT);
        uType.add(cntPJ);

        for (int i = 0; i < uType.size(); i++) {
            if (i == 0) {
                if (uType.get(i) == 3 || uType.get(i) == 2) {
                    newType += "E";
                } else {
                    newType += "I";
                }
            } else if (i == 1) {
                if (uType.get(i) == 3 || uType.get(i) == 2) {
                    newType += "N";
                } else {
                    newType += "S";
                }
            } else if (i == 2) {
                if (uType.get(i) == 3 || uType.get(i) == 2) {
                    newType += "F";
                } else {
                    newType += "T";
                }
            } else if (i == 3) {
                if (uType.get(i) == 3 || uType.get(i) == 2) {
                    newType += "P";
                } else {
                    newType += "J";
                }
            }
        }
        return newType;
    }

    // 3-4. m2type 정의 메소드
    public String newMbti(String mbti1) {
        String m2type = null;
        String sql = "SELECT M2TYPE FROM MBTI2TYPE WHERE M1TYPE = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, mbti1);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                m2type = rs.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return m2type;
    }

    // 4. muser update 메소드
    public void updateMuser(MBTIUser muser) {
        String sql = "INSERT INTO MUSER VALUES(?, ?, ?, ?, ?)";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, muser.getuNo());
            pstmt.setString(2, muser.getuName());
            pstmt.setString(3, muser.getMbti1());
            pstmt.setString(4, muser.getMbti2());
            pstmt.setString(5, muser.getDate());
            int result = pstmt.executeUpdate();

            if (result < 0) {
                System.out.println("검사 실패! 에러 발생...");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 5-1. 결과 보기 메소드
    public void selectResult(MBTIUser muser) {
        String sql = "SELECT M2.M2TYPE, M2.M1TYPE, D1.M1DESC FROM MBTI2TYPE M2, MBTI1TYPE M1, MBTI1DESC D1\n" +
                "WHERE M2.M1TYPE = M1.MTYPE AND M2.M1TYPE = D1.M1TYPE AND M2.M1TYPE = ?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, muser.getMbti1());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.printf("\n%s 님! 당신의 유형은 '%s(%s)'으로 나왔습니다!\n당신은 아마 이런 사람일지도..?\n"
                        , muser.getuName(), rs.getString(1), rs.getString(2));
                System.out.println("------------------------------------------------------------------------------");
                System.out.println(rs.getString(3));
                System.out.println("------------------------------------------------------------------------------\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 5-2. 검사 결과 목록 메소드
    public int selectUser() {
        int checkCnt = 0;
        String sql = "SELECT * FROM MUSER";
        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (!rs.next()) {
                System.out.println("조회할 회원이 없습니다...");
            } else {
                System.out.println("===========================================================");
                System.out.println("No.\t\tName    \tType\t\t\t\tDate");
                System.out.printf("[%d]\t\t%s\t\t%s(%s)   \t%s\n", rs.getInt(1), rs.getString(2), rs.getString(4), rs.getString(3), rs.getString(5));
                checkCnt = 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return checkCnt;
    }

    // 5-3. 다른 user의 검사결과 보기
    public void userInfo(int no) {
        String sql = "SELECT M.UNAME, M.U1TYPE, M.U2TYPE, D1.M1DESC FROM MUSER M, MBTI1DESC D1\n" +
                "WHERE D1.M1TYPE = M.U1TYPE AND M.UNO = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, no);
            rs = pstmt.executeQuery();
            if (!rs.next()) {
                System.out.println("조회할 회원이 없습니다...");
            } else {
                    System.out.printf("\n%s 님의 정보입니다.\n유형 : %s(%s) \n", rs.getString(1), rs.getString(3), rs.getString(2));
                    System.out.println("------------------------------------------------------------------------------");
                    System.out.printf("%s\n\n", rs.getString(4));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 6-1. 잘 맞는 mbti 메소드
    public void match1(MBTIUser muser) {
        String sql = "SELECT D.L2TYPE, C.M2TYPE, B.M1DESC FROM MBTI1TYPE A, MBTI1DESC B, MBTI2TYPE C, MATCH D " +
                "WHERE B.M1TYPE = A.MTYPE AND C.M1TYPE = A.MTYPE AND D.L2TYPE = A.MTYPE AND D.L1TYPE = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, muser.getMbti1());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("\nMBTI : " + rs.getString(1));
                System.out.println("송편 이름 : " + rs.getString(2));
                System.out.println("--------------------------------이 유형의 성격은--------------------------------");
                System.out.println(rs.getString(3));
                System.out.println("------------------------------------------------------------------------------\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 6-2. 잘 안맞는 mbti 메소드
    public void match2(MBTIUser muser) {
        String sql = "SELECT D.L3TYPE, C.M2TYPE, B.M1DESC FROM MBTI1TYPE A, MBTI1DESC B, MBTI2TYPE C, MATCH D " +
                "WHERE B.M1TYPE = A.MTYPE AND C.M1TYPE = A.MTYPE AND D.L3TYPE = A.MTYPE AND D.L1TYPE = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, muser.getMbti1());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("\nMBTI : " + rs.getString(1));
                System.out.println("송편 이름 : " + rs.getString(2));
                System.out.println("--------------------------------이 유형의 성격은--------------------------------");
                System.out.println(rs.getString(3));
                System.out.println("------------------------------------------------------------------------------\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //6-3. 매칭 (?) 기능 ?


}



