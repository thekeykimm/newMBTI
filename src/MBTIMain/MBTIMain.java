package MBTIMain;

import MBTIDAO.MBTISQL;
import MBTIDTO.MBTI1Type;
import MBTIDTO.MBTIUser;
import MBTIDTO.Questions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MBTIMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MBTIUser muser = new MBTIUser();
        MBTI1Type mbti1Type = new MBTI1Type();
        MBTISQL sql = new MBTISQL();
        Questions ques = new Questions();
        List<Integer> list = new ArrayList<Integer>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy년MM월dd일 hh:mm:ss");
        String date = dtf.format(LocalDateTime.now());
        // DB 접속
        sql.connect();

        boolean run1 = true;

        while (run1) {
            boolean run2 = true;
            System.out.println("===============성격검사===============");
            System.out.println("[1]테스트      [2]결과조회     [3]종료");
            System.out.println("=====================================");
            System.out.print("메뉴선택 > ");
            int menu = sc.nextInt();

            switch (menu) {
                case 1:
                    muser.setuNo(sql.genNo());
                    muser.setDate(date);
                    System.out.println("당신의 이름은?");
                    muser.setuName(sc.next());
                    System.out.printf("안녕하세요, %s님! 지금부터 '4간지st' <성격유형검사>를 시작합니다!\n", muser.getuName());
//                    list = sql.randArray();
//                    System.out.println(list);
//                    String mbti1 = sql.classify(list);
                    String mbti1 = sql.classify(sql.randArray());
                    muser.setMbti1(mbti1);
                    muser.setMbti2(sql.newMbti(mbti1));
                    sql.updateMuser(muser);
                    sql.selectResult(muser);

                    while (run2) {
                        System.out.println("=========================궁합보기=========================");
                        System.out.println("[1]나와 잘 맞는♥ 궁합은?  [2]나와 파국인♨ 궁합은?   [3]종료");
                        System.out.println("==========================================================");
                        System.out.print("메뉴선택 > ");
                        int se = sc.nextInt();
                        switch (se) {
                            case 1:
                                sql.match1(muser);
                                break;
                            case 2:
                                sql.match2(muser);
                                break;
                            case 3:
                                System.out.println("검사를 종료 합니다..");
                                run2 = false;
                                break;
                            default:
                                System.out.println("1 또는 2 중에 선택하세요");
                        }
                    }
                    break;
                        case 2:
                            int checkCnt = sql.selectUser();
                            if (checkCnt == 1) {
                                System.out.print("조회하고 싶은 사람의 번호를 입력하세요 \n>> ");
                                int no = sc.nextInt();
                                sql.userInfo(no);
                                break;
                            } else {
                                break;
                            }
                        case 3:
                            System.out.println("테스트를 종료합니다...");
                            run1 = false;
                            break;
                        default:
                            System.out.println("1 또는 2 중에 선택하세요...");
                            break;
                    }

            }

        }
    }

