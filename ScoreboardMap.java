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
        this.current = start + (descending ? 1 : -1);
    }

    private boolean descending;
    private int current;

    private Map<Integer, String> lines = Maps.newConcurrentMap();

    public Map<Integer, String> getMap() {
        return ImmutableMap.copyOf(lines);
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
