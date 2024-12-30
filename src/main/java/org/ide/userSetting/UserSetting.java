package org.ide.userSetting;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.ide.userSetting.command.HUDToggleCommand;
import org.ide.userSetting.repository.UserRepository;
import org.ide.userSetting.listener.UserExitListener;
import org.ide.userSetting.listener.UserJoinListener;

import java.util.Objects;

@Getter
public final class UserSetting extends JavaPlugin {
    private UserRepository userRepository;
    private static UserSetting instance;

    public static UserSetting getInstance() {
        if (instance == null) {
            instance = new UserSetting();
        }

        return instance;
    }

    @Override
    public void onEnable() {
        userRepository = new UserRepository();
        instance = this;

        getServer().getPluginManager().registerEvents(new UserJoinListener(), this);
        getServer().getPluginManager().registerEvents(new UserExitListener(), this);
        Objects.requireNonNull(this.getCommand("hud")).setExecutor(new HUDToggleCommand());
    }

    @Override
    public void onDisable() {
    }
}