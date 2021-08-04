package stormedpanda.simplyjetpacks.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;
import stormedpanda.simplyjetpacks.SimplyJetpacks;
import stormedpanda.simplyjetpacks.network.packets.*;

public class NetworkHandler {

    public static SimpleChannel CHANNEL_INSTANCE;
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        CHANNEL_INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(SimplyJetpacks.MODID, "simplyjetpacks"), () -> "1.0", s -> true, s -> true);

        CHANNEL_INSTANCE.messageBuilder(PacketToggleEngine.class, nextID())
                .encoder(PacketToggleEngine::toBytes)
                .decoder(PacketToggleEngine::new)
                .consumer(PacketToggleEngine::handle)
                .add();
        CHANNEL_INSTANCE.messageBuilder(PacketToggleHover.class, nextID())
                .encoder(PacketToggleHover::toBytes)
                .decoder(PacketToggleHover::new)
                .consumer(PacketToggleHover::handle)
                .add();

        CHANNEL_INSTANCE.messageBuilder(PacketToggleEHover.class, nextID())
                .encoder(PacketToggleEHover::toBytes)
                .decoder(PacketToggleEHover::new)
                .consumer(PacketToggleEHover::handle)
                .add();

        CHANNEL_INSTANCE.messageBuilder(PacketToggleCharger.class, nextID())
                .encoder(PacketToggleCharger::toBytes)
                .decoder(PacketToggleCharger::new)
                .consumer(PacketToggleCharger::handle)
                .add();

        CHANNEL_INSTANCE.messageBuilder(PacketUpdateInput.class, nextID())
                .encoder(PacketUpdateInput::toBytes)
                .decoder(PacketUpdateInput::fromBytes)
                .consumer(PacketUpdateInput::handle)
                .add();
    }

    public static void sendToClient(Object packet, ServerPlayer player) {
        CHANNEL_INSTANCE.sendTo(packet, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToServer(Object packet) {
        CHANNEL_INSTANCE.sendToServer(packet);
    }
}
