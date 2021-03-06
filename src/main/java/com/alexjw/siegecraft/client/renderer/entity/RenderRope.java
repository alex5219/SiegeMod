package com.alexjw.siegecraft.client.renderer.entity;

import com.alexjw.siegecraft.Siege;
import com.alexjw.siegecraft.client.model.ModelRope;
import com.alexjw.siegecraft.server.entity.EntityRope;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderRope extends RenderLiving<EntityRope> {

    public RenderRope(RenderManager manager) {
        super(manager, new ModelRope(), 0.1f);
    }

    protected ResourceLocation getEntityTexture(EntityRope entity) {
        return new ResourceLocation(Siege.MODID, "textures/models/rope.png");
    }
}