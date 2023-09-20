package MBTIMain;

import java.util.ArrayList;
import java.util.List;

public class MBTItest {
    public static void main(String[] args) {
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
            System.out.println(list);
            System.out.println(cnt);
            if (cnt == 0){
                run = false;
            }
        }
    }
}
