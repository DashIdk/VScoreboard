package me.dash.vscoreboard;

import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VScoreboardManager {
    private static boolean started = false;
    private static VScoreboardAdapter adapter = null;
    private static Map<Player, VScoreboard> scoreboardMap = Maps.newConcurrentMap();

    public static void setAdapter(VScoreboardAdapter adapter) {
        if(adapter == null) VScoreboardManager.adapter = adapter;
    }

    public static void start(JavaPlugin plugin) {
        if(started) return;
        started = true;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, VScoreboardManager::update, 0L, 5L);
        Bukkit.getPluginManager().registerEvents(new Listener() { @EventHandler public void onPlayerJoin(PlayerJoinEvent e) { e.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard()); } }, plugin);
    }

    private static void update() {
        if(adapter == null) return;
        for(Player p : Bukkit.getOnlinePlayers()) {
            VScoreboard scoreboard = scoreboardMap.get(p);
            if(scoreboard == null) {
                scoreboard = new VScoreboard(p);
                scoreboardMap.put(p, scoreboard);
            }
            scoreboard.update(adapter);
        }
    }
}
