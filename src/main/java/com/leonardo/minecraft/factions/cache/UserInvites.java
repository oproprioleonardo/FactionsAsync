package com.leonardo.minecraft.factions.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.google.inject.Singleton;
import com.leonardo.minecraft.factions.UserInvite;
import lombok.Getter;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Singleton
@Getter
public class UserInvites {

    private final Cache<Integer, UserInvite> cache =
            Caffeine.newBuilder()
                    .expireAfterWrite(900, TimeUnit.SECONDS)
                    .removalListener(
                            (Integer id, UserInvite userInvite, RemovalCause removalCause) -> {

                            })
                    .maximumSize(200)
                    .build();

    public void register(UserInvite userInvite) {
        this.cache.put(userInvite.getId(), userInvite);
    }

    public void remove(int id) {
        this.cache.invalidate(id);
    }

    public UserInvite get(int id) {
        return this.cache.getIfPresent(id);
    }

    public Set<UserInvite> getAll(String username) {
        return this.cache.asMap().values().stream().filter(o -> o.getUsername().equalsIgnoreCase(username)).collect(
                Collectors.toSet());
    }

}
