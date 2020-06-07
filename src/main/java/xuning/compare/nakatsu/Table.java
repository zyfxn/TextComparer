package xuning.compare.nakatsu;

import xuning.compare.range.PairRange;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;

public class Table {

    private ArrayList<Line> nodeTable = new ArrayList<>();

    public void add(int i, int j, int h) {
        j--;

        if (j > nodeTable.size()) {
            throw new RuntimeException("j is bigger than table array size");
        }

        if (j == nodeTable.size()) {
            nodeTable.add(new Line());
        }

        Line line = nodeTable.get(j);
        line.add(i, h);
    }

    public int getH0(int j) {
        j--;

        if (j - 1 < 0) return 0;

        int h = nodeTable.get(j - 1).getFirstH();
        if (h == 0) {
            throw new RuntimeException("h0 is 0");
        }
        return h;
    }

    public int getH1(int j) {
        j--;

        if (nodeTable.size() == j) {
            return 0;
        }
        return nodeTable.get(j).getFirstH();
    }

    public List<PairRange> output() {
        List<PairRange> res = new LinkedList<>();

        PairRange tmp = null;
        for (Line line : nodeTable) {
            int i = line.getLastI();
            int h = line.getH(i);

            if (tmp == null) {
                tmp = new PairRange(i, h);
            } else {
                boolean success = tmp.merge(i, h);
                if (!success) {
                    res.add(tmp);
                    tmp = new PairRange(i, h);
                }
            }
        }

        if (tmp != null) res.add(tmp);

        return res;
    }

    private class Line {

        private TreeMap<Integer, Integer> nodes = new TreeMap<>();

        private void add(int i, int h) {
            nodes.put(i, h);
        }

        public int getFirstH() {
            Entry<Integer, Integer> entry = nodes.firstEntry();
            if (entry == null) return 0;

            return nodes.firstEntry().getValue().intValue();
        }

        public int getLastI() {
            return nodes.lastKey().intValue();
        }

        public int getH(int i) {
            return nodes.get(i);
        }
    }
}
