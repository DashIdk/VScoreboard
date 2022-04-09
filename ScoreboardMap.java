package me.dash.vscoreboard;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Map;

public class ScoreboardMap {
    private Map<Integer, String> lines = Maps.newConcurrentMap();
    public Map<Integer, String> getMap() {
        Map<Integer, String> map = Maps.newConcurrentMap();
        int k = lines.size() - 1;
        for(int i : lines.keySet()) map.put(k--, lines.get(i));
        return ImmutableMap.copyOf(map);
    }

    public void add(String line) {
        lines.put(lines.size(), line);
    }

    public boolean contains(String line) {
        return lines.containsValue(line);
    }
}
