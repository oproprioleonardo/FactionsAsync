package com.leonardo.minecraft.factions.di;

import co.aikar.commands.BukkitCommandManager;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.leonardo.minecraft.factions.MinecraftFactions;
import com.leonardo.minecraft.factions.repositories.FactionRepository;
import com.leonardo.minecraft.factions.repositories.MUserRepository;
import com.leonardo.minecraft.factions.repositories.impl.FactionRepositoryImpl;
import com.leonardo.minecraft.factions.repositories.impl.MUserRepositoryImpl;
import com.leonardo.minecraft.factions.services.FactionService;
import com.leonardo.minecraft.factions.services.MUserService;
import com.leonardo.minecraft.factions.services.impl.FactionServiceImpl;
import com.leonardo.minecraft.factions.services.impl.MUserServiceImpl;
import io.vertx.mutiny.mysqlclient.MySQLPool;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.sqlclient.PoolOptions;
import lombok.AllArgsConstructor;

@AllArgsConstructor(staticName = "of")
public class MinecraftFactionsModule extends AbstractModule {

    private final MinecraftFactions core;

    @Override
    protected void configure() {
        bind(MinecraftFactions.class).toInstance(this.core);
        bind(FactionRepository.class).to(FactionRepositoryImpl.class);
        bind(MUserRepository.class).to(MUserRepositoryImpl.class);
        bind(FactionService.class).to(FactionServiceImpl.class);
        bind(MUserService.class).to(MUserServiceImpl.class);
        bind(BukkitCommandManager.class).toInstance(new BukkitCommandManager(this.core));
    }

    @Provides
    @Singleton
    private MySQLConnectOptions providesMySQLConnectOptions() {
        return new MySQLConnectOptions().setPort(3306).setHost("127.0.0.1")
                                        .setDatabase("main").setUser("root").setPassword("manager");
    }

    @Provides
    @Singleton
    private PoolOptions providesPoolOptions() {
        return new PoolOptions().setConnectionTimeout(20000).setIdleTimeout(20000)
                                .setMaxSize(30);
    }

    @Provides
    @Singleton
    private MySQLPool providesMySQLPool(PoolOptions poolOptions, MySQLConnectOptions connectionOptions) {
        return MySQLPool.pool(connectionOptions, poolOptions);
    }

}
