package org.ide.userSetting.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.ide.userSetting.cache.UserCacheManger;
import org.ide.userSetting.object.UserInfo;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class HUDToggleCommand implements CommandExecutor {
    private final UserCacheManger cacheManger = UserCacheManger.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            UUID uuid = player.getUniqueId();

            UserInfo userInfo = cacheManger.get(uuid);
            userInfo.setHud(); // hud 상태 토글

            String action = userInfo.isHud() ? "add" : "remove";
            String hudType = userInfo.isHud() ? "bar" : "slot";

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "betterhud hud "
                    + action + " " + player.getName() + " " + hudType);

            Bukkit.getLogger().info((userInfo.getName()) + "'s HUD: " + hudType);

            return true;
        } else {
            return false;
        }
    }
}