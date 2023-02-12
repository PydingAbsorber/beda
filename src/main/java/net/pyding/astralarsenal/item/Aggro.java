package net.pyding.astralarsenal.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class Aggro extends Item implements IItemProvider {
    public Aggro(Item.Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ActionResult<ItemStack> retval = super.onItemRightClick(world, player, hand);
        ItemStack itemstack = retval.getResult();
        if (itemstack.getOrCreateTag().getBoolean("aggro") == false) {
            itemstack.getOrCreateTag().putBoolean("aggro", true);
        } else {
            itemstack.getOrCreateTag().putBoolean("aggro", false);
        }
        return retval;
    }
    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean hasEffect(ItemStack itemstack) {
        if(itemstack.getOrCreateTag().getBoolean("aggro") == false)
            return false;
            else return true;
    }
}
