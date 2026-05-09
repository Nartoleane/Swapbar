package com.zandgall.swapbar;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.resources.Identifier;

public class Swapbar implements ClientModInitializer {
	public static KeyMapping switchKey;

	private static Inventory inventory;

	@Override
	public void onInitializeClient() {

		KeyMapping.Category CATEGORY = KeyMapping.Category.register(
			Identifier.fromNamespaceAndPath("swapbar", "category")
		);

		switchKey = KeyMappingHelper.registerKeyMapping(
				new KeyMapping("swapbar.swap", InputConstants.Type.KEYSYM, 82, CATEGORY)
			);
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			LocalPlayer player = Minecraft.getInstance().player;
			while (Swapbar.switchKey.consumeClick()) {
				if (player != null && player.getInventory() != null) {
					inventory = player.getInventory();
					for(int i = 0; i < 9; i++) {
						int top = i + 9;
						int mid = top + 9;
						int bot = mid + 9;
						if(InputConstants.isKeyDown(Minecraft.getInstance().getWindow(), InputConstants.KEY_LALT)) {
							Minecraft.getInstance().gameMode.handleContainerInput(player.containerMenu.containerId, bot, i, ContainerInput.SWAP, player);

							Minecraft.getInstance().gameMode.handleContainerInput(player.containerMenu.containerId, mid, i, ContainerInput.SWAP, player);
							Minecraft.getInstance().gameMode.handleContainerInput(player.containerMenu.containerId, top, i, ContainerInput.SWAP, player);
						} else {
							Minecraft.getInstance().gameMode.handleContainerInput(player.containerMenu.containerId, top, i, ContainerInput.SWAP, player);
							Minecraft.getInstance().gameMode.handleContainerInput(player.containerMenu.containerId, mid, i, ContainerInput.SWAP, player);
							Minecraft.getInstance().gameMode.handleContainerInput(player.containerMenu.containerId, bot, i, ContainerInput.SWAP, player);
						}
					}
				}
			}
		});
	}
}
