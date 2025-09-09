package net.mcreator.naruto.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.naruto.world.inventory.JutsuMenuGUIMenu;
import net.mcreator.naruto.network.JutsuMenuGUIButtonMessage;
import net.mcreator.naruto.init.NarutoModScreens;

import com.mojang.blaze3d.systems.RenderSystem;

public class JutsuMenuGUIScreen extends AbstractContainerScreen<JutsuMenuGUIMenu> implements NarutoModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	ImageButton imagebutton_jutsu_menu_buttons;
	ImageButton imagebutton_jutsu_menu_buttons1;
	ImageButton imagebutton_jutsu_menu_buttons2;
	ImageButton imagebutton_jutsu_menu_buttons3;
	ImageButton imagebutton_jutsu_menu_buttons4;
	ImageButton imagebutton_jutsu_menu_buttons5;

	public JutsuMenuGUIScreen(JutsuMenuGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 100;
		this.imageHeight = 210;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		menuStateUpdateActive = false;
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		guiGraphics.blit(ResourceLocation.parse("naruto:textures/screens/jutsu_menu_test.png"), this.leftPos + 0, this.topPos + -1, 0, 0, 100, 210, 100, 210);
		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
	}

	@Override
	public void init() {
		super.init();
		imagebutton_jutsu_menu_buttons = new ImageButton(this.leftPos + 2, this.topPos + 0, 16, 16,
				new WidgetSprites(ResourceLocation.parse("naruto:textures/screens/jutsu_menu_buttons.png"), ResourceLocation.parse("naruto:textures/screens/jutsu_menu_buttons.png")), e -> {
					int x = JutsuMenuGUIScreen.this.x;
					int y = JutsuMenuGUIScreen.this.y;
					if (true) {
						PacketDistributor.sendToServer(new JutsuMenuGUIButtonMessage(0, x, y, z));
						JutsuMenuGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_jutsu_menu_buttons);
		imagebutton_jutsu_menu_buttons1 = new ImageButton(this.leftPos + 18, this.topPos + 0, 16, 16,
				new WidgetSprites(ResourceLocation.parse("naruto:textures/screens/jutsu_menu_buttons.png"), ResourceLocation.parse("naruto:textures/screens/jutsu_menu_buttons.png")), e -> {
					int x = JutsuMenuGUIScreen.this.x;
					int y = JutsuMenuGUIScreen.this.y;
					if (true) {
						PacketDistributor.sendToServer(new JutsuMenuGUIButtonMessage(1, x, y, z));
						JutsuMenuGUIButtonMessage.handleButtonAction(entity, 1, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_jutsu_menu_buttons1);
		imagebutton_jutsu_menu_buttons2 = new ImageButton(this.leftPos + 34, this.topPos + 0, 16, 16,
				new WidgetSprites(ResourceLocation.parse("naruto:textures/screens/jutsu_menu_buttons.png"), ResourceLocation.parse("naruto:textures/screens/jutsu_menu_buttons.png")), e -> {
					int x = JutsuMenuGUIScreen.this.x;
					int y = JutsuMenuGUIScreen.this.y;
					if (true) {
						PacketDistributor.sendToServer(new JutsuMenuGUIButtonMessage(2, x, y, z));
						JutsuMenuGUIButtonMessage.handleButtonAction(entity, 2, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_jutsu_menu_buttons2);
		imagebutton_jutsu_menu_buttons3 = new ImageButton(this.leftPos + 50, this.topPos + 0, 16, 16,
				new WidgetSprites(ResourceLocation.parse("naruto:textures/screens/jutsu_menu_buttons.png"), ResourceLocation.parse("naruto:textures/screens/jutsu_menu_buttons.png")), e -> {
					int x = JutsuMenuGUIScreen.this.x;
					int y = JutsuMenuGUIScreen.this.y;
					if (true) {
						PacketDistributor.sendToServer(new JutsuMenuGUIButtonMessage(3, x, y, z));
						JutsuMenuGUIButtonMessage.handleButtonAction(entity, 3, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_jutsu_menu_buttons3);
		imagebutton_jutsu_menu_buttons4 = new ImageButton(this.leftPos + 66, this.topPos + 0, 16, 16,
				new WidgetSprites(ResourceLocation.parse("naruto:textures/screens/jutsu_menu_buttons.png"), ResourceLocation.parse("naruto:textures/screens/jutsu_menu_buttons.png")), e -> {
					int x = JutsuMenuGUIScreen.this.x;
					int y = JutsuMenuGUIScreen.this.y;
					if (true) {
						PacketDistributor.sendToServer(new JutsuMenuGUIButtonMessage(4, x, y, z));
						JutsuMenuGUIButtonMessage.handleButtonAction(entity, 4, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_jutsu_menu_buttons4);
		imagebutton_jutsu_menu_buttons5 = new ImageButton(this.leftPos + 82, this.topPos + 0, 16, 16,
				new WidgetSprites(ResourceLocation.parse("naruto:textures/screens/jutsu_menu_buttons.png"), ResourceLocation.parse("naruto:textures/screens/jutsu_menu_buttons.png")), e -> {
					int x = JutsuMenuGUIScreen.this.x;
					int y = JutsuMenuGUIScreen.this.y;
					if (true) {
						PacketDistributor.sendToServer(new JutsuMenuGUIButtonMessage(5, x, y, z));
						JutsuMenuGUIButtonMessage.handleButtonAction(entity, 5, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_jutsu_menu_buttons5);
	}
}