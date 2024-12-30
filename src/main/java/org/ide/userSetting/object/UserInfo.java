package org.ide.userSetting.object;

import java.util.UUID;

public class UserInfo {
    private final UUID uuid;
    private String name;
    private boolean hud;

    public UserInfo(UUID uuid, String name, boolean hud) {
        this.uuid = uuid;
        this.name = name;
        this.hud = hud;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHud() {
        return hud;
    }

    public void setHud() {
        this.hud = !this.hud;
    }
}