public class APList {
    private String[] list;
    private static int DEFAULT_SIZE = 10;
    private int maxCapacity;
    private int lastElement;

    public APList() {
        list = new String[DEFAULT_SIZE];
        lastElement = -1;
    }

    public APList(int maxCapacity) {
        list = new String[maxCapacity];
        this.maxCapacity = maxCapacity;
        lastElement = -1;
    }

    public void add(String element) {
        if (lastElement + 1 < maxCapacity) {
            list[lastElement + 1] = element;
        } else {
            String[] newList = new String[maxCapacity * 2];
            maxCapacity = maxCapacity * 2;
            for (int i = 0; i < list.length; i++) {
                newList[i] = list[i];
            }
            newList[lastElement + 1] = element;
            list = newList;
        }
        lastElement++;
    }

    public void add(int index, String element) {
        if (index == lastElement + 1 || (index > lastElement + 1 && index <= maxCapacity)) {
            add(element);
        } else if (index > maxCapacity || index < 0) {
            System.out.println("ERROR");
        } else {
            String temp = list[index];
            list[index] = element;
            for (int i = index + 1; i < list.length; i++) {
                String temp2 = list[i];
                list[i] = temp;
                temp = temp2;
            }
        }
    }

    public void remove() {
        if (lastElement != -1) {
            list[lastElement] = null;
            lastElement--;
        }
    }

    public String remove(int index) {
        if (index >= 0 && index <= lastElement) {
            String toReturn = list[index];
            list[index] = null;
            int i = index;
            while (i < lastElement) {
                list[i] = list[i + 1];
                i++;
            }
            list[lastElement] = null;
            lastElement--;
            return toReturn;
        }
        return null;
    }

    public boolean remove(String element) {
        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(element)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    public int removeAll(String element) {
        int numRemoved = 0;
        while (contains(element)) {
            remove(element);
            numRemoved++;
        }
        return numRemoved;
    }

    public int size() {
        return lastElement;
    }

    public String get(int index) {
        if (index >= 0 && index <= lastElement) {
            return list[index];
        }
        return "";
    }

    public boolean contains(String value) {
        for (String element : list) {
            if (element.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
