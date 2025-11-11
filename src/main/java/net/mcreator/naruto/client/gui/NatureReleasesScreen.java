package net.mcreator.naruto.client.gui;

import org.joml.Matrix4f;

import org.checkerframework.checker.units.qual.g;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;

import net.mcreator.naruto.world.inventory.NatureReleasesMenu;
import net.mcreator.naruto.network.SetActiveJutsuPacket;
import net.mcreator.naruto.network.NarutoModVariables;
import net.mcreator.naruto.init.NarutoModScreens;
import net.mcreator.naruto.init.NarutoModKeyMappings;
import net.mcreator.naruto.JutsuRegistry;
import net.mcreator.naruto.JutsuData;

import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.HashSet;
import java.util.Arrays;
import java.util.ArrayList;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.systems.RenderSystem;

import com.jcraft.jogg.Page;

public class NatureReleasesScreen extends AbstractContainerScreen<NatureReleasesMenu> implements NarutoModScreens.ScreenAccessor {
	private static final int RADIUS_IN = 40;
	private static final int RADIUS_OUT = 160;
	private static final int RADIUS_COLOR_OUT = 165; // Outer radius for color ring (extends from RADIUS_OUT)
	private static final int BASE_CIRCLE_COLOR = 0x96000000;
	private static final int HOVER_CIRCLE_COLOR = 0x96FFFFFF;
	private static final int PAGE_INDICATOR_COLOR = 0xFFFFFFFF;
	private static final int PAGE_DOT_COLOR = 0x80FFFFFF;
	private static final int PAGE_DOT_ACTIVE_COLOR = 0xFFFFFFFF;
	private static final float GAP_WIDTH_PIXELS = 12F; // Gap width in pixels (adjust this value to change gap size)
	private static final int ICON_SIZE = 64; // Size of the nature icon in pixels
	private final Level world;
	private final int x, y, z;
	private final Player entity;

	private static class Page {
		final String name;
		final String nature; // Store the nature type for icon lookup
		final List<String> jutsuIds;
		final List<String> jutsuNames;

		Page(String name, String nature, List<String> jutsuIds, List<String> jutsuNames) {
			this.name = name;
			this.nature = nature;
			this.jutsuIds = jutsuIds;
			this.jutsuNames = jutsuNames;
		}
	}

	private final List<Page> pages = new ArrayList<>();
	private int currentPage = 0;
	private boolean menuStateUpdateActive = false;
	private int hovered = -1;

	public NatureReleasesScreen(NatureReleasesMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 166;
		// Ensure jutsus are initialized
		JutsuRegistry.initializeJutsus();
		// Initialize pages dynamically from player's unlocked jutsus and natures
		initializePages();
	}

	private void initializePages() {
		// Get player's unlocked jutsus and natures from NBT data
		String unlockedJutsuString = entity.getData(NarutoModVariables.PLAYER_VARIABLES).unlockedJutsu;
		String unlockedNaturesString = entity.getData(NarutoModVariables.PLAYER_VARIABLES).unlockedNatures;
		// Parse unlocked jutsus into a set for fast lookup
		Set<String> unlockedJutsus = new HashSet<>();
		if (!unlockedJutsuString.isEmpty()) {
			String[] jutsuArray = unlockedJutsuString.split(",");
			for (String jutsu : jutsuArray) {
				unlockedJutsus.add(jutsu.trim());
			}
		}
		// Parse unlocked natures into a set for fast lookup
		Set<String> unlockedNatures = new HashSet<>();
		if (!unlockedNaturesString.isEmpty()) {
			String[] natureArray = unlockedNaturesString.split(",");
			for (String nature : natureArray) {
				unlockedNatures.add(nature.trim().toUpperCase());
			}
		}
		// Define the nature types we want pages for
		String[] natures = {"FIRE", "WATER", "EARTH", "LIGHTNING", "WIND"};
		String[] natureNames = {"Fire Release", "Water Release", "Earth Release", "Lightning Release", "Wind Release"};
		for (int i = 0; i < natures.length; i++) {
			String nature = natures[i];
			String natureName = natureNames[i];
			// Only create page if player has unlocked this nature
			if (!unlockedNatures.contains(nature)) {
				continue;
			}
			// Get all jutsus of this nature that are also type NATURE
			Map<String, JutsuData> natureJutsus = JutsuRegistry.getJutsusByNature(nature);
			List<String> jutsuIds = new ArrayList<>();
			List<String> jutsuNames = new ArrayList<>();
			for (Map.Entry<String, JutsuData> entry : natureJutsus.entrySet()) {
				JutsuData jutsu = entry.getValue();
				String jutsuId = entry.getKey();
				// Only include jutsus that are:
				// 1. Type NATURE
				// 2. Unlocked by the player
				if (jutsu.getType().equalsIgnoreCase("NATURE") && unlockedJutsus.contains(jutsuId)) {
					jutsuIds.add(jutsuId);
					jutsuNames.add(jutsu.getName());
				}
			}
			// Only add page if there are unlocked jutsus for this nature
			if (!jutsuIds.isEmpty()) {
				pages.add(new Page(natureName, nature, jutsuIds, jutsuNames));
			}
		}
		// Fallback if no pages were created
		if (pages.isEmpty()) {
			pages.add(new Page("No Unlocked Jutsus", "", new ArrayList<>(), Arrays.asList("No jutsus unlocked yet")));
		}
	}

	@Override
	public void init() {
		super.init();
		if (this.minecraft == null) {
			this.minecraft = Minecraft.getInstance();
		}
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		menuStateUpdateActive = false;
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		if (this.minecraft == null)
			return;
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		int windowWidth = this.minecraft.getWindow().getGuiScaledWidth();
		int windowHeight = this.minecraft.getWindow().getGuiScaledHeight();
		int centerX = windowWidth / 2;
		int centerY = windowHeight / 2;
		renderRadialWheel(guiGraphics, centerX, centerY);
		renderColorRing(guiGraphics, centerX, centerY); // Render the new color ring
		renderNatureIcon(guiGraphics, centerX, centerY); // Render the nature icon in the center
		renderJutsuLabels(guiGraphics, centerX, centerY);
		renderPageIndicator(guiGraphics, windowWidth, windowHeight);
	}

	private List<String> getCurrentJutsuNames() {
		if (currentPage >= 0 && currentPage < pages.size()) {
			return pages.get(currentPage).jutsuNames;
		}
		return new ArrayList<>();
	}

	private List<String> getCurrentJutsuIds() {
		if (currentPage >= 0 && currentPage < pages.size()) {
			return pages.get(currentPage).jutsuIds;
		}
		return new ArrayList<>();
	}

	private String getCurrentPageName() {
		if (currentPage >= 0 && currentPage < pages.size()) {
			return pages.get(currentPage).name;
		}
		return "";
	}

	private String getCurrentNature() {
		if (currentPage >= 0 && currentPage < pages.size()) {
			return pages.get(currentPage).nature;
		}
		return "";
	}

	private void renderNatureIcon(GuiGraphics guiGraphics, int centerX, int centerY) {
		String nature = getCurrentNature();
		if (nature.isEmpty())
			return;

		// Convert nature to lowercase and create the icon resource location
		// Format: namespace:textures/gui/nature_icons/fire_release_icon.png
		String iconName = nature.toLowerCase() + "_release_icon";
		ResourceLocation iconLocation = ResourceLocation.fromNamespaceAndPath("naruto", "textures/screens/" + iconName + ".png");

		// Calculate position to center the icon
		int iconX = centerX - ICON_SIZE / 2;
		int iconY = centerY - ICON_SIZE / 2;

		// Enable blending for transparency
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();

		// Render the icon
		guiGraphics.blit(iconLocation, iconX, iconY, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);

		RenderSystem.disableBlend();
	}

	private void renderRadialWheel(GuiGraphics guiGraphics, int centerX, int centerY) {
		List<String> jutsuNames = getCurrentJutsuNames();
		if (jutsuNames.isEmpty())
			return;
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		guiGraphics.pose().pushPose();
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder buffer = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
		for (int i = 0; i < jutsuNames.size(); i++) {
			int color = (this.hovered == i) ? HOVER_CIRCLE_COLOR : BASE_CIRCLE_COLOR;
			if (jutsuNames.size() > 1) {
				float gapAngleIn = GAP_WIDTH_PIXELS / RADIUS_IN;
				float gapAngleOut = GAP_WIDTH_PIXELS / RADIUS_OUT;
				float startAngleIn = getAngleFor(i - 0.5F, jutsuNames.size()) + gapAngleIn / 2;
				float endAngleIn = getAngleFor(i + 0.5F, jutsuNames.size()) - gapAngleIn / 2;
				float startAngleOut = getAngleFor(i - 0.5F, jutsuNames.size()) + gapAngleOut / 2;
				float endAngleOut = getAngleFor(i + 0.5F, jutsuNames.size()) - gapAngleOut / 2;
				drawSliceWithGaps(guiGraphics.pose(), buffer, centerX, centerY, startAngleIn, endAngleIn, startAngleOut, endAngleOut, RADIUS_IN, RADIUS_OUT, color);
			} else {
				float startAngle = getAngleFor(i - 0.5F, jutsuNames.size());
				float endAngle = getAngleFor(i + 0.5F, jutsuNames.size());
				drawSlice(guiGraphics.pose(), buffer, centerX, centerY, startAngle, endAngle, RADIUS_IN, RADIUS_OUT, color);
			}
		}
		BufferUploader.drawWithShader(buffer.buildOrThrow());
		RenderSystem.disableBlend();
		guiGraphics.pose().popPose();
	}

	private void renderColorRing(GuiGraphics guiGraphics, int centerX, int centerY) {
		List<String> jutsuNames = getCurrentJutsuNames();
		List<String> jutsuIds = getCurrentJutsuIds();
		// Add safety check - both lists must have the same size and not be empty
		if (jutsuNames.isEmpty() || jutsuIds.isEmpty() || jutsuNames.size() != jutsuIds.size()) {
			return;
		}
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		guiGraphics.pose().pushPose();
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder buffer = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
		for (int i = 0; i < jutsuNames.size(); i++) {
			// Get the jutsu color from JutsuData
			String jutsuId = jutsuIds.get(i);
			JutsuData jutsuData = JutsuRegistry.getJutsu(jutsuId);
			int color = jutsuData != null ? jutsuData.getColor() : 0x96FFFFFF; // Default to white if not found
			if (jutsuNames.size() > 1) {
				// Use same gap angles as outer radius to create seamless continuation
				float gapAngleOut = GAP_WIDTH_PIXELS / RADIUS_OUT;
				float gapAngleColorOut = GAP_WIDTH_PIXELS / RADIUS_COLOR_OUT;
				float startAngleOut = getAngleFor(i - 0.5F, jutsuNames.size()) + gapAngleOut / 2;
				float endAngleOut = getAngleFor(i + 0.5F, jutsuNames.size()) - gapAngleOut / 2;
				float startAngleColorOut = getAngleFor(i - 0.5F, jutsuNames.size()) + gapAngleColorOut / 2;
				float endAngleColorOut = getAngleFor(i + 0.5F, jutsuNames.size()) - gapAngleColorOut / 2;
				drawSliceWithGaps(guiGraphics.pose(), buffer, centerX, centerY, startAngleOut, endAngleOut, startAngleColorOut, endAngleColorOut, RADIUS_OUT, RADIUS_COLOR_OUT, color);
			} else {
				float startAngle = getAngleFor(i - 0.5F, jutsuNames.size());
				float endAngle = getAngleFor(i + 0.5F, jutsuNames.size());
				drawSlice(guiGraphics.pose(), buffer, centerX, centerY, startAngle, endAngle, RADIUS_OUT, RADIUS_COLOR_OUT, color);
			}
		}
		BufferUploader.drawWithShader(buffer.buildOrThrow());
		RenderSystem.disableBlend();
		guiGraphics.pose().popPose();
	}

	private void renderJutsuLabels(GuiGraphics guiGraphics, int centerX, int centerY) {
		if (font == null)
			return;
		List<String> jutsuNames = getCurrentJutsuNames();
		if (jutsuNames.isEmpty())
			return;
		float radius = (RADIUS_IN + RADIUS_OUT) / 2.0F;
		for (int i = 0; i < jutsuNames.size(); i++) {
			float startAngle = getAngleFor(i - 0.5F, jutsuNames.size());
			float endAngle = getAngleFor(i + 0.5F, jutsuNames.size());
			float middleAngle = (startAngle + endAngle) / 2.0F;
			int posX = (int) (centerX + radius * Math.cos(middleAngle));
			int posY = (int) (centerY + radius * Math.sin(middleAngle));
			guiGraphics.drawCenteredString(font, jutsuNames.get(i), posX, posY - font.lineHeight / 2, 0xFFFFFFFF);
		}
	}

	private void renderPageIndicator(GuiGraphics guiGraphics, int windowWidth, int windowHeight) {
		if (font == null || pages.size() < 1)
			return;
		// Render page name at top (larger)
		String pageName = getCurrentPageName();
		guiGraphics.pose().pushPose();
		guiGraphics.pose().scale(1.5F, 1.5F, 1.5F);
		guiGraphics.drawCenteredString(font, pageName, (int) (windowWidth / 2 / 1.5F), (int) (40 / 1.5F), PAGE_INDICATOR_COLOR);
		guiGraphics.pose().popPose();
		if (pages.size() > 1) {
			// Render page dots
			int dotY = windowHeight - 40;
			int dotSpacing = 15;
			int totalWidth = (pages.size() - 1) * dotSpacing;
			int startX = (windowWidth - totalWidth) / 2;
			for (int i = 0; i < pages.size(); i++) {
				int dotX = startX + i * dotSpacing;
				int color = (i == currentPage) ? PAGE_DOT_ACTIVE_COLOR : PAGE_DOT_COLOR;
				int size = (i == currentPage) ? 4 : 3;
				guiGraphics.fill(dotX - size / 2, dotY - size / 2, dotX + size / 2, dotY + size / 2, color);
			}
			// Render scroll hint
			guiGraphics.drawCenteredString(font, "Scroll to change pages", windowWidth / 2, windowHeight - 60, 0xFFFFFFFF);
		}
	}

	private void drawSliceWithGaps(PoseStack poseStack, BufferBuilder buffer, float centerX, float centerY, float startAngleIn, float endAngleIn, float startAngleOut, float endAngleOut, int radiusIn, int radiusOut, int color) {
		float angleIn = endAngleIn - startAngleIn;
		float angleOut = endAngleOut - startAngleOut;
		float precision = 2.5F / 360.0F;
		int sectionsIn = Math.max(1, Mth.ceil(angleIn / precision));
		int sectionsOut = Math.max(1, Mth.ceil(angleOut / precision));
		int sections = Math.max(sectionsIn, sectionsOut);
		int r = (color >> 16) & 0xFF;
		int g = (color >> 8) & 0xFF;
		int b = color & 0xFF;
		int a = (color >> 24) & 0xFF;
		float sliceIn = angleIn / sections;
		float sliceOut = angleOut / sections;
		Matrix4f matrix4f = poseStack.last().pose();
		for (int i = 0; i < sections; i++) {
			float angle1In = startAngleIn + i * sliceIn;
			float angle2In = startAngleIn + (i + 1) * sliceIn;
			float angle1Out = startAngleOut + i * sliceOut;
			float angle2Out = startAngleOut + (i + 1) * sliceOut;
			float x1 = centerX + radiusIn * (float) Math.cos(angle1In);
			float y1 = centerY + radiusIn * (float) Math.sin(angle1In);
			float x2 = centerX + radiusOut * (float) Math.cos(angle1Out);
			float y2 = centerY + radiusOut * (float) Math.sin(angle1Out);
			float x3 = centerX + radiusOut * (float) Math.cos(angle2Out);
			float y3 = centerY + radiusOut * (float) Math.sin(angle2Out);
			float x4 = centerX + radiusIn * (float) Math.cos(angle2In);
			float y4 = centerY + radiusIn * (float) Math.sin(angle2In);
			buffer.addVertex(matrix4f, x2, y2, 0.0F).setColor(r, g, b, a);
			buffer.addVertex(matrix4f, x1, y1, 0.0F).setColor(r, g, b, a);
			buffer.addVertex(matrix4f, x4, y4, 0.0F).setColor(r, g, b, a);
			buffer.addVertex(matrix4f, x3, y3, 0.0F).setColor(r, g, b, a);
		}
	}

	private void drawSlice(PoseStack poseStack, BufferBuilder buffer, float centerX, float centerY, float startAngle, float endAngle, int radiusIn, int radiusOut, int color) {
		float angle = endAngle - startAngle;
		float precision = 2.5F / 360.0F;
		int sections = Math.max(1, Mth.ceil(angle / precision));
		int r = (color >> 16) & 0xFF;
		int g = (color >> 8) & 0xFF;
		int b = color & 0xFF;
		int a = (color >> 24) & 0xFF;
		float slice = angle / sections;
		Matrix4f matrix4f = poseStack.last().pose();
		for (int i = 0; i < sections; i++) {
			float angle1 = startAngle + i * slice;
			float angle2 = startAngle + (i + 1) * slice;
			float x1 = centerX + radiusIn * (float) Math.cos(angle1);
			float y1 = centerY + radiusIn * (float) Math.sin(angle1);
			float x2 = centerX + radiusOut * (float) Math.cos(angle1);
			float y2 = centerY + radiusOut * (float) Math.sin(angle1);
			float x3 = centerX + radiusOut * (float) Math.cos(angle2);
			float y3 = centerY + radiusOut * (float) Math.sin(angle2);
			float x4 = centerX + radiusIn * (float) Math.cos(angle2);
			float y4 = centerY + radiusIn * (float) Math.sin(angle2);
			buffer.addVertex(matrix4f, x2, y2, 0.0F).setColor(r, g, b, a);
			buffer.addVertex(matrix4f, x1, y1, 0.0F).setColor(r, g, b, a);
			buffer.addVertex(matrix4f, x4, y4, 0.0F).setColor(r, g, b, a);
			buffer.addVertex(matrix4f, x3, y3, 0.0F).setColor(r, g, b, a);
		}
	}

	private float getAngleFor(float index, int totalItems) {
		if (totalItems == 0)
			return 0;
		return (float) (((index / totalItems) + 0.25D) * Mth.TWO_PI + Math.PI);
	}

	@Override
	public void mouseMoved(double mouseX, double mouseY) {
		super.mouseMoved(mouseX, mouseY);
		List<String> jutsuNames = getCurrentJutsuNames();
		if (jutsuNames.isEmpty())
			return;
		int windowWidth = this.minecraft.getWindow().getGuiScaledWidth();
		int windowHeight = this.minecraft.getWindow().getGuiScaledHeight();
		int centerX = windowWidth / 2;
		int centerY = windowHeight / 2;
		double mouseAngle = Math.atan2(mouseY - centerY, mouseX - centerX);
		double mouseDistance = Math.sqrt(Math.pow(mouseX - centerX, 2.0D) + Math.pow(mouseY - centerY, 2.0D));
		float startAngle = getAngleFor(-0.5F, jutsuNames.size());
		float endAngle = getAngleFor(jutsuNames.size() - 0.5F, jutsuNames.size());
		while (mouseAngle < startAngle)
			mouseAngle += Mth.TWO_PI;
		while (mouseAngle >= endAngle)
			mouseAngle -= Mth.TWO_PI;
		this.hovered = -1;
		for (int i = 0; i < jutsuNames.size(); i++) {
			if (jutsuNames.size() > 1) {
				// Calculate gap angles for both inner and outer radius
				float gapAngleIn = GAP_WIDTH_PIXELS / RADIUS_IN;
				float gapAngleOut = GAP_WIDTH_PIXELS / RADIUS_OUT;
				// Interpolate gap angle based on mouse distance
				float gapAngle = gapAngleOut + (gapAngleIn - gapAngleOut) * (float) ((mouseDistance - RADIUS_OUT) / (RADIUS_IN - RADIUS_OUT));
				float currentStart = getAngleFor(i - 0.5F, jutsuNames.size()) + gapAngle / 2;
				float currentEnd = getAngleFor(i + 0.5F, jutsuNames.size()) - gapAngle / 2;
				if (mouseAngle >= currentStart && mouseAngle < currentEnd && mouseDistance >= RADIUS_IN && mouseDistance < RADIUS_OUT) {
					this.hovered = i;
					break;
				}
			} else {
				float currentStart = getAngleFor(i - 0.5F, jutsuNames.size());
				float currentEnd = getAngleFor(i + 0.5F, jutsuNames.size());
				if (mouseAngle >= currentStart && mouseAngle < currentEnd && mouseDistance >= RADIUS_IN && mouseDistance < RADIUS_OUT) {
					this.hovered = i;
					break;
				}
			}
		}
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
		if (pages.size() <= 1)
			return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
		if (scrollY > 0) {
			currentPage--;
			if (currentPage < 0)
				currentPage = pages.size() - 1;
			this.hovered = -1;
			return true;
		} else if (scrollY < 0) {
			currentPage++;
			if (currentPage >= pages.size())
				currentPage = 0;
			this.hovered = -1;
			return true;
		}
		return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		List<String> jutsuIds = getCurrentJutsuIds();
		List<String> jutsuNames = getCurrentJutsuNames();
		// Add safety checks to prevent IndexOutOfBoundsException
		if (jutsuIds.isEmpty() || jutsuNames.isEmpty()) {
			return super.mouseClicked(mouseX, mouseY, button);
		}
		// Ensure hovered index is valid for both lists
		if (this.hovered >= 0 && this.hovered < jutsuNames.size() && this.hovered < jutsuIds.size()) {
			if (this.minecraft != null && this.minecraft.player != null) {
				String selectedJutsuId = jutsuIds.get(this.hovered);
				String selectedJutsuName = jutsuNames.get(this.hovered);
				// Send packet to server to set active jutsu (NeoForge way)
				PacketDistributor.sendToServer(new SetActiveJutsuPacket(selectedJutsuId));
				// Optional: Show confirmation message
				this.minecraft.player.displayClientMessage(Component.literal("Selected: " + selectedJutsuName), true);
				this.minecraft.player.closeContainer();
			}
			return true;
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean keyPressed(int key, int scanCode, int modifiers) {
		if (key == 256) {
			if (this.minecraft != null && this.minecraft.player != null) {
				this.minecraft.player.closeContainer();
				return true;
			}
		}
		return super.keyPressed(key, scanCode, modifiers);
	}

	@Override
	public boolean keyReleased(int key, int scanCode, int modifiers) {
		if (key == NarutoModKeyMappings.JUTSU_WHEEL.getKey().getValue()) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyReleased(key, scanCode, modifiers);
	}

	@Override
	public void removed() {
		if (this.minecraft != null && this.minecraft.player != null) {
			super.removed();
		}
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
	}

	@Override
	public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public boolean shouldCloseOnEsc() {
		return true;
	}
}