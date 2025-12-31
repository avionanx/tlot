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
  public static final RegistryDelegate<Equipment> GLOWSTICK = EQUIPMENT_REGISTRAR.register("glowstick", () -> new Equipment(200, 0x0, EquipmentSlot.WEAPON, 0x80, 0x80, LodMod.NO_ELEMENT.get(), 0, new ElementSet(), new ElementSet(), 0x0, 0, 35, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, new FishIcon(12), 0, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0));
  // Alvitz
  public static final RegistryDelegate<Equipment> NAMELESS_SPEAR = EQUIPMENT_REGISTRAR.register("nameless_spear", () -> new Equipment(200, 0x0, EquipmentSlot.WEAPON, 0x80, 0x40, LodMod.NO_ELEMENT.get(), 0, new ElementSet(), new ElementSet(), 0x0, 0, 50, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, new FishIcon(14), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0));
  // Sharanda
  public static final RegistryDelegate<Equipment> BIANCA = EQUIPMENT_REGISTRAR.register("bianca", () -> new Equipment(200, 0x0, EquipmentSlot.WEAPON, 0x80, 0x2, LodMod.NO_ELEMENT.get(), 0, new ElementSet(), new ElementSet(), 0x0, 0, 30, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, new FishIcon(13), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0));
  // Rose
  public static final RegistryDelegate<Equipment> ENERGY_SWORD = EQUIPMENT_REGISTRAR.register("energy_sword", () -> new Equipment(200, 0x0, EquipmentSlot.WEAPON, 0x80, 0x4, LodMod.NO_ELEMENT.get(), 0, new ElementSet(), new ElementSet(), 0x0, 0, 25, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, new FishIcon(16), 5, 0, 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0));
  // Haschel
  public static final RegistryDelegate<Equipment> PUFFERFISH_KNUCKLES = EQUIPMENT_REGISTRAR.register("pufferfish_knuckles", () -> new Equipment(200, 0x0, EquipmentSlot.WEAPON, 0x80, 0x10, LodMod.NO_ELEMENT.get(), 0, new ElementSet(), new ElementSet(), 0x0, 0, 35, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, new FishIcon(17), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0));
  // Meru
  public static final RegistryDelegate<Equipment> GUITAR = EQUIPMENT_REGISTRAR.register("guitar", () -> new Equipment(200, 0x0, EquipmentSlot.WEAPON, 0x80, 0x1, LodMod.NO_ELEMENT.get(), 0, new ElementSet(), new ElementSet(), 0x0, 0, 25, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 10, 0, 0, 0, new FishIcon(18), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0));
  // Kongol
  public static final RegistryDelegate<Equipment> OVERSIZED_KEY = EQUIPMENT_REGISTRAR.register("oversized_key", () -> new Equipment(200, 0x0, EquipmentSlot.WEAPON, 0x80, 0x20, LodMod.NO_ELEMENT.get(), 0, new ElementSet(), new ElementSet(), 0x0, 0, 50, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, new FishIcon(15), 0, 0, 0, 20, 20, 0, 0, 0, 0, 0, 0, 0, 0x0));

  public static final RegistryDelegate<Equipment> OLD_BOOTS = EQUIPMENT_REGISTRAR.register("old_boots", () -> new Equipment(200, 0x0, EquipmentSlot.BOOTS, 0x40, 0xf7, LodMod.NO_ELEMENT.get(), 0, new ElementSet(), new ElementSet(), 0x0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 10, 0, 0, 0, ItemIcon.BOOTS, 0, 0, 0, 20, 20, 0, 0, 0, 0, 0, 0, 0, 0x0));
  public static final RegistryDelegate<Equipment> THE_ONE_RING = EQUIPMENT_REGISTRAR.register("the_one_ring", () -> new Equipment(1000, 0x0, EquipmentSlot.ACCESSORY, 0x20, 0xf7, LodMod.NO_ELEMENT.get(), 0, new ElementSet(), new ElementSet(), 0x0, 0, 50, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, ItemIcon.BRACELET, 0, 0, 50, 50, 50, 0, 0, 0, 0, 0, 0, 0, 0x0));

  public static final RegistryDelegate<Equipment> ORTHOS_PRIME = EQUIPMENT_REGISTRAR.register("orthos_prime", () -> new Equipment(200, 0x0, EquipmentSlot.WEAPON, 0x80, 0x40, LodMod.NO_ELEMENT.get(), 0, new ElementSet(), new ElementSet(), 0x0, 0, 70, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, ItemIcon.SPEAR, 5, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0));
  public static final RegistryDelegate<Equipment> GIGANTO_SKIRT = EQUIPMENT_REGISTRAR.register("giganto_skirt", () -> new Equipment(200, 0x0, EquipmentSlot.ARMOUR, 0x80, 0x20, LodMod.NO_ELEMENT.get(), 0, new ElementSet(), new ElementSet(), 0x0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, ItemIcon.RED_DRESS, 20, 0, 100, 0, 50, 0, 0, 0, 20, 0, 0, 0, 0x0));
  public static final RegistryDelegate<Equipment> THIGH_HIGHS = EQUIPMENT_REGISTRAR.register("thigh_highs", () -> new Equipment(200, 0x0, EquipmentSlot.BOOTS, 0x80, 0x80, LodMod.NO_ELEMENT.get(), 0, new ElementSet(), new ElementSet(), 0x0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, ItemIcon.KNEEPIECE, 40, 0, 0, 0, 0, 0, 0, 10, 10, 0, 0, 0, 0x0));
  public static final RegistryDelegate<Equipment> CAT_EARS = EQUIPMENT_REGISTRAR.register("cat_ears", () -> new Equipment(200, 0x0, EquipmentSlot.HELMET, 0x80, 0x4, LodMod.NO_ELEMENT.get(), 0, new ElementSet(), new ElementSet(), 0x0, 0, 20, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, ItemIcon.HELM, 10, 0, 20, 0, 0, 0, 0, 20, 20, 0, 0, 0, 0x0));

  static void register(final EquipmentRegistryEvent event) {
    EQUIPMENT_REGISTRAR.registryEvent(event);
  }
}
