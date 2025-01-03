package org.ide.userSetting.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;
import org.ide.userSetting.UserSetting;
import org.ide.userSetting.cache.UserCacheManger;
import org.ide.userSetting.repository.UserRepository;
import org.ide.userSetting.object.UserInfo;

import java.util.UUID;

public class UserJoinListener implements Listener {
    private final UserRepository repository = UserSetting.getInstance().getUserRepository();
    private final UserCacheManger cacheManger = UserCacheManger.getInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String playerName = player.getName();

        UserInfo userInfo = repository.getPlayerInfo(uuid);

        if (userInfo == null) {
            userInfo = new UserInfo(uuid, playerName, true);
            cacheManger.put(uuid, userInfo);
        } else {
            cacheManger.put(uuid, userInfo);

            // 닉네임 변경 감지
            if (!playerName.equals(cacheManger.get(uuid).getName())) {
                cacheManger.get(uuid).setName(playerName);
            }
        }
    }
}