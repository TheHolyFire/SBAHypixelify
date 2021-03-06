package pronze.hypixelify.game;

import com.google.common.base.Strings;
import pronze.hypixelify.SBAHypixelify;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.statistics.PlayerStatistic;


public class PlayerWrapper implements pronze.hypixelify.api.wrapper.PlayerWrapper {

    private final String name;
    private final Player instance;
    private final PlayerStatistic statistic;
    private int shout;
    private boolean shouted = false;

    public PlayerWrapper(Player player) {
        name = player.getDisplayName();
        instance = player;
        shout = SBAHypixelify.getConfigurator().config.getInt("shout.time-out", 60);
        statistic = Main.getPlayerStatisticsManager().getStatistic(instance);
    }

    @Override
    public int getShoutTimeOut() {
        return shout;
    }

    @Override
    public void shout() {
        if (!shouted) {
            shouted = true;
            new BukkitRunnable() {
                @Override
                public void run() {
                    shout--;
                    if (shout == 0) {
                        shouted = false;
                        shout = SBAHypixelify.getConfigurator().config.getInt("shout.time-out", 60);
                        this.cancel();
                    }
                }
            }.runTaskTimer(SBAHypixelify.getInstance(), 0L, 20L);
        }
    }

    @Override
    public boolean canShout() {
        return !shouted;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getKills() {
        return statistic.getKills();
    }

    @Override
    public int getWins() {
        return statistic.getWins();
    }

    @Override
    public int getBedDestroys() {
        return statistic.getDestroyedBeds();
    }

    @Override
    public int getDeaths() {
        return statistic.getDeaths();
    }

    @Override
    public int getXP() {
        try {
            return statistic.getScore();
        } catch (Exception e) {
            return 1;
        }
    }

    @Override
    public int getLevel() {
        int xp = getXP();

        if (xp < 50)
            return 1;

        return getXP() / 500;
    }

    @Override
    public String getProgress() {
        String p = "§b{p}§7/§a500";
        int progress;
        try {
            progress = getXP() - (getLevel() * 500);
        } catch (Exception e) {
            progress = 1;
        }
        return p
                .replace("{p}", String.valueOf(progress));
    }

    @Override
    public int getIntegerProgress() {
        int progress;
        try {
            progress = getXP() - (getLevel() * 500);
        } catch (Exception e) {
            progress = 1;
        }
        return progress;
    }

    public String getStringProgress() {
        String progress = null;
        try {
            int p = getIntegerProgress();
            if (p < 0)
                progress = "§b0§7/§a500";
            else
                progress = getProgress();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (progress == null) {
            return "§b0§7/§a500";
        }
        return progress;
    }

    @Override
    public String getCompletedBoxes() {
        int progress;
        try {
            progress = (getXP() - (getLevel() * 500)) / 5;
        } catch (Exception e) {
            progress = 1;
        }
        if (progress < 1)
            progress = 1;
        char i;
        i = String.valueOf(Math.abs((long) progress)).charAt(0);
        if (progress < 10) {
            i = '1';
        }
        return "§7[§b" + Strings.repeat("■", Integer.parseInt(String.valueOf(i)))
                + "§7" + Strings.repeat("■", 10 - Integer.parseInt(String.valueOf(i))) + "]";
    }

    @Override
    public void sendMessage(String message) {
        if (!instance.isOnline()) {
            return;
        }

        instance.sendMessage(message);
    }

    @Override
    public double getKD() {
        return Main.getPlayerStatisticsManager().getStatistic(instance).getKD();
    }

}