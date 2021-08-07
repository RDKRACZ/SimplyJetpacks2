package stormedpanda.simplyjetpacks.handlers;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import stormedpanda.simplyjetpacks.config.SimplyJetpacksConfig;
import stormedpanda.simplyjetpacks.integration.IntegrationList;
import stormedpanda.simplyjetpacks.util.AdvancementUtil;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class SyncHandler {

    private static final Map<Player, Boolean> HOLDING_UP = new HashMap<>();
    private static final Map<Player, Boolean> HOLDING_DOWN = new HashMap<>();
    private static final Map<Player, Boolean> HOLDING_FORWARDS = new HashMap<>();
    private static final Map<Player, Boolean> HOLDING_BACKWARDS = new HashMap<>();
    private static final Map<Player, Boolean> HOLDING_LEFT = new HashMap<>();
    private static final Map<Player, Boolean> HOLDING_RIGHT = new HashMap<>();

    public static boolean isHoldingUp(Player player) {
        return HOLDING_UP.containsKey(player) && HOLDING_UP.get(player);
    }

    public static boolean isHoldingDown(Player player) {
        return HOLDING_DOWN.containsKey(player) && HOLDING_DOWN.get(player);
    }

    public static boolean isHoldingForwards(Player player) {
        return HOLDING_FORWARDS.containsKey(player) && HOLDING_FORWARDS.get(player);
    }

    public static boolean isHoldingBackwards(Player player) {
        return HOLDING_BACKWARDS.containsKey(player) && HOLDING_BACKWARDS.get(player);
    }

    public static boolean isHoldingLeft(Player player) {
        return HOLDING_LEFT.containsKey(player) && HOLDING_LEFT.get(player);
    }

    public static boolean isHoldingRight(Player player) {
        return HOLDING_RIGHT.containsKey(player) && HOLDING_RIGHT.get(player);
    }

    public static void update(Player player, boolean up, boolean down, boolean forwards, boolean backwards, boolean left, boolean right) {
        HOLDING_UP.put(player, up);
        HOLDING_DOWN.put(player, down);
        HOLDING_FORWARDS.put(player, forwards);
        HOLDING_BACKWARDS.put(player, backwards);
        HOLDING_LEFT.put(player, left);
        HOLDING_RIGHT.put(player, right);
    }

    public static void clear() {
        HOLDING_UP.clear();
        HOLDING_FORWARDS.clear();
        HOLDING_DOWN.clear();
        HOLDING_BACKWARDS.clear();
        HOLDING_LEFT.clear();
        HOLDING_RIGHT.clear();
    }

    public static void remove(Player player) {
        HOLDING_UP.remove(player);
        HOLDING_FORWARDS.remove(player);
        HOLDING_DOWN.remove(player);
        HOLDING_BACKWARDS.remove(player);
        HOLDING_LEFT.remove(player);
        HOLDING_RIGHT.remove(player);
    }

    // This is here because it does not want to be lonely.
    public static void checkAdvancements(Player player) {
        AdvancementUtil.unlockAdvancement(player, "root");

        if (IntegrationList.integrateVanilla) {
            AdvancementUtil.unlockAdvancement(player, "vanilla/root_vanilla");
        }
        if (IntegrationList.integrateImmersiveEngineering) {
            AdvancementUtil.unlockAdvancement(player, "ie/root_ie");
        }
        if (IntegrationList.integrateMekanism) {
            AdvancementUtil.unlockAdvancement(player, "mek/root_mek");
        }
        if (IntegrationList.integrateThermalExpansion) {
            AdvancementUtil.unlockAdvancement(player, "thermal/root_thermal");
        }
    }

    @SubscribeEvent
    public void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        remove(event.getPlayer());
    }

    @SubscribeEvent
    public void onChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        remove(event.getPlayer());
    }

    @SubscribeEvent
    public void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (SimplyJetpacksConfig.COMMON.enableJoinAdvancements.get()) {
            checkAdvancements(event.getPlayer());
        }
    }
}