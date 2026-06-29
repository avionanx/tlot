package lod.thelegendoftides;

import legend.core.GameEngine;
import legend.game.characters.ElementSet;
import legend.game.inventory.Equipment;
import legend.game.inventory.EquipmentRegistryEvent;
import legend.game.inventory.ItemIcon;
import legend.game.types.EquipmentSlot;
import legend.lodmod.LodMod;
import lod.thelegendoftides.icons.FishIcon;
import org.legendofdragoon.modloader.registries.Registrar;
import org.legendofdragoon.modloader.registries.RegistryDelegate;

public class TlotEquipments {
  private TlotEquipments() { }

  private static final Registrar<Equipment, EquipmentRegistryEvent> EQUIPMENT_REGISTRAR = new Registrar<>(GameEngine.REGISTRIES.equipment, Tlot.MOD_ID);

  // Model Swap Weapons
  // Dart
  public static final RegistryDelegate<Equipment> GLOWSTICK = EQUIPMENT_REGISTRAR.register("glowstick", () -> new Equipment(400, 0x0, EquipmentSlot.WEAPON, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 15, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, new FishIcon(12), 0, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0x0));
  public static final RegistryDelegate<Equipment> LIVART = EQUIPMENT_REGISTRAR.register("livart", () -> new Equipment(400, 0x0, EquipmentSlot.WEAPON, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 35, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, ItemIcon.SWORD, 0, 0, 30, 0, 0, 0, 0, 0, 0, 0, 0x0));
  public static final RegistryDelegate<Equipment> BRILLANTE = EQUIPMENT_REGISTRAR.register("brillante", () -> new Equipment(400, 0x0, EquipmentSlot.WEAPON, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 65, 0, 0, 0, 0, 20, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, ItemIcon.SWORD, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0));
  public static final RegistryDelegate<Equipment> ERICCIL = EQUIPMENT_REGISTRAR.register("ericcil", () -> new Equipment(400, 0x0, EquipmentSlot.WEAPON, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 20, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, ItemIcon.SWORD, 20, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0x0));
  // Alvitz
  public static final RegistryDelegate<Equipment> NAMELESS_SPEAR = EQUIPMENT_REGISTRAR.register("nameless_spear", () -> new Equipment(400, 0x0, EquipmentSlot.WEAPON, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 22, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, new FishIcon(14), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0));
  public static final RegistryDelegate<Equipment> ORTHOS_PRIME = EQUIPMENT_REGISTRAR.register("orthos_prime", () -> new Equipment(400, 0x0, EquipmentSlot.WEAPON, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 70, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, new FishIcon(30), 5, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0x0));
  public static final RegistryDelegate<Equipment> POOL_NOODLE = EQUIPMENT_REGISTRAR.register("pool_noodle", () -> new Equipment(400, 0x0, EquipmentSlot.WEAPON, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 25, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, new FishIcon(28), 0, 0, 0, 5, 5, 0, 0, 0, 0, 0, 0x0));
  // Sharanda
  public static final RegistryDelegate<Equipment> BIANCA = EQUIPMENT_REGISTRAR.register("bianca", () -> new Equipment(400, 0x0, EquipmentSlot.WEAPON, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 200, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, new FishIcon(13), 0, 0, -50, 0, 0, 0, 0, 0, 0, 0, 0x0));
  // Rose
  public static final RegistryDelegate<Equipment> ENERGY_SWORD = EQUIPMENT_REGISTRAR.register("energy_sword", () -> new Equipment(400, 0x0, EquipmentSlot.WEAPON, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 20, 0, 0, 0, 0, 10, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, new FishIcon(16), 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0x0));
  public static final RegistryDelegate<Equipment> KERNVITER = EQUIPMENT_REGISTRAR.register("kernviter", () -> new Equipment(400, 0x0, EquipmentSlot.WEAPON, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 80, 0, 0, 0, 0, 20, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, ItemIcon.SWORD, 0, 0, 40, 0, 0, 0, 0, 0, 0, 40, 0x8));
  // Haschel
  public static final RegistryDelegate<Equipment> PUFFERFISH_KNUCKLES = EQUIPMENT_REGISTRAR.register("pufferfish_knuckles", () -> new Equipment(400, 0x0, EquipmentSlot.WEAPON, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 0, 40, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, new FishIcon(17), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0));
  public static final RegistryDelegate<Equipment> PAWS = EQUIPMENT_REGISTRAR.register("paws", () -> new Equipment(400, 0x0, EquipmentSlot.WEAPON, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 40, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, new FishIcon(29), 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0));
  // Meru
  public static final RegistryDelegate<Equipment> GUITAR = EQUIPMENT_REGISTRAR.register("guitar", () -> new Equipment(400, 0x0, EquipmentSlot.WEAPON, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 25, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 5, 0, 0, 0, new FishIcon(18), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0));
  // Kongol
  public static final RegistryDelegate<Equipment> OVERSIZED_KEY = EQUIPMENT_REGISTRAR.register("oversized_key", () -> new Equipment(400, 0x0, EquipmentSlot.WEAPON, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 55, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, new FishIcon(15), 0, 0, 0, 20, 20, 0, 0, 0, 0, 0, 0x0));
  public static final RegistryDelegate<Equipment> HIS_HANDS = EQUIPMENT_REGISTRAR.register("his_hands", () -> new Equipment(400, 0x0, EquipmentSlot.WEAPON, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 80, 0, 0, 0, 0, 100, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, ItemIcon.BOXING_GLOVE, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0));

  public static final RegistryDelegate<Equipment> MAGIS_BOOTS = EQUIPMENT_REGISTRAR.register("magis_boots", () -> new Equipment(400, 0x0, EquipmentSlot.BOOTS, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 0, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 5, 0, 0, 0, ItemIcon.BOOTS, 0, 0, 0, 5, 5, 0, 0, 0, 0, 0, 0x0));
  public static final RegistryDelegate<Equipment> OLD_BOOTS = EQUIPMENT_REGISTRAR.register("old_boots", () -> new Equipment(400, 0x0, EquipmentSlot.BOOTS, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 0, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 10, 0, 0, 0, ItemIcon.BOOTS, 0, 0, 0, 10, 10, 0, 0, 0, 0, 0, 0x0));
  public static final RegistryDelegate<Equipment> THE_ONE_RING = EQUIPMENT_REGISTRAR.register("the_one_ring", () -> new Equipment(400, 0x0, EquipmentSlot.ACCESSORY, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 50, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, ItemIcon.RING, 0, 0, 50, 50, 50, 0, 0, 0, 0, 0, 0x0));

  public static final RegistryDelegate<Equipment> BLUE_AMULET = EQUIPMENT_REGISTRAR.register("blue_amulet", () -> new Equipment(400, 0x0, EquipmentSlot.ACCESSORY, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 0, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, ItemIcon.RING, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0));
  public static final RegistryDelegate<Equipment> AZURE_AMULET = EQUIPMENT_REGISTRAR.register("azure_amulet", () -> new Equipment(400, 0x0, EquipmentSlot.ACCESSORY, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 0, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, ItemIcon.RING, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0));
  public static final RegistryDelegate<Equipment> CHEATER_RING = EQUIPMENT_REGISTRAR.register("cheater_ring", () -> new Equipment(400, 0x0, EquipmentSlot.ACCESSORY, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 0, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, ItemIcon.RING, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0));
  public static final RegistryDelegate<Equipment> CRITICAL_RING = EQUIPMENT_REGISTRAR.register("critical_ring", () -> new Equipment(400, 0x0, EquipmentSlot.ACCESSORY, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 80, 0, 0, 0, 0, -75, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, ItemIcon.RING, 0, 0, 80, 0, 0, 0, 0, 0, 0, 0, 0x0));

  public static final RegistryDelegate<Equipment> ASSAULT_GEAR = EQUIPMENT_REGISTRAR.register("assault_gear", () -> new Equipment(400, 0x0, EquipmentSlot.ARMOUR, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 20, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, ItemIcon.ARMOR, 0, 0, 0, 40, 10, 0, 0, 0, 0, 0, 0x0));
  public static final RegistryDelegate<Equipment> BOUNDLESS_PLATE = EQUIPMENT_REGISTRAR.register("boundless_plate", () -> new Equipment(400, 0x0, EquipmentSlot.ARMOUR, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 0, 0, 0, 0, 0, -10, 150, 0, false, false, false, false, 0, 0, 10, 0, 0, ItemIcon.ARMOR, 0, 0, 15, 0, 50, 0, 0, 0, 0, 0, 0x0));
  public static final RegistryDelegate<Equipment> BERSERK_PLUME = EQUIPMENT_REGISTRAR.register("berserk_plume", () -> new Equipment(400, 0x0, EquipmentSlot.HELMET, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 44, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, ItemIcon.HELM, 20, 0, 0, -10, -10, 0, 0, 0, 0, 0, 0x0));
  public static final RegistryDelegate<Equipment> STORMCROWN = EQUIPMENT_REGISTRAR.register("stormcrown", () -> new Equipment(400, 0x0, EquipmentSlot.HELMET, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 10, 0, 0, 0, 0, 10, 10, 0, false, false, false, false, 10, 10, 10, 10, 0, ItemIcon.CROWN, 10, 0, 10, 10, 10, 0, 0, 10, 10, 0, 0x0));

  public static final RegistryDelegate<Equipment> GIGANTO_SKIRT = EQUIPMENT_REGISTRAR.register("giganto_skirt", () -> new Equipment(400, 0x0, EquipmentSlot.ARMOUR, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 0, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, ItemIcon.RED_DRESS, 20, 0, 100, 0, 50, 0, 0, 0, 20, 0, 0x0));
  public static final RegistryDelegate<Equipment> THIGH_HIGHS = EQUIPMENT_REGISTRAR.register("thigh_highs", () -> new Equipment(400, 0x0, EquipmentSlot.BOOTS, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 0, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, ItemIcon.KNEEPIECE, 40, 0, 0, 0, 0, 0, 0, 10, 10, 0, 0x0));
  public static final RegistryDelegate<Equipment> CAT_EARS = EQUIPMENT_REGISTRAR.register("cat_ears", () -> new Equipment(200, 0x0, EquipmentSlot.HELMET, LodMod.NO_ELEMENT.get(), new ElementSet(), new ElementSet(), 0x0, 20, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, ItemIcon.HAIRBAND, 10, 0, 20, 0, 0, 100, 100, 20, 20, 0, 0x0));

  static void register(final EquipmentRegistryEvent event) {
    EQUIPMENT_REGISTRAR.registryEvent(event);
  }
}
