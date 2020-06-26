package com.alexjw.siegecraft.client.event;

import com.alexjw.siegecraft.Siege;
import com.alexjw.siegecraft.client.gui.GuiCustomMenu;
import com.alexjw.siegecraft.server.data.SiegeData;
import com.alexjw.siegecraft.server.data.SiegePlayer;
import com.alexjw.siegecraft.server.helper.SiegeHelper;
import com.alexjw.siegecraft.server.items.ModItems;
import com.alexjw.siegecraft.server.items.guns.IGun;
import com.alexjw.siegecraft.server.items.guns.ItemStimPistol;
import com.alexjw.siegecraft.server.operators.Operator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.Objects;
import java.util.Random;

import static com.alexjw.core.client.util.GuiUtil.drawTexturedQuadFit;

@Mod.EventBusSubscriber(modid = Siege.MODID)
public class ClientEventHandler {
    public static boolean isLeaningLeft = false;
    public static boolean isLeaningRight = false;
    private static final ResourceLocation droneHudTexture = new ResourceLocation(Siege.MODID, "textures/gui/camera_hud.png");
    private static final ResourceLocation jackalHudTexture = new ResourceLocation(Siege.MODID, "textures/gui/jackal_hud.png");
    private static final ResourceLocation hudTexture = new ResourceLocation(Siege.MODID, "textures/gui/rainbow_hud.png");
    private static final ResourceLocation none = new ResourceLocation(Siege.MODID, "textures/gui/icon/none.png");
    public static boolean isZooming;
    private static float lastFOV = 0;
    private static Random random = new Random();
    private static int count = 0;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onFOVUpdate(FOVUpdateEvent event) {
        EntityPlayer entityPlayer = event.getEntity();

        if (entityPlayer.getHeldItemMainhand().getItem().equals(ModItems.itemStimPistol)) {
            ItemStimPistol itemStimPistol = (ItemStimPistol) entityPlayer.getHeldItemMainhand().getItem();
            if (isZooming) {
                lastFOV = event.getFov();
                event.setNewfov(event.getFov() - itemStimPistol.getWeaponZoom());
            } else if (lastFOV != 0) {
                event.setNewfov(lastFOV);
            }
        } else if (isZooming && lastFOV != 0) {
            isZooming = false;
            event.setNewfov(lastFOV);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void ammoRender(RenderGameOverlayEvent.Pre event) {
        EntityPlayer entityPlayer = Minecraft.getMinecraft().player;

        if ((event.getType().equals(RenderGameOverlayEvent.ElementType.HOTBAR))) {
            if (entityPlayer.getHeldItemMainhand().getItem().equals(ModItems.itemStimPistol)) {
                ItemStimPistol itemStimPistol = (ItemStimPistol) entityPlayer.getHeldItemMainhand().getItem();
                ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
                int xPos = scaledResolution.getScaledWidth();
                int yPos = scaledResolution.getScaledHeight();
                Minecraft.getMinecraft().ingameGUI.drawString(Minecraft.getMinecraft().fontRenderer, itemStimPistol.getAmmo(entityPlayer.getHeldItemMainhand()) + "/1", xPos - 24, (yPos) - 14, -1);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onLeftClick(PlayerInteractEvent.LeftClickEmpty event) {
        if (event.getItemStack().getItem().equals(ModItems.itemStimPistol)) {
            if (event.isCancelable())
                event.setCanceled(true);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onMouseEvent(MouseEvent event) {
        Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND);
        if (Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof IGun) {
            if (event.getButton() == 0 && event.isButtonstate()) {
                if (((IGun) Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND).getItem()).onLeftClick(Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND), Minecraft.getMinecraft().player)) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onMainMenuLoad(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiMainMenu && Siege.isStandalone) {
            event.setGui(new GuiCustomMenu());
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void renderPlayer(EntityViewRenderEvent.CameraSetup entityViewRenderEvent) {
        if (entityViewRenderEvent.getEntity() instanceof EntityPlayer) {
            if (SiegeHelper.getOperator((EntityPlayer) entityViewRenderEvent.getEntity()) != null && !SiegeHelper.isDroning((EntityPlayer) entityViewRenderEvent.getEntity())) {
                if (isLeaningLeft) {
                    entityViewRenderEvent.setRoll(-20);
                } else if (isLeaningRight) {
                    entityViewRenderEvent.setRoll(20);
                } else {
                    entityViewRenderEvent.setRoll(0);
                }
            } else {
                entityViewRenderEvent.setRoll(0);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void hudShowText(RenderGameOverlayEvent.Pre event) {
        EntityPlayer entityPlayer = Minecraft.getMinecraft().player;
        if ((event.getType().equals(RenderGameOverlayEvent.ElementType.HOTBAR))) {
            if (entityPlayer != null) {
                Operator operator = SiegeHelper.getOperator(entityPlayer);
                if (operator != null) {
                    ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
                    int xPos = scaledResolution.getScaledWidth();
                    int yPos = scaledResolution.getScaledHeight();
                    if (operator.getTeam().equals(Operator.Team.ATTACKER) && SiegeHelper.canRappel(entityPlayer)) {
                        Minecraft.getMinecraft().ingameGUI.drawCenteredString(Minecraft.getMinecraft().fontRenderer, "Press 'F' to rappel.", xPos / 2, (yPos / 2) + 90, -1);
                    } else if (SiegeHelper.isRopeOut(entityPlayer)) {
                        Minecraft.getMinecraft().ingameGUI.drawCenteredString(Minecraft.getMinecraft().fontRenderer, "Press 'F' to remove your rope.", xPos / 2, (yPos / 2) + 90, -1);
                    } else if (SiegeHelper.canVault(entityPlayer)) {
                        Minecraft.getMinecraft().ingameGUI.drawCenteredString(Minecraft.getMinecraft().fontRenderer, "Press 'SPACE' to vault.", xPos / 2, (yPos / 2) + 90, -1);
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void rappelRenderEvent(RenderLivingEvent.Pre event) {
        if (event.getEntity() instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer) event.getEntity();
            SiegePlayer siegePlayer = SiegeHelper.getSiegePlayerByEntity(entityPlayer);
            if (siegePlayer != null) {
                if (siegePlayer.isRapelling()) {
                    GlStateManager.pushMatrix();
                    GlStateManager.enableRescaleNormal();
                    EnumFacing enumFacing = siegePlayer.getEnumFacing();
                    switch (enumFacing) {
                        case EAST:
                            GlStateManager.rotate(90, 0, 0, 1);
                            break;
                        case WEST:
                            GlStateManager.rotate(-90, 0, 0, 1);
                            break;
                        case NORTH:
                            GlStateManager.rotate(90, 1, 0, 0);
                            break;
                        case SOUTH:
                            GlStateManager.rotate(-90, 1, 0, 0);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onRenderPost(RenderLivingEvent.Post event) {
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void renderHand(RenderHandEvent event) {
        if (SiegeHelper.isDroning(Minecraft.getMinecraft().player)) event.setCanceled(true);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void renderArmorHud(RenderGameOverlayEvent.Pre event) {
        Minecraft mc = Minecraft.getMinecraft();
        if ((event.getType().equals(RenderGameOverlayEvent.ElementType.HOTBAR))) {
            if (SiegeHelper.isDroning(mc.player)) {
                ScaledResolution scaledResolution = new ScaledResolution(mc);
                GlStateManager.pushMatrix();
                GlStateManager.color(1.0F, 1.0F, 1.0F, 0.2F);
                Minecraft.getMinecraft().getTextureManager().bindTexture(droneHudTexture);
                drawTexturedQuadFit(0.0D, 0.0D, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), -100.0D);
                GlStateManager.popMatrix();
            }
            if (SiegeData.isEyenoxActive.get(mc.player) != null) {
                if (SiegeData.isEyenoxActive.get(mc.player)) {
                    ScaledResolution scaledResolution = new ScaledResolution(mc);
                    GlStateManager.pushMatrix();
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 0.15F);
                    Minecraft.getMinecraft().getTextureManager().bindTexture(jackalHudTexture);
                    drawTexturedQuadFit(0.0D, 0.0D, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), -100.0D);
                    GlStateManager.popMatrix();
                }
            }
            if (!SiegeHelper.isDroning(mc.player) && mc.player.isPotionActive(Objects.requireNonNull(Potion.getPotionById(22)))) {
                Operator operator = SiegeHelper.getOperator(mc.player);
                if (operator != null) {
                    if (operator.getEnumTeam().equals(Operator.Team.ATTACKER)) {
                        ScaledResolution scaledResolution = new ScaledResolution(mc);
                        GlStateManager.pushMatrix();
                        int randomTick = 0;
                        if (count == 25) {
                            randomTick = random.nextInt(4);
                            count = 0;
                        } else {
                            count++;
                        }
                        GlStateManager.color((float) (1.0 - (randomTick / 4)), (float) (1.0 - (randomTick / 4)), (float) (1.0 - (randomTick / 4)), 0.25F);
                        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Siege.MODID, "textures/gui/adrenaline_hud_" + randomTick + ".png"));
                        drawTexturedQuadFit(0.0D, 0.0D, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), -100.0D);
                        GlStateManager.popMatrix();
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void renderHud(RenderGameOverlayEvent.Pre event) {
        Minecraft mc = Minecraft.getMinecraft();
        if ((event.getType().equals(RenderGameOverlayEvent.ElementType.HOTBAR))) {
            ScaledResolution scaledResolution = new ScaledResolution(mc);

            if (SiegeData.teamA != null || SiegeData.teamB != null) {
                GlStateManager.pushMatrix();
                int xPos = scaledResolution.getScaledWidth();
                int yPos = 4;
                int i = scaledResolution.getScaledWidth() / 2;
                mc.ingameGUI.drawCenteredString(mc.fontRenderer, "" + SiegeData.teamA.getScore(), i - 33, yPos + 4, -1);
                mc.ingameGUI.drawCenteredString(mc.fontRenderer, "" + SiegeData.teamB.getScore(), i + 31, yPos + 4, -1);
                mc.ingameGUI.drawCenteredString(mc.fontRenderer, "0:00", (xPos / 2) - 1, yPos + 2, -1);
                mc.ingameGUI.drawCenteredString(mc.fontRenderer, "ROUND " + SiegeData.roundNumber, (xPos / 2) - 1, yPos + 12, -1);
                int start = 150;
                for (SiegePlayer siegePlayer : SiegeData.teamA.getPlayers()) {
                    ResourceLocation loc = none;
                    if (siegePlayer.getOperator() != null) {
                        loc = siegePlayer.getOperator().getIcon();
                    }
                    if (siegePlayer.isAlive()) {
                        GlStateManager.color(1F, 1F, 1F, 0.9f);
                    } else {
                        GlStateManager.color(0.5F, 0.5F, 0.5F, 0.9f);
                    }
                    Minecraft.getMinecraft().getTextureManager().bindTexture(loc);
                    drawIcon((i - start), yPos + 1);
                    start = (start - 15);
                }
                int start2 = -130;

                for (SiegePlayer siegePlayer : SiegeData.teamB.getPlayers()) {
                    ResourceLocation loc = none;
                    if (siegePlayer.getOperator() != null)
                        loc = siegePlayer.getOperator().getIcon();
                    Minecraft.getMinecraft().getTextureManager().bindTexture(loc);
                    if (siegePlayer.isAlive()) {
                        GlStateManager.color(1F, 1F, 1F, 0.9F);
                    } else {
                        GlStateManager.color(0.5F, 0.5F, 0.5F, 0.9F);
                    }
                    drawIcon((i - start2), yPos + 1);
                    start2 = (start2 + 15);
                }
                GlStateManager.color(1.0F, 1.0F, 1.0F, 0.95F);
                mc.getTextureManager().bindTexture(hudTexture);
                mc.ingameGUI.drawTexturedModalRect((float) ((i) - 130), yPos, 0, 0, 256, 16);
                GlStateManager.popMatrix();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static void drawIcon(int x, int y) {
        final float uScale = 16f / 0x100;
        final float vScale = 16f / 0x100;
        int height = 16, width = 16, zLevel = 0;
        int u = 0, v = 0;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder wr = tessellator.getBuffer();
        wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        wr.pos(x, y + height, zLevel).tex(u * uScale, ((v + height) * vScale)).endVertex();
        wr.pos(x + width, y + height, zLevel).tex((u + width) * uScale, ((v + height) * vScale)).endVertex();
        wr.pos(x + width, y, zLevel).tex((u + width) * uScale, (v * vScale)).endVertex();
        wr.pos(x, y, zLevel).tex(u * uScale, (v * vScale)).endVertex();
        tessellator.draw();
    }
}
