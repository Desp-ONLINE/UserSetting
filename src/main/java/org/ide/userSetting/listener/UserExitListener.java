package org.ide.userSetting.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.entity.Player;
import org.ide.userSetting.UserSetting;
import org.ide.userSetting.cache.UserCacheManger;
import org.ide.userSetting.repository.UserRepository;
import org.ide.userSetting.object.UserInfo;

import java.util.UUID;

public class UserExitListener implements Listener {
    private final UserRepository repository = UserSetting.getInstance().getUserRepository();
    private final UserCacheManger cacheManger = UserCacheManger.getInstance();

    @EventHandler
    public void onUserExit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        UserInfo userInfo = cacheManger.get(uuid);

        if (repository.getPlayerInfo(uuid) != null) {
            repository.updateUserInfo(userInfo);
        } else {
          repository.saveUserInfo(userInfo);
        }

        cacheManger.remove(uuid);
    }
}