package me.dash.vscoreboard;

import com.google.common.collect.Maps;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.ChatColor;

import java.util.Map;
import java.util.Set;

public class VScoreboard {
    private Player player;
    private Scoreboard scoreboard;
    private Objective objective;

    private Map<Integer, String> displayedScores = Maps.newConcurrentMap();

    public VScoreboard(Player p) {
        this.player = p;
        this.scoreboard = p.getScoreboard();
        this.objective = scoreboard.registerNewObjective("vScore", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }
    
    private String color(String str) {
        if(str == null) return null;
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public void update(VScoreboardAdapter adapter) {
        String title = JColor.translateColorCodes(adapter.getTitle(player));
        if(!objective.getDisplayName().equals(title)) objective.setDisplayName(title);
        Map<Integer, String> lines = adapter.getLines(player).getMap();
        for(int i = min(lines.keySet(), displayedScores.keySet()); i < max(lines.keySet(), displayedScores.keySet()); i++) {
            String replaceLine = JColor.translateColorCodes(lines.get(i));
            String[] replaceScoreData = replaceLine == null ? null : separate(replaceLine);
            String replaceScoreName = replaceScoreData != null && replaceScoreData.length >= 1 ? replaceScoreData[0] : null;
            String currentScore = displayedScores.get(i);
            if(currentScore != null && !currentScore.equals(replaceScoreName)) {
                scoreboard.resetScores(currentScore);
                displayedScores.remove(i, currentScore);
            }
            if(replaceScoreData != null && !replaceScoreData[0].equals(currentScore)) {
                Team t = scoreboard.getTeam(replaceScoreData[0]);
                if(t == null) {
                    t = scoreboard.registerNewTeam(replaceScoreData[0]);
                    t.addEntry(replaceScoreData[0]);
                    t.setPrefix(replaceScoreData[1]);
                    t.setSuffix(replaceScoreData[2]);
                }
                objective.getScore(replaceScoreData[0]).setScore(i);
                displayedScores.put(i, replaceScoreData[0]);
                if(!t.getPrefix().equals(replaceScoreData[1]) || !t.getSuffix().equals(replaceScoreData[2])) {
                    t.setPrefix(replaceScoreData[1]);
                    t.setSuffix(replaceScoreData[2]);
                }
            }
        }
    }

    public int min(Set<Integer>... sets) {
        int i = 0;
        for(Set<Integer> ints : sets) for(int n : ints) if(n < i) i = n;
        return i;
    }

    public int max(Set<Integer>... sets) {
        int i = 0;
        for(Set<Integer> ints : sets) for(int n : ints) if(n > i) i = n;
        return i;
    }

    public String[] separate(String line) {
        String prefix = "";
        String score = "";
        String suffix = "";
        if(line.length() <= 16) score = line;
        else for(int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if(line.length() <= 32) {
                if(score.length() < 16 && !(i == 16 && c == ChatColor.COLOR_CHAR)) score += c;
                else if(suffix.length() < 16) suffix += c;
            } else {
                if(prefix.length() < 16 && !(i == 16 && c == ChatColor.COLOR_CHAR)) prefix += c;
                else if(score.length() < 16 && !(i == 32 && c == ChatColor.COLOR_CHAR)) score += c;
                else if (suffix.length() < 16) suffix += c;
            }
        }
        return new String[]{score, prefix, suffix};
    }
}
