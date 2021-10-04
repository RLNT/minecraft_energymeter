package dev.rlnt.energymeter.network;

import static dev.rlnt.energymeter.core.Constants.NETWORK_ID;

import dev.rlnt.energymeter.util.TextUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {

    private static final ResourceLocation ID = TextUtils.getRL(NETWORK_ID);
    private static final String PROTOCOL = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
        ID,
        () -> PROTOCOL,
        PROTOCOL::equals,
        PROTOCOL::equals
    );

    static {
        int id = -1;

        CHANNEL
            .messageBuilder(ClientSyncPacket.class, ++id, NetworkDirection.PLAY_TO_CLIENT)
            .decoder(ClientSyncPacket::decode)
            .encoder(ClientSyncPacket::encode)
            .consumer(ClientSyncPacket::handle)
            .add();

        CHANNEL
            .messageBuilder(IOUpdatePacket.class, ++id, NetworkDirection.PLAY_TO_SERVER)
            .decoder(IOUpdatePacket::decode)
            .encoder(IOUpdatePacket::encode)
            .consumer(IOUpdatePacket::handle)
            .add();

        CHANNEL
            .messageBuilder(SettingUpdatePacket.class, ++id, NetworkDirection.PLAY_TO_SERVER)
            .decoder(SettingUpdatePacket::decode)
            .encoder(SettingUpdatePacket::encode)
            .consumer(SettingUpdatePacket::handle)
            .add();
    }

    private PacketHandler() {
        throw new IllegalStateException("Utility class");
        // TODO: remove
    }

    public static void init() {
        // utility method to initialize the packet handler
    }

    public static class SyncFlags {

        public static final int SIDE_CONFIG = (1);
        public static final int TRANSFER_RATE = (1 << 1);
        public static final int STATUS = (1 << 2);
        public static final int NUMBER_MODE = (1 << 3);
        public static final int MODE = (1 << 4);
        public static final int ALL = SIDE_CONFIG | TRANSFER_RATE | STATUS | NUMBER_MODE | MODE;

        private SyncFlags() {}
    }
}
