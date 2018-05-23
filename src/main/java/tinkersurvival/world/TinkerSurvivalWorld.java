package tinkersurvival.world;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemMultiTexture;

import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.registries.IForgeRegistry;

import tinkersurvival.TinkerSurvival;
import tinkersurvival.world.block.BlockRock;
import tinkersurvival.world.item.ItemBase;
import tinkersurvival.world.item.ItemRock;
import tinkersurvival.world.item.TinkerSurvivalArmor;

public class TinkerSurvivalWorld {

    private static final List<Item> all = new ArrayList<>();
    private static final List<ItemStack> rocks = new ArrayList<>();
    public static ArmorMaterial reinforced_wool_armor_material;
    public static ArmorMaterial reinforced_jelled_slime_armor_material;
    
	public static TinkerSurvivalArmor reinforced_wool_helmet;
    public static TinkerSurvivalArmor reinforced_wool_chestplate;
    public static TinkerSurvivalArmor reinforced_wool_leggings;
    public static TinkerSurvivalArmor reinforced_wool_boots;
    public static TinkerSurvivalArmor reinforced_jelled_slime_helmet;
    public static TinkerSurvivalArmor reinforced_jelled_slime_chestplate;
    public static TinkerSurvivalArmor reinforced_jelled_slime_leggings;
    public static TinkerSurvivalArmor reinforced_jelled_slime_boots;

    public static BlockRock looseRock;
    public static ItemRock rockStone;
    public static ItemBase grassFiber;
    public static ItemBase grassString;
    public static ItemBase flintShard;

    public static void initItemRepairMaterials() {
        if (Loader.isModLoaded("basemetals")) {
            ItemStack material = new ItemStack(Item.getByNameOrId("basemetals:adamantine_ingot"));
            reinforced_wool_armor_material.setRepairItem(material);
            reinforced_jelled_slime_armor_material.setRepairItem(material);
        }
    }

    public static void init() {
        float reinforced_hardness = 12.0f;
        float reinforced_strength = 100.0f;
        int reinforced_durability = 200;
        int reinforced_enchantability = 25;
        float reinforced_toughness = (reinforced_hardness / 5);

        reinforced_wool_armor_material = EnumHelper.addArmorMaterial(
            "REINFORCED_WOOL",
            TinkerSurvival.MODID + ":reinforced_wool_armor",
            reinforced_durability,
            getDamageReduction(reinforced_hardness),
            reinforced_enchantability,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
            reinforced_toughness
        );

        reinforced_jelled_slime_armor_material = EnumHelper.addArmorMaterial(
            "REINFORCED_JELLED_SLIME",
            TinkerSurvival.MODID + ":reinforced_jelled_slime_armor",
            reinforced_durability,
            getDamageReduction(reinforced_hardness),
            reinforced_enchantability,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
            reinforced_toughness
        );

        reinforced_wool_helmet = getArmor(reinforced_wool_helmet, reinforced_wool_armor_material, EntityEquipmentSlot.HEAD, "reinforced_wool_helmet");
        reinforced_wool_chestplate = getArmor(reinforced_wool_chestplate, reinforced_wool_armor_material, EntityEquipmentSlot.CHEST, "reinforced_wool_chestplate");
        reinforced_wool_leggings = getArmor(reinforced_wool_leggings, reinforced_wool_armor_material, EntityEquipmentSlot.LEGS, "reinforced_wool_leggings");
        reinforced_wool_boots = getArmor(reinforced_wool_boots, reinforced_wool_armor_material, EntityEquipmentSlot.FEET, "reinforced_wool_boots");
        reinforced_jelled_slime_helmet = getArmor(reinforced_jelled_slime_helmet, reinforced_jelled_slime_armor_material, EntityEquipmentSlot.HEAD, "reinforced_jelled_slime_helmet");
        reinforced_jelled_slime_chestplate = getArmor(reinforced_jelled_slime_chestplate, reinforced_jelled_slime_armor_material, EntityEquipmentSlot.CHEST, "reinforced_jelled_slime_chestplate");
        reinforced_jelled_slime_leggings = getArmor(reinforced_jelled_slime_leggings, reinforced_jelled_slime_armor_material, EntityEquipmentSlot.LEGS, "reinforced_jelled_slime_leggings");
        reinforced_jelled_slime_boots = getArmor(reinforced_jelled_slime_boots, reinforced_jelled_slime_armor_material, EntityEquipmentSlot.FEET, "reinforced_jelled_slime_boots");

        looseRock = getLooseRock(looseRock, "loose_rock");
        rockStone = getRock(rockStone, "rock");
        grassFiber = getItem(grassFiber, "grass_fiber");
        grassString = getItem(grassString, "grass_string");
        flintShard = getItem(flintShard, "flint_shard");
    }

	private static TinkerSurvivalArmor getArmor(TinkerSurvivalArmor armor, ArmorMaterial material, EntityEquipmentSlot slot, String name) {
        armor = new TinkerSurvivalArmor(material, slot, name);
        all.add(armor);
        return armor;
    }

    private static BlockRock getLooseRock(BlockRock block, String name) {
        return new BlockRock(name);
    }

    private static ItemRock getRock(ItemRock rock, String name) {
        rock = new ItemRock(name);
        all.add(rock);
        return rock;
    }

    private static ItemBase getItem(ItemBase item, String name) {
        item = new ItemBase(name);
        all.add(item);
        return item;
    }

    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        all.forEach(registry::register);

        event.getRegistry().registerAll(
            new ItemMultiTexture(looseRock, looseRock, looseRock::getStoneName).setRegistryName(looseRock.name)
        );
    }

    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
            looseRock
        );
    }

    public static void registerItemModels() {
        for (int i=0; i < 4; i++) {
            ItemStack rock = new ItemStack(rockStone, 1, i);
            rocks.add(rock);
            TinkerSurvival.proxy.registerItemModelWithVariant(
                rockStone,
                i,
                rockStone.name + "_" +  rockStone.getStoneName(rock),
                "inventory"
            );
        }

        all.forEach(item -> {
            if (!(item instanceof ItemRock)) {
                TinkerSurvival.proxy.registerItemModel(item);
            }
        });
    }

    public static void registerBlockModels() {
        for (int i=0; i < 4; i++) {
            TinkerSurvival.proxy.registerItemModelWithVariant(
                Item.getItemFromBlock(looseRock),
                i,
                looseRock.name,
                "type=" + looseRock.getStoneName(i)
            );
        }
    }

    public static List<ItemStack> listAllRocks() {
        return rocks;
    }
    
	public static int[] getDamageReduction(float hardness) {
		int[] protection = new int[4];
		float minimum = 5f;
		float hardnessFactor = 1.25f;
		float total = (hardnessFactor * hardness) + minimum;
		int feetIndex = EntityEquipmentSlot.FEET.getIndex();
		int legsIndex = EntityEquipmentSlot.LEGS.getIndex();
		int chestIndex = EntityEquipmentSlot.CHEST.getIndex();
		int headIndex = EntityEquipmentSlot.HEAD.getIndex();
		protection[headIndex] = Math.round(0.1f * total); // head
		protection[chestIndex] = Math.round(0.4f * total); // torso
		protection[legsIndex] = Math.round(0.35f * total); // legs
		protection[feetIndex] = Math.round(0.15f * total); // feet
		return protection;
    }
}