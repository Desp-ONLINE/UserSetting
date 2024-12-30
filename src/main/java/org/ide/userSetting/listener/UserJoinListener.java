package org.ide.userSetting.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;
import org.ide.userSetting.database.MongoDBRepository;
import org.ide.userSetting.object.UserInfo;

import java.util.UUID;

public class UserJoinListener implements Listener {
    private final MongoDBRepository repository;

    public UserJoinListener(MongoDBRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerUniqueId = player.getUniqueId();
        String playerName = player.getName();

        UserInfo userInfo = repository.getPlayerInfo(playerUniqueId);

        if (userInfo == null) {
            userInfo = new UserInfo(playerUniqueId, playerName);
            repository.insertPlayerInfo(userInfo);
        } else if (!userInfo.getName().equals(playerName)) {
            repository.updateNickname(playerUniqueId, playerName);
        }
    }
}