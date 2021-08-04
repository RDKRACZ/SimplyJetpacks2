package stormedpanda.simplyjetpacks.lists;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import stormedpanda.simplyjetpacks.SimplyJetpacks;

public enum ArmorMaterialList implements ArmorMaterial {

	PILOT_GOGGLES("pilot_goggles", 0, new int[] {0, 0, 0, 0}, 0, Items.IRON_INGOT, "item.armor.equip_leather", 0.0f, 0.0f),
	JETPACK("jetpack", 0, new int[] {0, 0, 2, 0}, 10, Items.IRON_INGOT, "item.armor.equip_iron", 0.0f, 0.0f),
	JETPACK_ARMORED("jetpack_armored", 0, new int[] {0, 0, 4, 0}, 10, Items.IRON_INGOT, "item.armor.equip_iron", 0.0f, 0.0f),
	JETPLATE("jetplate", 0, new int[] {0, 0, 12, 0}, 10, Items.IRON_INGOT, "item.armor.equip_iron", 3.0f, 3.0f);

	private final String name;
	private final int durability;
	private final int[] damageReductionAmounts;
	private int enchantability;
	private final Item repairItem;
	private final String equipSound;
	private final float toughness;
	private final float knockbackResistance;
	private static final int[] max_damage_array = new int[] {13, 15, 16, 11};

	ArmorMaterialList(String name, int durability, int[] defense, int enchantability, Item repairItem, String equipSound, float toughness, float knockbackResistance) {
		this.name = name;
		this.durability = durability;
		this.damageReductionAmounts = defense;
		this.enchantability = enchantability;
		this.repairItem = repairItem;
		this.equipSound = equipSound;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
	}

	public static void setStats(ArmorMaterialList armor, boolean isArmored, int enchant, int defense) {
		defense = isArmored ? defense : (defense - 1) / 2;
		armor.enchantability = enchant;
		armor.damageReductionAmounts[EquipmentSlot.CHEST.getIndex()] = defense;
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantability;
	}

	@Override
	public SoundEvent getEquipSound() {
		return new SoundEvent(new ResourceLocation(equipSound));
	}

	@Override
	public Ingredient getRepairIngredient() {
		//return Ingredient.fromItems(this.repairItem);
		return null;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public String getName() {
		return SimplyJetpacks.MODID + ":" + this.name;
	}

	@Override
	public int getDurabilityForSlot(EquipmentSlot slotIn) {
		return max_damage_array[slotIn.getIndex()] * this.durability;
	}

	@Override
	public int getDefenseForSlot(EquipmentSlot slotIn) {
		return this.damageReductionAmounts[slotIn.getIndex()];
	}

	@Override
	public float getToughness() {
		return this.toughness;
	}

	@Override
	public float getKnockbackResistance() {
		return this.knockbackResistance;
	}
}
