package org.kybe;

import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.feature.module.IModule;
import org.rusherhack.client.api.plugin.Plugin;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.core.setting.BooleanSetting;

import java.util.EventListener;
import java.util.Optional;

/**
 * Open Folder Plugin
 *
 * Author: kybe236
 */
public class OpenFolder extends Plugin {

	private EventListener inputListener;
	public static final String[] modules = {"antispam", "autonametag", "autotrader", "basefinder", "extrachest", "extratooltips", "fastbreak", "fastplace", "fastuse", "greeter", "hitboxignore", "hotbarreplenish", "inventorycleaner", "newchunks", "nuker", "scaffold", "search", "spammer", "windows", "xray"};

	@Override
	public void onLoad() {
		try {
			for (String module : modules) {
				try {
					register(module);
				} catch (Exception e) {
					ChatUtils.print("Error on: " + module);
				}
			}
			RusherHackAPI.getModuleManager().registerFeature(new org.kybe.Module());
			if (inputListener == null) {
				inputListener = new Listeners();
				RusherHackAPI.getEventBus().subscribe(inputListener);
			}
		} catch (Exception e) {
			ChatUtils.print("Error: " + e.getMessage());
		}
	}

	@Override
	public void onUnload() {
		if (inputListener != null) {
			RusherHackAPI.getEventBus().unsubscribe(inputListener);
			inputListener = null;
		}
	}

	private void register(String name) {
		Optional<IModule> optionalModule = RusherHackAPI.getModuleManager().getFeature(name);
		if (optionalModule.isPresent()) {
			IModule module = optionalModule.get();
			module.registerSettings(new BooleanSetting("Open this Folder", "Opens this Folder", false));
		} else {
			ChatUtils.print("Module not found: " + name);
		}
	}
}
