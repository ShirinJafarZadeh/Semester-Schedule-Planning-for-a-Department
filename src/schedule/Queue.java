package schedule;
import java.util.ArrayList;

public class Queue {

    private ArrayList<Arc> queue;       //صف : لیستی از یالها
    
    public Queue() {
        queue = new ArrayList();
    }

    public void add(Arc a) {        //یال به صف اضافه میکند
        queue.add(a);
    }

    public Arc remove() {           //یال از صف حذف میکند
        Arc result = queue.get(0);
        queue.remove(0);
        return result;
    }

    public boolean isEmpty() {      //میگوید صف خالیست یا نه
        return queue.isEmpty();
    }

}
