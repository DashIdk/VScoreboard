package me.dash.vscoreboard;

import org.bukkit.entity.Player;

public interface VScoreboardAdapter {
    public String getTitle(Player p);

    public ScoreboardMap getLines(Player p);
}
