package org.kybe;


import net.minecraft.client.Minecraft;
import org.rusherhack.client.api.events.client.input.EventMouse;
import org.rusherhack.client.api.feature.module.ModuleCategory;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.core.event.stage.Stage;
import org.rusherhack.core.event.subscribe.Subscribe;
import org.rusherhack.core.setting.BooleanSetting;
import org.rusherhack.core.setting.StringSetting;

public class Module extends org.rusherhack.client.api.feature.module.Module {
    public BooleanSetting rusherhack = new BooleanSetting("Open rusherhack Folder", "Open Folder", false);
    public BooleanSetting config = new BooleanSetting("Open config Folder", "Opens config Folder", false);
    public BooleanSetting binds = new BooleanSetting("Open binds Folder", "Opens binds Folder", false);
    public BooleanSetting modules = new BooleanSetting("Open modules Folder", "Opens modules Folder", false);
    public BooleanSetting themes = new BooleanSetting("Open themes Folder", "Opens themes Folder", false);
    public BooleanSetting windows = new BooleanSetting("Open windows Folder", "Opens windows Folder", false);
    public StringSetting prefix = new StringSetting("Prefix", "Prefix for the command", "*");

    public Module() {
        super("Open Folder", "Open Folder", ModuleCategory.MISC);

        this.registerSettings(
                rusherhack,
                config,
                binds,
                modules,
                themes,
                windows,
                prefix
        );
    }

    @Subscribe(stage = Stage.ALL)
    private void onMouseInput(EventMouse.Key ignoredEvent) {
        try {
            handle(rusherhack, "");
            handle(config, "config");
            handle(binds, "binds");
            handle(modules, "modules");
            handle(themes, "themes");
            handle(windows, "windows");
        } catch (Exception e) {
            ChatUtils.print("Error: " + e.getMessage());
        }
    }
    private void handle(BooleanSetting setting, String folder) {
        if (setting.getValue()) {
            if (Minecraft.getInstance().player == null) return;
            Minecraft.getInstance().player.connection.sendChat(prefix.getValue() + "openfolder " + folder);
            setting.setValue(false);
        }
    }
}
