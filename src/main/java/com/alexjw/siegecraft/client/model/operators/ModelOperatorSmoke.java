package com.alexjw.siegecraft.client.model.operators;// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelOperatorSmoke extends ModelBiped {

    public ModelOperatorSmoke() {
        super(0.1f, 0.0f, 128, 128);
        textureWidth = 128;
        textureHeight = 128;
        this.bipedLeftArm.mirror = false;
        this.bipedLeftArm = new ModelRenderer(this);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.bipedLeftArm.cubeList.add(new ModelBox(bipedLeftArm, 32, 48, -1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F, false));
        this.bipedLeftArm.mirror = false;

        ModelRenderer bipedHeadStrap2 = new ModelRenderer(this);
        bipedHeadStrap2.setRotationPoint(4.2F, -6.0F, -1.0F);
        bipedHead.addChild(bipedHeadStrap2);
        setRotationAngle(bipedHeadStrap2, 0.0F, 0.0F, 0.3491F);
        bipedHeadStrap2.cubeList.add(new ModelBox(bipedHeadStrap2, 92, 56, -8.0F, 2.0F, 4.224F, 9, 1, 1, 0.0F, false));

        ModelRenderer bipedHeadStrap1 = new ModelRenderer(this);
        bipedHeadStrap1.setRotationPoint(4.2F, -6.0F, -1.0F);
        bipedHead.addChild(bipedHeadStrap1);
        setRotationAngle(bipedHeadStrap1, 0.0F, 0.0F, -0.3491F);
        bipedHeadStrap1.cubeList.add(new ModelBox(bipedHeadStrap1, 94, 56, -8.756F, -1.0F, 4.2144F, 9, 1, 1, 0.0F, false));

        ModelRenderer bipedHeadLenses = new ModelRenderer(this);
        bipedHeadLenses.setRotationPoint(3.0F, 21.0F, -5.0F);
        bipedHead.addChild(bipedHeadLenses);
        bipedHeadLenses.cubeList.add(new ModelBox(bipedHeadLenses, 101, 56, -2.0F, -26.0F, 0.35F, 2, 2, 1, 0.0F, false));
        bipedHeadLenses.cubeList.add(new ModelBox(bipedHeadLenses, 7, 8, -7.0F, -28.0F, 0.6F, 8, 7, 1, 0.0F, false));
        bipedHeadLenses.cubeList.add(new ModelBox(bipedHeadLenses, 7, 8, -7.0F, -28.0F, 0.6F, 8, 7, 1, 0.0F, false));
        bipedHeadLenses.cubeList.add(new ModelBox(bipedHeadLenses, 10, 8, -7.0F, -29.0F, 0.6F, 2, 1, 1, 0.0F, false));
        bipedHeadLenses.cubeList.add(new ModelBox(bipedHeadLenses, 11, 8, -1.0F, -29.0F, 0.6F, 2, 1, 1, 0.0F, false));
        bipedHeadLenses.cubeList.add(new ModelBox(bipedHeadLenses, 12, 14, -0.75F, -28.0F, 3.1F, 2, 1, 1, 0.0F, false));
        bipedHeadLenses.cubeList.add(new ModelBox(bipedHeadLenses, 5, 11, -7.25F, -28.0F, 3.1F, 2, 1, 1, 0.0F, false));
        bipedHeadLenses.cubeList.add(new ModelBox(bipedHeadLenses, 14, 6, -0.75F, -25.0F, 3.1F, 2, 1, 1, 0.0F, false));
        bipedHeadLenses.cubeList.add(new ModelBox(bipedHeadLenses, 8, 7, -7.25F, -25.0F, 3.1F, 2, 1, 1, 0.0F, false));
        bipedHeadLenses.cubeList.add(new ModelBox(bipedHeadLenses, 11, 8, 0.25F, -28.75F, 1.35F, 1, 8, 1, 0.0F, false));
        bipedHeadLenses.cubeList.add(new ModelBox(bipedHeadLenses, 11, 8, -7.25F, -28.75F, 1.35F, 1, 8, 1, 0.0F, false));
        bipedHeadLenses.cubeList.add(new ModelBox(bipedHeadLenses, 11, 8, 0.25F, -28.0F, 2.1F, 1, 5, 1, 0.0F, false));
        bipedHeadLenses.cubeList.add(new ModelBox(bipedHeadLenses, 11, 8, -7.25F, -28.0F, 2.1F, 1, 5, 1, 0.0F, false));
        bipedHeadLenses.cubeList.add(new ModelBox(bipedHeadLenses, 87, 98, -2.5F, -26.5F, 0.1F, 3, 3, 1, -0.4F, false));
        bipedHeadLenses.cubeList.add(new ModelBox(bipedHeadLenses, 87, 98, -6.5F, -26.5F, 0.1F, 3, 3, 1, -0.4F, false));
        bipedHeadLenses.cubeList.add(new ModelBox(bipedHeadLenses, 103, 57, -6.0F, -26.0F, 0.35F, 2, 2, 1, 0.0F, false));
        bipedHeadLenses.cubeList.add(new ModelBox(bipedHeadLenses, 105, 56, -7.12F, -25.0F, 4.0F, 1, 1, 5, 0.0F, false));
        bipedHeadLenses.cubeList.add(new ModelBox(bipedHeadLenses, 103, 58, -7.2F, -28.0F, 4.0F, 1, 1, 5, 0.0F, false));
        bipedHeadLenses.cubeList.add(new ModelBox(bipedHeadLenses, 102, 54, 0.2F, -28.0F, 4.0F, 1, 1, 5, 0.0F, false));
        bipedHeadLenses.cubeList.add(new ModelBox(bipedHeadLenses, 102, 57, 0.182F, -25.0F, 4.0F, 1, 1, 5, 0.0F, false));
        bipedHeadLenses.cubeList.add(new ModelBox(bipedHeadLenses, 106, 56, -4.0F, -27.0F, 8.44F, 2, 2, 1, 0.0F, false));

        ModelRenderer bipedHeadGasMask = new ModelRenderer(this);
        bipedHeadGasMask.setRotationPoint(0.0F, 24.0F, 11.0F);
        bipedHead.addChild(bipedHeadGasMask);
        setRotationAngle(bipedHeadGasMask, 0.3491F, 0.0F, 0.0F);
        bipedHeadGasMask.cubeList.add(new ModelBox(bipedHeadGasMask, 30, 98, -1.0F, -30.5283F, -6.7462F, 2, 2, 2, 0.0F, false));
        bipedHeadGasMask.cubeList.add(new ModelBox(bipedHeadGasMask, 30, 98, 0.25F, -29.9126F, -6.3403F, 1, 1, 2, 0.0F, false));
        bipedHeadGasMask.cubeList.add(new ModelBox(bipedHeadGasMask, 30, 98, -1.25F, -29.9126F, -6.3403F, 1, 1, 2, 0.0F, false));
        bipedHeadGasMask.cubeList.add(new ModelBox(bipedHeadGasMask, 35, 105, -0.4528F, -31.892F, -5.352F, 1, 2, 1, 0.0F, false));
        bipedHeadGasMask.cubeList.add(new ModelBox(bipedHeadGasMask, 90, 102, -1.0F, -30.4777F, -7.8941F, 2, 2, 1, 0.2F, false));
        bipedHeadGasMask.cubeList.add(new ModelBox(bipedHeadGasMask, 103, 63, -0.46F, -30.1053F, -8.2361F, 1, 1, 1, 0.0F, false));

        ModelRenderer bipedBodyVest = new ModelRenderer(this);
        bipedBodyVest.setRotationPoint(0.0F, 3.0F, -3.0F);
        bipedBody.addChild(bipedBodyVest);
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 26, 95, -4.0F, -1.0F, 0.6F, 8, 9, 1, 0.0F, false));
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 39, 101, 2.0F, -3.0F, 0.78F, 2, 2, 1, 0.0F, false));
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 42, 101, -4.0F, -3.0F, 0.78F, 2, 2, 1, 0.0F, false));
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 38, 100, -4.0F, -3.04F, 1.0F, 2, 1, 4, 0.0F, false));
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 30, 100, 2.0F, -3.04F, 1.0F, 2, 1, 4, 0.0F, false));
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 104, 58, -1.6F, 4.6F, 0.0F, 1, 2, 1, 0.0F, false));
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 94, 58, -4.0256F, 8.0F, 0.2797F, 8, 1, 5, 0.0F, false));
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 102, 54, -0.506F, -1.0F, 0.36F, 1, 9, 1, 0.0F, false));
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 88, 57, -3.104F, 0.0F, 5.0F, 3, 2, 1, 0.0F, false));
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 88, 55, 0.3F, 0.0F, 5.0F, 3, 2, 1, 0.0F, false));
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 36, 96, -4.28F, 0.0F, 1.2F, 1, 7, 4, 0.0F, false));
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 36, 97, 3.4F, 0.0F, 1.2F, 1, 7, 4, 0.0F, false));
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 112, 57, 1.2F, 0.0F, 0.0F, 1, 2, 1, 0.0F, false));
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 36, 101, 1.2F, 0.0F, -0.25F, 1, 1, 1, -0.1F, false));
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 36, 101, -1.55F, 4.75F, -0.25F, 1, 1, 1, -0.1F, false));
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 36, 101, 2.45F, 0.0F, -0.25F, 1, 1, 1, -0.1F, false));
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 36, 101, 1.2F, 4.0F, -0.25F, 2, 1, 1, -0.1F, false));
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 103, 56, 2.48F, 0.0F, 0.0F, 1, 2, 1, 0.0F, false));
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 105, 61, 1.24F, 4.0F, 0.0F, 2, 3, 1, 0.0F, false));
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 84, 58, -3.0F, 3.0F, 5.0F, 6, 1, 1, 0.0F, false));
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 38, 102, -4.0F, -3.0F, 4.32F, 2, 2, 1, 0.0F, false));
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 28, 95, -4.0F, -1.0F, 4.6F, 8, 8, 1, 0.0F, false));
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 37, 99, 2.0F, -3.0F, 4.3392F, 2, 2, 1, 0.0F, false));
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 108, 58, -3.0F, 6.28F, 0.0F, 1, 1, 1, 0.0F, false));
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 105, 61, -3.0F, 5.0F, 0.0F, 1, 1, 1, 0.0F, false));
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 102, 59, -3.0F, 3.68F, 0.0F, 1, 1, 1, 0.0F, false));
        bipedBodyVest.cubeList.add(new ModelBox(bipedBodyVest, 105, 59, -3.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F, false));

        ModelRenderer bipedBodyVestAir = new ModelRenderer(this);
        bipedBodyVestAir.setRotationPoint(2.46F, 8.0F, 2.0F);
        bipedBody.addChild(bipedBodyVestAir);
        setRotationAngle(bipedBodyVestAir, 0.0F, 0.0F, -0.7854F);
        bipedBodyVestAir.cubeList.add(new ModelBox(bipedBodyVestAir, 92, 55, -4.3587F, -3.7941F, 0.0F, 2, 2, 1, 0.0F, false));
        bipedBodyVestAir.cubeList.add(new ModelBox(bipedBodyVestAir, 89, 55, -1.6303F, -1.0032F, 0.0F, 2, 2, 1, 0.0F, false));

        ModelRenderer bipedBodyVestWalkieTalkie = new ModelRenderer(this);
        bipedBodyVestWalkieTalkie.setRotationPoint(0.0F, 2.0F, -2.0F);
        bipedBody.addChild(bipedBodyVestWalkieTalkie);
        setRotationAngle(bipedBodyVestWalkieTalkie, 0.0F, 0.0F, 0.4363F);
        bipedBodyVestWalkieTalkie.cubeList.add(new ModelBox(bipedBodyVestWalkieTalkie, 104, 56, 2.0F, -2.44F, -0.44F, 1, 2, 1, 0.0F, false));
        bipedBodyVestWalkieTalkie.cubeList.add(new ModelBox(bipedBodyVestWalkieTalkie, 104, 56, 2.804F, -3.452F, -0.44F, 0, 2, 1, -0.2F, false));

        ModelRenderer bipedRightArmFlag = new ModelRenderer(this);
        bipedRightArmFlag.setRotationPoint(5.0F, 22.0F, 0.0F);
        bipedRightArm.addChild(bipedRightArmFlag);
        bipedRightArmFlag.cubeList.add(new ModelBox(bipedRightArmFlag, 0, 0, -8.2128F, -23.0F, -1.3502F, 1, 2, 3, -0.2F, false));

        ModelRenderer bipedRightLegBandolier = new ModelRenderer(this);
        bipedRightLegBandolier.setRotationPoint(1.9F, 12.0F, 0.0F);
        bipedRightLeg.addChild(bipedRightLegBandolier);
        bipedRightLegBandolier.cubeList.add(new ModelBox(bipedRightLegBandolier, 102, 84, -4.28F, -9.0F, -2.16F, 4, 1, 4, 0.0F, false));
        bipedRightLegBandolier.cubeList.add(new ModelBox(bipedRightLegBandolier, 110, 76, -4.0F, -9.0F, 1.1648F, 4, 1, 1, 0.0F, false));
        bipedRightLegBandolier.cubeList.add(new ModelBox(bipedRightLegBandolier, 87, 58, -2.098F, -9.52F, -2.4108F, 1, 2, 1, 0.0F, false));
        bipedRightLegBandolier.cubeList.add(new ModelBox(bipedRightLegBandolier, 91, 56, -3.44F, -9.56F, -2.4F, 1, 2, 1, 0.0F, false));
        bipedRightLegBandolier.cubeList.add(new ModelBox(bipedRightLegBandolier, 112, 82, -0.7F, -9.0F, -2.0F, 1, 1, 4, 0.0F, false));
        bipedRightLegBandolier.cubeList.add(new ModelBox(bipedRightLegBandolier, 99, 58, -4.6F, -10.0F, -1.0F, 1, 3, 2, 0.0F, false));

        ModelRenderer bipedRightLegStrap = new ModelRenderer(this);
        bipedRightLegStrap.setRotationPoint(0.0F, -9.0F, -3.0F);
        bipedRightLegBandolier.addChild(bipedRightLegStrap);
        setRotationAngle(bipedRightLegStrap, 0.0F, 0.0F, -0.6109F);
        bipedRightLegStrap.cubeList.add(new ModelBox(bipedRightLegStrap, 109, 88, -1.0F, -5.0F, 0.858F, 1, 5, 1, 0.0F, false));
        bipedRightLegStrap.cubeList.add(new ModelBox(bipedRightLegStrap, 108, 82, -1.0F, -5.0F, 4.12F, 1, 5, 1, 0.0F, false));

        ModelRenderer bipedRightLegHolsterStrap = new ModelRenderer(this);
        bipedRightLegHolsterStrap.setRotationPoint(0.0F, -7.0F, -1.16F);
        bipedRightLegBandolier.addChild(bipedRightLegHolsterStrap);
        setRotationAngle(bipedRightLegHolsterStrap, 0.4363F, 0.0F, 0.0F);
        bipedRightLegHolsterStrap.cubeList.add(new ModelBox(bipedRightLegHolsterStrap, 111, 83, -4.2F, -5.0F, 2.0F, 1, 5, 1, 0.0F, false));

        ModelRenderer bipedLeftLegStrap = new ModelRenderer(this);
        bipedLeftLegStrap.setRotationPoint(6.1F, 8.0F, 0.0F);
        bipedLeftLeg.addChild(bipedLeftLegStrap);
        setRotationAngle(bipedLeftLegStrap, 0.0F, 0.0F, -1.0472F);
        bipedLeftLegStrap.cubeList.add(new ModelBox(bipedLeftLegStrap, 106, 83, 0.3F, -9.0F, -2.16F, 5, 1, 1, 0.0F, false));
        bipedLeftLegStrap.cubeList.add(new ModelBox(bipedLeftLegStrap, 111, 80, 0.3301F, -8.6962F, 1.12F, 5, 1, 1, 0.0F, false));

        ModelRenderer bipedLeftLegPouch = new ModelRenderer(this);
        bipedLeftLegPouch.setRotationPoint(6.1F, 8.0F, 0.0F);
        bipedLeftLeg.addChild(bipedLeftLegPouch);
        bipedLeftLegPouch.cubeList.add(new ModelBox(bipedLeftLegPouch, 26, 54, -6.64F, -4.0F, -2.22F, 2, 3, 1, 0.0F, false));
        bipedLeftLegPouch.cubeList.add(new ModelBox(bipedLeftLegPouch, 115, 81, -7.878F, -5.0F, -2.2032F, 1, 1, 1, 0.0F, false));
        bipedLeftLegPouch.cubeList.add(new ModelBox(bipedLeftLegPouch, 113, 85, -7.92F, -5.0F, 1.28F, 2, 1, 1, 0.0F, false));
        bipedLeftLegPouch.cubeList.add(new ModelBox(bipedLeftLegPouch, 110, 84, -8.33F, -5.0F, -2.0F, 1, 1, 4, 0.0F, false));
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}