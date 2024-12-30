package org.ide.userSetting.cache;

import org.ide.userSetting.object.UserInfo;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserCacheManger {
    private static UserCacheManger instance;
    private final Map<UUID, UserInfo> cache;

    private UserCacheManger() {
        cache = new ConcurrentHashMap<>();
    }

    public static UserCacheManger getInstance() {
        if (instance == null) {
            instance = new UserCacheManger();
        }

        return instance;
    }

    public UserInfo get(final UUID uuid) {
        return cache.get(uuid);
    }

    public void put(final UUID uuid, final UserInfo value) {
        cache.put(uuid, value);
    }

    public void remove(final UUID uuid) {
        cache.remove(uuid);
    }
}
