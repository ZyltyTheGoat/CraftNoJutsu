package net.mcreator.naruto.network;

import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.mcreator.naruto.NarutoMod;

@EventBusSubscriber(modid = NarutoMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class NarutoModMessages {
	@SubscribeEvent
	public static void register(RegisterPayloadHandlersEvent event) {
		PayloadRegistrar registrar = event.registrar("1");
		registrar.playToServer(SetActiveJutsuPacket.TYPE, SetActiveJutsuPacket.STREAM_CODEC, SetActiveJutsuPacket::handle);
		registrar.playToServer(SetToggleJutsuPacket.TYPE, SetToggleJutsuPacket.STREAM_CODEC, SetToggleJutsuPacket::handle);
		registrar.playToServer(SetFavouriteJutsuPacket.TYPE, SetFavouriteJutsuPacket.STREAM_CODEC, SetFavouriteJutsuPacket::handle);
	}
}