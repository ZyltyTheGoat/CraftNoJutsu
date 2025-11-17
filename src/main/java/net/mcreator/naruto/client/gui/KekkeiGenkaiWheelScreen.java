package net.mcreator.naruto.client.gui;

import org.joml.Matrix4f;

import org.checkerframework.checker.units.qual.s;
import org.checkerframework.checker.units.qual.kg;
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

import net.mcreator.naruto.world.inventory.KekkeiGenkaiWheelMenu;
import net.mcreator.naruto.procedures.JutsuWheelOnKeyPressedProcedure;
import net.mcreator.naruto.network.SetToggleJutsuPacket;
import net.mcreator.naruto.network.SetFavouriteJutsuPacket;
import net.mcreator.naruto.network.SetActiveJutsuPacket;
import net.mcreator.naruto.network.NarutoModVariables;
import net.mcreator.naruto.init.NarutoModScreens;
import net.mcreator.naruto.init.NarutoModKeyMappings;
import net.mcreator.naruto.JutsuRegistry;
import net.mcreator.naruto.JutsuData;

import java.util.stream.Collectors;
import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.LinkedHashSet;
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

public class KekkeiGenkaiWheelScreen extends AbstractContainerScreen<KekkeiGenkaiWheelMenu> implements NarutoModScreens.ScreenAccessor {
	private static final int RADIUS_IN = 40;
	private static final int RADIUS_OUT = 160;
	private static final int RADIUS_COLOR_OUT = 165;
	private static final int BASE_CIRCLE_COLOR = 0x96000000;
	private static final int HOVER_CIRCLE_COLOR = 0x96FFFFFF;
	private static final int PAGE_INDICATOR_COLOR = 0xFFFFFFFF;
	private static final int PAGE_DOT_COLOR = 0x80FFFFFF;
	private static final int PAGE_DOT_ACTIVE_COLOR = 0xFFFFFFFF;
	private static final float GAP_WIDTH_PIXELS = 4F;
	private static final int ICON_SIZE = 64;
	private final Level world;
	private final int x, y, z;
	private final Player entity;

	private static class Page {
		final String name;
		final String nature;
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

	public KekkeiGenkaiWheelScreen(KekkeiGenkaiWheelMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 166;
		JutsuRegistry.initializeJutsus();
		initializePages();
	}

	private void initializePages() {
		String unlockedKekkeiGenkaiString = entity.getData(NarutoModVariables.PLAYER_VARIABLES).unlockedKekkeiGenkai;
		Set<String> unlockedKekkeiGenkai = new HashSet<>();
		if (unlockedKekkeiGenkaiString != null && !unlockedKekkeiGenkaiString.isEmpty()) {
			String[] kgArray = unlockedKekkeiGenkaiString.split(",");
			for (String kg : kgArray) {
				unlockedKekkeiGenkai.add(kg.trim().toUpperCase());
			}
		}
		String[] natures = {"WOOD", "LAVA", "BOIL", "ICE", "MAGNET", "EXPLOSION", "STORM", "DUST"};
		String[] natureNames = {"Wood Release", "Lava Release", "Boil Release", "Ice Release", "Magnet Release", "Explosion Release", "Storm Release", "Dust Release"};
		for (int i = 0; i < natures.length; i++) {
			String nature = natures[i];
			String natureName = natureNames[i];
			if (!unlockedKekkeiGenkai.contains(nature)) {
				continue;
			}
			Map<String, JutsuData> allJutsus = JutsuRegistry.getJutsusByNature(nature);
			List<String> jutsuIds = new ArrayList<>();
			List<String> jutsuNames = new ArrayList<>();
			for (Map.Entry<String, JutsuData> entry : allJutsus.entrySet()) {
				JutsuData jutsu = entry.getValue();
				String jutsuId = entry.getKey();
				if (jutsu.getType().equalsIgnoreCase("KEKKEI_GENKAI")) {
					jutsuIds.add(jutsuId);
					jutsuNames.add(jutsu.getName());
				}
			}
			if (!jutsuIds.isEmpty()) {
				pages.add(new Page(natureName, nature, jutsuIds, jutsuNames));
			}
		}
		if (pages.isEmpty()) {
			pages.add(new Page("No Kekkei Genkai", "", new ArrayList<>(), Arrays.asList("No Kekkei Genkai unlocked yet")));
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
		renderColorRing(guiGraphics, centerX, centerY);
		renderNatureIcon(guiGraphics, centerX, centerY);
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

	private Set<String> getActiveToggleJutsus() {
		Set<String> activeToggles = new HashSet<>();
		String activeToggleString = entity.getData(NarutoModVariables.PLAYER_VARIABLES).activeToggleJutsu;
		if (activeToggleString != null && !activeToggleString.isEmpty()) {
			String[] toggleArray = activeToggleString.split(",");
			for (String toggle : toggleArray) {
				activeToggles.add(toggle.trim());
			}
		}
		return activeToggles;
	}

	private boolean isJutsuToggled(String jutsuId) {
		return getActiveToggleJutsus().contains(jutsuId);
	}

	private void renderNatureIcon(GuiGraphics guiGraphics, int centerX, int centerY) {
		String nature = getCurrentNature();
		if (nature.isEmpty())
			return;
		String iconName = "icon_" + nature.toLowerCase();
		ResourceLocation iconLocation = ResourceLocation.fromNamespaceAndPath("naruto", "textures/screens/" + iconName + ".png");
		int iconX = centerX - ICON_SIZE / 2;
		int iconY = centerY - ICON_SIZE / 2;
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		guiGraphics.blit(iconLocation, iconX, iconY, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);
		RenderSystem.disableBlend();
	}

	private void renderRadialWheel(GuiGraphics guiGraphics, int centerX, int centerY) {
		List<String> jutsuNames = getCurrentJutsuNames();
		List<String> jutsuIds = getCurrentJutsuIds();
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
			boolean isToggled = false;
			if (i < jutsuIds.size()) {
				String jutsuId = jutsuIds.get(i);
				isToggled = isJutsuToggled(jutsuId);
			}
			int color;
			if (isToggled) {
				color = (this.hovered == i) ? BASE_CIRCLE_COLOR : HOVER_CIRCLE_COLOR;
			} else {
				color = (this.hovered == i) ? HOVER_CIRCLE_COLOR : BASE_CIRCLE_COLOR;
			}
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
		List<String> jutsuIds = getCurrentJutsuIds();
		if (jutsuNames.isEmpty())
			return;
		Set<String> favouriteJutsus = new HashSet<>();
		String favouriteJutsuString = entity.getData(NarutoModVariables.PLAYER_VARIABLES).favouriteJutsu;
		if (favouriteJutsuString != null && !favouriteJutsuString.isEmpty()) {
			String[] favouriteArray = favouriteJutsuString.split(",");
			for (String jutsu : favouriteArray) {
				favouriteJutsus.add(jutsu.trim());
			}
		}
		float radius = (RADIUS_IN + RADIUS_OUT) / 2.0F;
		for (int i = 0; i < jutsuNames.size(); i++) {
			float startAngle = getAngleFor(i - 0.5F, jutsuNames.size());
			float endAngle = getAngleFor(i + 0.5F, jutsuNames.size());
			float middleAngle = (startAngle + endAngle) / 2.0F;
			int posX = (int) (centerX + radius * Math.cos(middleAngle));
			int posY = (int) (centerY + radius * Math.sin(middleAngle));
			String displayName = jutsuNames.get(i);
			if (i < jutsuIds.size() && favouriteJutsus.contains(jutsuIds.get(i))) {
				displayName = "★ " + displayName;
			}
			guiGraphics.drawCenteredString(font, displayName, posX, posY - font.lineHeight / 2, 0xFFFFFFFF);
		}
	}

	private void renderPageIndicator(GuiGraphics guiGraphics, int windowWidth, int windowHeight) {
		if (font == null || pages.size() < 1)
			return;
		String pageName = getCurrentPageName();
		guiGraphics.pose().pushPose();
		guiGraphics.pose().scale(1.5F, 1.5F, 1.5F);
		guiGraphics.drawCenteredString(font, pageName, (int) (windowWidth / 2 / 1.5F), (int) (40 / 1.5F), PAGE_INDICATOR_COLOR);
		guiGraphics.pose().popPose();
		if (pages.size() > 1) {
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
				float gapAngleIn = GAP_WIDTH_PIXELS / RADIUS_IN;
				float gapAngleOut = GAP_WIDTH_PIXELS / RADIUS_OUT;
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
		if (jutsuIds.isEmpty() || this.hovered < 0 || this.hovered >= jutsuIds.size() || this.minecraft == null || this.minecraft.player == null) {
			return super.mouseClicked(mouseX, mouseY, button);
		}
		String selectedJutsuId = jutsuIds.get(this.hovered);
		String selectedJutsuName = jutsuNames.get(this.hovered);
		JutsuData jutsuData = JutsuRegistry.getJutsu(selectedJutsuId);
		if (button == 1) {
			String currentFavorites = entity.getData(NarutoModVariables.PLAYER_VARIABLES).favouriteJutsu;
			Set<String> favoriteSet = currentFavorites != null && !currentFavorites.isEmpty()
					? Arrays.stream(currentFavorites.split(",")).map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toCollection(LinkedHashSet::new))
					: new LinkedHashSet<>();
			if (!favoriteSet.remove(selectedJutsuId)) {
				favoriteSet.add(selectedJutsuId);
			}
			PacketDistributor.sendToServer(new SetFavouriteJutsuPacket(String.join(",", favoriteSet)));
			return true;
		}
		if (jutsuData != null && jutsuData.getIsToggle()) {
			String currentToggles = entity.getData(NarutoModVariables.PLAYER_VARIABLES).activeToggleJutsu;
			Set<String> toggleSet = currentToggles != null && !currentToggles.isEmpty() ? Arrays.stream(currentToggles.split(",")).map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toSet()) : new HashSet<>();
			if (!toggleSet.remove(selectedJutsuId)) {
				toggleSet.add(selectedJutsuId);
			}
			PacketDistributor.sendToServer(new SetToggleJutsuPacket(String.join(",", toggleSet)));
		} else {
			PacketDistributor.sendToServer(new SetActiveJutsuPacket(selectedJutsuId));
			this.minecraft.player.closeContainer();
		}
		return true;
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
			JutsuWheelOnKeyPressedProcedure.recordCloseTime();
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