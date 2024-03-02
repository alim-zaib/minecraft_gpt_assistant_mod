package net.alimzaib.GPTAssistantMod.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import java.util.HashMap;
import java.util.Map;

public class InventoryUtil {

    public static String getInventoryStateAsText(Player player) {
        StringBuilder inventoryState = new StringBuilder();
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (!stack.isEmpty()) {
                String itemName = stack.getItem().getDescriptionId();
                int quantity = stack.getCount();
                inventoryState.append(itemName).append(": ").append(quantity).append("\n");
            }
        }
        return inventoryState.toString();
    }
}