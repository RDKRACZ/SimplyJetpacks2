package stormedpanda.simplyjetpacks.crafting;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import stormedpanda.simplyjetpacks.client.particle.JetpackParticleType;
import stormedpanda.simplyjetpacks.items.JetpackItem;
import stormedpanda.simplyjetpacks.items.JetpackType;
import stormedpanda.simplyjetpacks.util.NBTHelper;

public class EnergyTransferHandler {

    @SubscribeEvent
    public void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        ItemStack craftedStack = event.getCrafting();
        Item craftedItem = event.getCrafting().getItem();
        int storedEnergy = 0;
        CompoundTag tags = null;

        if (craftedItem instanceof JetpackItem) {
            for (int i = 0; i < event.getInventory().getContainerSize(); i++) {
                ItemStack input = event.getInventory().getItem(i);
                if (!(input.getItem() instanceof JetpackItem)) { continue; }
                if (input.getItem() instanceof JetpackItem) {
                    JetpackType inputJetpack = ((JetpackItem) input.getItem()).getType();
                    tags = NBTHelper.getTagCompound(input).copy();
                    storedEnergy = NBTHelper.getInt(input, "Energy");
                    craftedStack.setTag(tags);
                    int energyToTransfer = Math.min(storedEnergy, ((JetpackItem) craftedItem).getCapacity());
                    JetpackParticleType particleType = inputJetpack.getParticleType(input);
                    ((JetpackItem) craftedItem).setParticleType(craftedStack, particleType);
                    break;
                }
            }
        }
    }
}
