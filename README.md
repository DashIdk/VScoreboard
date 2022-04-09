# VScoreboard
A Scoreboard API for the BukkitAPI that doesn't flicker and supports 48 characters per line.

## Examples

### Setup
```java
public class ExamplePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        VScoreboardManager.start(this);
        VScoreboardManager.setAdapter(new ExampleAdapter());
    }
}
```
### Adapter
```java
public class ExampleAdapter implements VScoreboardAdapter {
    
    @Override
    public String getTitle(Player p) {
        return "Title [" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + "]";
    }
    
    @Override
    public ScoreboardMap getLines(Player p) {
        ScoreboardMap map = new ScoreboardMap();
        map.add("&7&m---------------");
        map.add("&6&lName&7:&f " + p.getName());
        map.add("&6&lDisplay Name&7:&f " + p.getDisplayName());
        map.add("&c");
        map.add("&7&m---------------&c");
        return map;
    }
}
```

## Known issues
* Scoreboard may not show lines while there is only one player online
