package schedule;

import java.io.File;
import java.util.*;

public class Schedule {

    private static Variable[] variables;        //آرایه ای از متغیرها
    private static int remainingvariables;      //تعداد متغیرهای مقداردهی نشده

    public static void main(String[] args) {
        /*if (args.length != 2) {      //در دستور خط فرمان فقط دو پارامتر فایل ورودی و شماره ی الگوریتم هست  در غیر اینصورت باید ارور بدهد
            System.err.println("Invalid command !");
            return;
        }*/
        String fileName = "input.txt";//args[0];
        Scanner input;
        try {
            int b = 0, t = 0, m = 0;
            String[] cources;       //نام درس ها
            String[][] interests;   //دروس مورد علاقه ی استادها
            String[][] conflicts;   //دروسی که با هم تداخل دارند
            int[][] times;          //زمان خالی استادها
            remainingvariables = 0;
            input = new Scanner(new File(fileName));
            String s = input.nextLine();
            String[] substrings = s.split(", ");
            b = Integer.parseInt(substrings[0]);
            t = Integer.parseInt(substrings[1]);
            m = Integer.parseInt(substrings[2]);

            interests = new String[t][];    //شماره ی ستون را مشخص نکردیم زیرا آرایه در جاوا قابلیت این را دارد که هر سطر ستون های مختلفی داشته باشد
            times = new int[t][];
            conflicts = new String[m][];

            String line = input.nextLine();
            cources = line.split(", ");

            for (int i = 0; i < t; i++) {
                String cs = input.nextLine();
                String[] subs = cs.split(", ");
                int k = Integer.parseInt(subs[0]);
                interests[i] = new String[k];
                System.arraycopy(subs, 1, interests[i], 0, k);
                remainingvariables += k;
            }
            for (int i = 0; i < t; i++) {
                String tm = input.nextLine();
                String[] subs = tm.split(", ");
                int k = Integer.parseInt(subs[0]);
                times[i] = new int[k];
                for (int j = 1; j < subs.length; j++) {
                    times[i][j - 1] = Integer.parseInt(subs[j]);
                }
            }
            for (int i = 0; i < m; i++) {
                String cf = input.nextLine();
                String[] subs = cf.split(", ");
                int k = Integer.parseInt(subs[0]);
                conflicts[i] = new String[k];
                System.arraycopy(subs, 1, conflicts[i], 0, k);
            }
            createCSPGraph(t, interests, times, conflicts);
        } catch (Exception ex) {
            System.err.println("Invalid Input");
        }

        int alg = 1;//Integer.parseInt(args[1]);    //شماره ی الگوریتم وارد شده در فرمان وارد شده خط فرمان
        int start;
        switch (alg) {
            case 1:
                //BT-FC 
                if (BT_FC(0)) {
                    for (int i = 0; i < variables.length; i++) {
                        System.out.println(variables[i].course + ", " + (variables[i].teacher + 1) + ", " + variables[i].value);
                    }
                } else {
                    System.out.println("No Solution");
                }
                break;
            case 2:
                //BT-FC-MRV
                start = MRV();
                if (BT_FC_MRV(start)) {
                    for (int i = 0; i < variables.length; i++) {
                        System.out.println(variables[i].course + ", " + (variables[i].teacher + 1) + ", " + variables[i].value);
                    }
                } else {
                    System.out.println("No Solution");
                }
                break;
            case 3:
                //AC3+BT-FC-MRV
                AC3();
                start = MRV();
                if (BT_FC_MRV(start)) {
                    for (int i = 0; i < variables.length; i++) {
                        System.out.println(variables[i].course + ", " + (variables[i].teacher + 1) + ", " + variables[i].value);
                    }
                } else {
                    System.out.println("No Solution");
                }
                break;
            default:
                System.err.println("Algorithm not found!");
        }
    }

    // خروجی را چاپ میکند
    private static void output() {
        for (int i = 0; i < variables.length; i++) {
            System.out.println(variables[i].course + ", " + variables[i].teacher + ", " + variables[i].value);
        }
    }

//****************************************************************************************************************
    private static void createCSPGraph(int t, String[][] interests, int[][] times, String[][] conflicts) {      //این متد گراف محدودیت را می سازد
        variables = new Variable[remainingvariables];
        int indx = 0;       //ایندکس اون متغیری که میخواد ساخته بشه
        int start;
        for (int i = 0; i < t; i++) {   //به ازای هر استاد
            start = indx;
            for (int j = 0; j < interests[i].length; j++) {     //به ازای همه ی موردعلاقه هاش
                variables[indx] = new Variable(i + 1, interests[i][j], times[i]);       //متغیر می سازد
                indx++;     //اندیس یکی اضافه می شود
            }
            for (int k = start; k < indx; k++) {
                for (int l = start; l < indx; l++) {
                    if (k != l) {
                        variables[k].addNeighbourhoods(variables[l], l);       //دو متغیر را همسایه می کند
                    }
                }
            }
        }
        //باید تمام دروسی که باهم تداخل دارند را در گره های شامل آن پیدا کنیم
        for (int i = 0; i < conflicts.length; i++) {
            ArrayList<Integer> list = new ArrayList<>();        //گره هایی را نگه میدارد که درساشون باهم تداخل دارد
            for (int j = 0; j < conflicts[i].length; j++) {
                list.add(findNodeWithCourse(conflicts[i][j]));
            }
            for (int k = 0; k < list.size(); k++) {
                for (int l = 0; l < list.size(); l++) {
                    if (k != l) {
                        variables[list.get(k)].addNeighbourhoods(variables[list.get(l)], list.get(l));      //تمام خانه های لیست را باهم همسایه میکند
                    }
                }
            }
        }
    }

    private static int findNodeWithCourse(String course) {
        for (int i = 0; i < variables.length; i++) {
            if (variables[i].course.equals(course)) {
                return i;
            }

        }
        return -1;
    }

    //این الگوریتم ها در ابتدا یک اینتیجر می گیرند که شماره ی اندیس متغیری که قرار است مقداردهی شود است
    // خروجی این الگوریتم ها یک متغیر بولین است که در صورت درستی مشخص میکند که با این مقداردهی به این متغیر به جواب میرسیم
    private static boolean BT_FC(int n) {
        int[] domain = variables[n].domain;     //دامنه متغیر ورودی را در آرایه ای قرار میدهد
        if (remainingvariables == 1) {          //اگر یک متغیر باقی مانده باشد که مقداردهی نشده باشد آنگاه برگ است
            for (int i = 0; i < domain.length; i++) {
                if (domain[i] > 0) {        //اگر خانه ی خاص دامنه مثبت بود یعنی حذف نشده بود
                    variables[n].setValue(domain[i]);   //آن مقدار دامنه را به متغیر بده
                    return true;
                }
            }
            return false;       //در صورتیکه هیچ مقدار مثبتی نداشته باشد(دامنه اش تهی باشد)
        } else {
            //  175 تا168 // be ezaye har meghdar dehi be gereye n , backtrack ra baraye n+1 ejra mikonim , dar suratike backtrack ture bargardanad 
            remainingvariables--;       //متغیر  را از متغیر های باقیمانده حذف میکنیم
            for (int i = 0; i < domain.length; i++) {
                if (domain[i] > 0) {
                    if (variables[n].setValue(domain[i])) {
                        if (BT_FC(n + 1)) {
                            return true;
                        }
                    }
                    variables[n].resetValue(domain[i]);     // اگر فالس بود بالا میرود و به متغیر مقدار دیگر می دهد
                }
            }
            remainingvariables++;       //هرچی مقداردهی از دامنه ی گره به آن کردیم و بک ترک را برای گره ی بعدی انجام دادیم دیدیم که به جواب نمیرسیم برمیگردیم و آن گره را به مق به متغیرهای باقیمانده اضافه میکنیم و پره ی دیگری را مقدار میدهیم
            return false;
        }
    }

    private static boolean BT_FC_MRV(int n) {
        int[] domain = variables[n].domain;
        if (remainingvariables == 1) {
            for (int i = 0; i < domain.length; i++) {
                if (domain[i] > 0) {
                    variables[n].setValue(domain[i]);
                    return true;
                }
            }
            return false;
        } else {
            remainingvariables--;
            for (int i = 0; i < domain.length; i++) {
                if (domain[i] > 0) {
                    if (variables[n].setValue(domain[i])) {
                        int next = MRV();       // اندیس متغیری که کمترین مقدار باقیمانده را در دامنه دارد را برمیگرداند
                        if (BT_FC_MRV(next)) {
                            return true;
                        }
                    }
                    variables[n].resetValue(domain[i]);
                }
            }
            remainingvariables++;
            return false;
        }
    }

    private static int MRV() {      //از بین متغیرهای مقداردهی نشده آنکه کمترین دامنه معتبر را دارد انتخاب میشود
        int min = Integer.MAX_VALUE, imin = -1;
        for (int i = 0; i < variables.length; i++) {        //برای تمامی متغیرها ...
            if (variables[i].value == 0 && variables[i].domainLength < min) {   // اگر متغیر موردنظر مقداردهی نشده  و طول دامنه ی آن کمتر از مین است آنگاه...
                min = variables[i].domainLength;   //طول دامنه آن را در مین بگذار 
                imin = i;   //اندیس آنرا در  imin
            }
        }
        return imin;
    }

    private static void AC3() {
        Queue queue = new Queue();
        for (int i = 0; i < variables.length; i++) {        //به ازای تمامی متغیرها
            for (int j = 0; j < variables[i].neighbourhoodsIndex.length; j++) {     //به ازای تمام همسایه های متغیر خاص
                Arc a = new Arc(i, variables[i].neighbourhoodsIndex[j]);        //یالی جدید می سازد از متغیر خاص به همسایه ی آن
                queue.add(a);       //آن یال را به صف اضافه میکند
            }
        }
        while (!queue.isEmpty()) {      //تا زمانی که صف خالی نشده
            Arc a = queue.remove();     //یال خاص را از صف حذف کن
            if (removeInconsistentValues(a.i, a.j)) {       //اگر یال ناسازگار خاص حذف شود
                int[] neighbourhoodsIndex = variables[a.i].neighbourhoodsIndex;
                for (int k = 0; k < neighbourhoodsIndex.length; k++) {
                    queue.add(new Arc(neighbourhoodsIndex[k], a.i));
                }
            }
        }
    }

    private static boolean removeInconsistentValues(int i, int j) {     //از دامنه ی اولی تمام مقادیری که باعث میشود دامنه ی دومی تهی شود را حذف میکند اگر عضوی از دامنه اولی حذف شد درست برمیگرداند
        if (variables[j].domain.length == 1) {      //اگر طول دامنه ی متغیر خاص 1 بود
            int x = variables[j].domain[0];
            int length = variables[i].domain.length;
            for (int k = 0; k < length; k++) {      //به ازای تمامی مقادیر طول دامنه ی اولی
                if (variables[i].domain[k] == x) {      //مقداری از دامنه ی اولی برابر تک مقدار دامنه ی دومی بود
                    int[] temp = new int[length - 1];       //آرایه ای به طول یکی کمتر از اولی درست کن  ////حذف آن مقدار از دامنه اولی
                    System.arraycopy(variables[i].domain, 0, temp, 0, k);
                    System.arraycopy(variables[i].domain, k + 1, temp, k, length - k - 1);
                    variables[i].domain = temp;
                    variables[i].domainLength--;
                    if(variables[i].domainLength == 0){
                        remainingvariables--;
                    }
                    return true;
                }
            }
        }
        return false;
    }

}
