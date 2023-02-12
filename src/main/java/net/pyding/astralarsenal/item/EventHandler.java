package net.pyding.astralarsenal.item;

import net.minecraft.block.Blocks;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.ai.brain.task.AttackTargetTask;
import net.minecraft.entity.ai.brain.task.RunAwayTask;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.apache.logging.log4j.core.net.Priority;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static net.pyding.astralarsenal.item.ModItems.AGGRO;

@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void onEntityTick(LivingEvent.LivingUpdateEvent event)
    {
        if(event.getEntityLiving() instanceof MobEntity) {
            MobEntity entity = (MobEntity) event.getEntityLiving();
            if(entity.isGlowing() && entity.getAttackTarget() == null)
            entity.setGlowing(false);
        }
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerTickUpdate(TickEvent.PlayerTickEvent event)
    {
        PlayerEntity player = (PlayerEntity) event.player;
        World world = player.world;
        double x = player.getPosX();
        double y = player.getPosY();
        double z = player.getPosZ();
        if(player.getHeldItemMainhand().getOrCreateTag().getBoolean("aggro") && player.isCreative() == false) {
            List<CreatureEntity> _entfound = world
                    .getEntitiesWithinAABB(CreatureEntity.class,
                            new AxisAlignedBB(x - (10 / 2d), y - (10 / 2d), z - (10 / 2d), x + (10 / 2d), y + (10 / 2d), z + (10 / 2d)), null)
                    .stream().sorted(new Object() {
                        Comparator<MobEntity> compareDistOf(double _x, double _y, double _z) {
                            return Comparator.comparing(_entcnd -> _entcnd.getDistanceSq(_x, _y, _z));
                        }
                    }.compareDistOf(x, y, z)).collect(Collectors.toList());
            for (CreatureEntity entityiterator : _entfound) {
                if(!(entityiterator instanceof MonsterEntity)) {
                    if (entityiterator instanceof WaterMobEntity &&
                            !(world.getBlockState(new BlockPos(x + 1, y, z)).getBlock() == Blocks.AIR) &&
                            !(world.getBlockState(new BlockPos(x - 1, y, z)).getBlock() == Blocks.AIR) &&
                            !(world.getBlockState(new BlockPos(x, y, z + 1)).getBlock() == Blocks.AIR) &&
                            !(world.getBlockState(new BlockPos(x, y, z - 1)).getBlock() == Blocks.AIR) &&
                            !(world.getBlockState(new BlockPos(x, y + 1, z)).getBlock() == Blocks.AIR)) {
                        entityiterator.setAggroed(true);
                        entityiterator.setAttackTarget(player);
                        entityiterator.setGlowing(true);
                    } else {
                        entityiterator.setAggroed(true);
                        entityiterator.setAttackTarget(player);
                        entityiterator.setGlowing(true);
                    }
                    entityiterator.goalSelector.addGoal(8, new TemptGoal(entityiterator, 2.5D, Ingredient.fromItems(AGGRO.get()), false));
                    if(entityiterator.getDistance(player) <= 2)
                        player.attackEntityFrom(new DamageSource("moral damage").setDifficultyScaled(),1);
                } else {
                    entityiterator.setAggroed(true);
                    entityiterator.setAttackTarget(player);
                    entityiterator.setGlowing(true);
                }
            }
        }
    }
}
