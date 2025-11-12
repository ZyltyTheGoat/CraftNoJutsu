/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.naruto.init;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.naruto.client.gui.NatureReleasesScreen;
import net.mcreator.naruto.client.gui.KekkeiGenkaiWheelScreen;
import net.mcreator.naruto.client.gui.JutsuWheelScreenScreen;
import net.mcreator.naruto.client.gui.DojutsuWheelScreen;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class NarutoModScreens {
	@SubscribeEvent
	public static void clientLoad(RegisterMenuScreensEvent event) {
		event.register(NarutoModMenus.JUTSU_WHEEL_SCREEN.get(), JutsuWheelScreenScreen::new);
		event.register(NarutoModMenus.NATURE_RELEASES.get(), NatureReleasesScreen::new);
		event.register(NarutoModMenus.DOJUTSU_WHEEL.get(), DojutsuWheelScreen::new);
		event.register(NarutoModMenus.KEKKEI_GENKAI_WHEEL.get(), KekkeiGenkaiWheelScreen::new);
	}

	public interface ScreenAccessor {
		void updateMenuState(int elementType, String name, Object elementState);
	}
}