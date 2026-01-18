package pers.ketikai.minecraft.forge.dragonbloom.listener;

import blockbuster.BedrockMaterial;
import blockbuster.render.BloomHelper;
import eos.moe.dragoncore.eca;
import eos.moe.dragoncore.kea;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;
import pers.ketikai.minecraft.forge.dragonbloom.DragonBloom;
import pers.ketikai.minecraft.forge.dragonbloom.DragonBloomHook;
import pers.ketikai.minecraft.forge.dragonbloom.packet.event.ForgePacketReceivedEvent;
import pers.ketikai.minecraft.protocol.dragonbloom.config.Configuration;

public class ConfigurationListener {

//    @SubscribeEvent(priority = EventPriority.LOWEST)
//    @SideOnly(Side.CLIENT)
//    public void on(RenderLivingEvent.Pre<EntityLivingBase> event) {
//        EntityLivingBase entity = event.getEntity();
//        kea kea = eca.t.ALLATORIxDEMO(entity);
//        DragonBloomHook.hookEeaFunc_77036_a0(
//                kea, null, entity,0,0,0,0,0,0
//        );
//    }
//
//    @SubscribeEvent(priority = EventPriority.HIGHEST)
//    @SideOnly(Side.CLIENT)
//    public void on(RenderLivingEvent.Post<EntityLivingBase> event) {
//        EntityLivingBase entity = event.getEntity();
//        kea kea = eca.t.ALLATORIxDEMO(entity);
//        DragonBloomHook.hookEeaFunc_77036_a1(
//                kea, null, entity,0,0,0,0,0,0
//        );
//    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    @SideOnly(Side.CLIENT)
    public void on(RenderLivingEvent.Pre<EntityLivingBase> event) {
        EntityLivingBase entity = event.getEntity();
        kea kea = eca.t.ALLATORIxDEMO(entity);
        DragonBloomHook.hookEeaFunc_77036_a0(
                kea, null, entity,0,0,0,0,0,0
        );
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    @SideOnly(Side.CLIENT)
    public void on(RenderLivingEvent.Post<EntityLivingBase> event) {
        EntityLivingBase entity = event.getEntity();
        kea kea = eca.t.ALLATORIxDEMO(entity);
        DragonBloomHook.hookEeaFunc_77036_a1(
                kea, null, entity,0,0,0,0,0,0
        );
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void on(ForgePacketReceivedEvent event) {
        Object payload = event.getPayload();
        Logger logger = DragonBloom.getLogger();
        logger.info("接收到远程数据");
        if (payload instanceof Configuration) {
            DragonBloom.setConfiguration(((Configuration) payload).compile());
            logger.info("已加载远程配置");
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void on(FMLNetworkEvent.ServerDisconnectionFromClientEvent event) {
        DragonBloom.setConfiguration(null);
        DragonBloom.getLogger().debug("已清除远程配置");
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void on(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        DragonBloom.setConfiguration(null);
        DragonBloom.getLogger().debug("已清除远程配置");
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void on(PlayerEvent.PlayerLoggedOutEvent event) {
        DragonBloom.setConfiguration(null);
        DragonBloom.getLogger().debug("已清除远程配置");
    }
}
