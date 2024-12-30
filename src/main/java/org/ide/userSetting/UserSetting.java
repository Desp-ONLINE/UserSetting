package org.ide.userSetting;

import org.bukkit.plugin.java.JavaPlugin;
import org.ide.userSetting.database.MongoDBRepository;
import org.ide.userSetting.listener.UserJoinListener;

public final class UserSetting extends JavaPlugin {
    private MongoDBRepository dbRepository;

    @Override
    public void onEnable() {
        // Plugin startup logic
        dbRepository = new MongoDBRepository();
        getServer().getPluginManager().registerEvents(new UserJoinListener(dbRepository), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (dbRepository != null) {
            dbRepository.close();
        }
    }
}