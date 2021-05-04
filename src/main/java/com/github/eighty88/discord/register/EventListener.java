package com.github.eighty88.discord.register;

import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class EventListener implements Listener {
    public String keyMessage = "あなたは認証されていません。\n以下のコマンドをDiscordで入力してください。\n!register %key%";

    public DiscordRegister discordRegister;

    public EventListener(DiscordRegister discordRegister) {
        this.discordRegister = discordRegister;
    }

    @EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent e) {
        if(!discordRegister.PlayerHash.containsKey(e.getUniqueId())) {
            String key;
            if(discordRegister.RegisterHash.containsKey(e.getUniqueId())) {
                key = discordRegister.RegisterHash.get(e.getUniqueId());
            } else {
                do {
                    key = RandomStringUtils.randomAlphanumeric(7);
                } while (discordRegister.RegisterHash.containsValue(key));
                discordRegister.RegisterHash.put(e.getUniqueId(), key);
            }
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,
                    keyMessage.replace("%key%", key));
        }
    }
}
