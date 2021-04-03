package schedule;

public class Variable {

    int teacher;
    String course;
    int[] domain;
    int domainLength;
    int value;
    Variable[] neighbourhoods;
    int[] neighbourhoodsIndex; //اندیس متغیرهای همسایه یک متغیر

    public Variable(int teacher, String course, int[] domain) {
        this.teacher = teacher;
        this.course = course;
        this.domain = domain.clone();
        domainLength = domain.length;
        this.value = 0;
        neighbourhoods = new Variable[0];
        neighbourhoodsIndex = new int[0];
    }

    public void addNeighbourhoods(Variable neighbourhood, int index) { //add a neighburhood to special variable 
        Variable[] temp = new Variable[neighbourhoods.length + 1]; //to add a cell to an array , must get a copy from array then araise array's length one ! 
        System.arraycopy(neighbourhoods, 0, temp, 0, neighbourhoods.length);
        temp[neighbourhoods.length] = neighbourhood;
        neighbourhoods = temp;  //cause we use word neighbourhoods in code, so we change temp to neighburhoods .

        int[] tempIndex = new int[neighbourhoodsIndex.length + 1];  //to add the index of neighburhood that added.
        System.arraycopy(neighbourhoodsIndex, 0, tempIndex, 0, neighbourhoodsIndex.length);
        tempIndex[neighbourhoodsIndex.length] = index;
        neighbourhoodsIndex = tempIndex;
    }

    public boolean setValue(int value) {   //this method set the value of a special variable 
        this.value = value;
        //forward checking
        for (int i = 0; i < neighbourhoods.length; i++) {
            if (neighbourhoods[i].value == 0) {         ////////////
                boolean flag = neighbourhoods[i].removeValuefromDomain(value); //after setting of values , we must remove them from neighburs who have this value.
                if (!flag) {
                    return false;
                }
            }
        }
        return true;
    }

    public void resetValue(int value) {
        this.value = 0;
        for (int i = 0; i < neighbourhoods.length; i++) {
            if (neighbourhoods[i].value == 0) {
                neighbourhoods[i].addValuetoDomain(value);  //after backtrack to a node that its values set worng, we must add that value to nodes get removed from.
            }
        }
    }

    private boolean removeValuefromDomain(int value) {
        for (int i = 0; i < domain.length; i++) {
            if (domain[i] == value) {
                domain[i] *= -1;
                domainLength--;
                break;
            }
        }
        return domainLength > 0;
    }

    private void addValuetoDomain(int value) {
        for (int i = 0; i < domain.length; i++) {
            if (domain[i] == -value) {
                domain[i] *= -1;
                domainLength++;
                return;
            }
        }
    }

}
