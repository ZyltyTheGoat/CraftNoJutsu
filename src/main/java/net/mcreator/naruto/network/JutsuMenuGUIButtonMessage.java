package net.mcreator.naruto.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.mcreator.naruto.procedures.GUIJutsuMenuButtonWindReleaseSelectProcedure;
import net.mcreator.naruto.procedures.GUIJutsuMenuButtonWaterReleaseSelectProcedure;
import net.mcreator.naruto.procedures.GUIJutsuMenuButtonLightningReleaseSelectProcedure;
import net.mcreator.naruto.procedures.GUIJutsuMenuButtonKekkeiGenkaiReleaseSelectProcedure;
import net.mcreator.naruto.procedures.GUIJutsuMenuButtonFireReleaseSelectProcedure;
import net.mcreator.naruto.procedures.GUIJutsuMenuButtonEarthReleaseSelectProcedure;
import net.mcreator.naruto.NarutoMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public record JutsuMenuGUIButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {

	public static final Type<JutsuMenuGUIButtonMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(NarutoMod.MODID, "jutsu_menu_gui_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, JutsuMenuGUIButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, JutsuMenuGUIButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new JutsuMenuGUIButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));
	@Override
	public Type<JutsuMenuGUIButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final JutsuMenuGUIButtonMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> handleButtonAction(context.player(), message.buttonID, message.x, message.y, message.z)).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			GUIJutsuMenuButtonFireReleaseSelectProcedure.execute(entity);
		}
		if (buttonID == 1) {

			GUIJutsuMenuButtonWaterReleaseSelectProcedure.execute(entity);
		}
		if (buttonID == 2) {

			GUIJutsuMenuButtonEarthReleaseSelectProcedure.execute(entity);
		}
		if (buttonID == 3) {

			GUIJutsuMenuButtonWindReleaseSelectProcedure.execute(entity);
		}
		if (buttonID == 4) {

			GUIJutsuMenuButtonLightningReleaseSelectProcedure.execute(entity);
		}
		if (buttonID == 5) {

			GUIJutsuMenuButtonKekkeiGenkaiReleaseSelectProcedure.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		NarutoMod.addNetworkMessage(JutsuMenuGUIButtonMessage.TYPE, JutsuMenuGUIButtonMessage.STREAM_CODEC, JutsuMenuGUIButtonMessage::handleData);
	}
}