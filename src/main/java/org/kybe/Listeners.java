package org.kybe;

import net.minecraft.client.Minecraft;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.events.client.input.EventMouse;
import org.rusherhack.client.api.feature.module.IModule;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.core.event.stage.Stage;
import org.rusherhack.core.event.subscribe.Subscribe;
import org.rusherhack.core.setting.BooleanSetting;

import java.util.EventListener;
import java.util.Optional;

public class Listeners implements EventListener {
    String prefix;

    public Listeners() {
        Optional<IModule> optionalModule = RusherHackAPI.getModuleManager().getFeature("Open Folder");
        if (optionalModule.isPresent()) {
            prefix = optionalModule.get().getSetting("Prefix").getValue().toString();
        } else {
            ChatUtils.print("Module 'Open Folder' not found");
        }
    }

    @Subscribe(stage = Stage.ALL)
    private void onMouseInput(EventMouse.Key ignoredEvent) {
        for (String module : OpenFolder.modules) {
            handle(module);
        }
    }

    private void handle(String moduleName) {
        Optional<IModule> optionalModule = RusherHackAPI.getModuleManager().getFeature(moduleName);
        if (optionalModule.isPresent()) {
            IModule module = optionalModule.get();
            BooleanSetting setting = (BooleanSetting) module.getSetting("Open this Folder");
            if (setting.getValue()) {
                if (Minecraft.getInstance().player == null) return;
                Minecraft.getInstance().player.connection.sendChat(prefix + "openfolder " + moduleName);
                setting.setValue(false);
            }
        } else {
            ChatUtils.print("Module not found: " + moduleName);
        }
    }
}
