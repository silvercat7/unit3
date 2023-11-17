public class APList {
    private String[] list;
    private static final int DEFAULT_SIZE = 2;
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
            expand();
            add(element);
        }
        lastElement++;
    }

    public void add(int index, String element) {
        if (index == lastElement + 1 || (index > lastElement + 1 && index <= maxCapacity)) {
            add(element);
        } else if (index > maxCapacity || index < 0) {
            System.out.println("ERROR");
        } else {
            for (int i = lastElement + 1; i > index; i++) {
                list[i] = list[i - 1];
            }
            list[index] = element;
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
        while (remove(element)) {
            numRemoved++;
        }
        return numRemoved;
    }

    public void set(int index, String element) {
        if (index <= lastElement && index > 0) {
            list[index] = element;
        }
    }

    public String get(int index) {
        if (index >= 0 && index <= lastElement) {
            return list[index];
        } else if (Math.abs(index) <= lastElement) {
            return list[lastElement + 1 + index];
        } else {
            return null;
        }
    }

    public boolean contains(String value) {
        for (String element : list) {
            if (element.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return lastElement;
    }

    public void expand() {
        String[] newList = new String[maxCapacity * 2];
        maxCapacity = maxCapacity * 2;
        System.arraycopy(list, 0, newList, 0, list.length);
        list = newList;
    }
}
