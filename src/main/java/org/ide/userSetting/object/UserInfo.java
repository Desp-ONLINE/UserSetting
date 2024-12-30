package org.ide.userSetting.object;

import java.util.UUID;

public class UserInfo {
    private final UUID uuid;
    private final String name;

    public UserInfo(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }
}