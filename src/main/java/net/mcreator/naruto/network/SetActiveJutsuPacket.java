package net.mcreator.naruto.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.FriendlyByteBuf;

import net.mcreator.naruto.NarutoMod;
public record SetActiveJutsuPacket(String jutsuId) implements CustomPacketPayload {
    
    public static final Type<SetActiveJutsuPacket> TYPE = 
        new Type<>(ResourceLocation.fromNamespaceAndPath(NarutoMod.MODID, "set_active_jutsu"));
    
    public static final StreamCodec<FriendlyByteBuf, SetActiveJutsuPacket> STREAM_CODEC = 
        StreamCodec.composite(
            net.minecraft.network.codec.ByteBufCodecs.STRING_UTF8,
            SetActiveJutsuPacket::jutsuId,
            SetActiveJutsuPacket::new
        );
    @Override
    public Type<SetActiveJutsuPacket> type() {
        return TYPE;
    }
    public static void handle(SetActiveJutsuPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer serverPlayer) {
                // Get player variables
                NarutoModVariables.PlayerVariables playerVars = 
                    serverPlayer.getData(NarutoModVariables.PLAYER_VARIABLES);
                
                // Optional: Validate that player has unlocked this jutsu
                if (playerVars.unlockedJutsu != null && !playerVars.unlockedJutsu.isEmpty()) {
                    String[] unlockedJutsus = playerVars.unlockedJutsu.split(",");
                    boolean hasJutsu = false;
                    for (String jutsu : unlockedJutsus) {
                        if (jutsu.trim().equals(packet.jutsuId)) {
                            hasJutsu = true;
                            break;
                        }
                    }
                    
                    if (!hasJutsu) {
                        return; // Player doesn't have this jutsu, reject
                    }
                }
                
                // Set the active jutsu
                playerVars.activeJutsu = packet.jutsuId;
                
                // Sync to client (assumes your PlayerVariables has a sync method)
                playerVars.syncPlayerVariables(serverPlayer);
            }
        });
    }
}
