package me.dash.vscoreboard;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Map;

public class ScoreboardMap {
    
    public ScoreboardMap() {
        this(15, true);
    }
    
    public ScoreboardMap(int start, boolean descending) {
        this.descending = descending;
        this.current = start;
    }
    
    private boolean descending;
    private int current;
    
    private Map<Integer, String> lines = Maps.newConcurrentMap();
    
    public Map<Integer, String> getMap() {
        Map<Integer, String> map = Maps.newConcurrentMap();
        int k = lines.size() - 1;
        for(int i : lines.keySet()) map.put(k--, lines.get(i));
        return ImmutableMap.copyOf(map);
    }

    public void add(String line) {
        if(descending) current--;
        else current++;
        lines.put(current, line);
    }

    public boolean contains(String line) {
        return lines.containsValue(line);
    }
}
