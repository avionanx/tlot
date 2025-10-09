package lod.thelegendoftides;

import legend.core.GameEngine;
import legend.game.characters.ElementSet;
import legend.game.inventory.Equipment;
import legend.game.inventory.EquipmentRegistryEvent;
import legend.game.inventory.ItemIcon;
import legend.game.types.EquipmentSlot;
import legend.lodmod.LodMod;
import org.legendofdragoon.modloader.registries.Registrar;
import org.legendofdragoon.modloader.registries.RegistryDelegate;

public class TlotEquipments {
  private TlotEquipments() { }

  private static final Registrar<Equipment, EquipmentRegistryEvent> EQUIPMENT_REGISTRAR = new Registrar<>(GameEngine.REGISTRIES.equipment, Tlot.MOD_ID);

  // Model Swap Weapons
  // Dart
  public static final RegistryDelegate<Equipment> LIGHTSABER = EQUIPMENT_REGISTRAR.register("lightsaber", () -> new Equipment(200, 0x0, EquipmentSlot.WEAPON, 0x80, 0x80, LodMod.NO_ELEMENT.get(), 0, new ElementSet(), new ElementSet(), 0x0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, ItemIcon.SWORD, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0));
  // Alvitz
  public static final RegistryDelegate<Equipment> POOL_NOODLE = EQUIPMENT_REGISTRAR.register("pool_noodle", () -> new Equipment(200, 0x0, EquipmentSlot.WEAPON, 0x80, 0x80, LodMod.NO_ELEMENT.get(), 0, new ElementSet(), new ElementSet(), 0x0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, ItemIcon.SWORD, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0));
  // Sharanda
  public static final RegistryDelegate<Equipment> GUN = EQUIPMENT_REGISTRAR.register("gun", () -> new Equipment(200, 0x0, EquipmentSlot.WEAPON, 0x80, 0x80, LodMod.NO_ELEMENT.get(), 0, new ElementSet(), new ElementSet(), 0x0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, ItemIcon.SWORD, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0));
  // Rose
  public static final RegistryDelegate<Equipment> ENERGY_SWORD = EQUIPMENT_REGISTRAR.register("energy_sword", () -> new Equipment(200, 0x0, EquipmentSlot.WEAPON, 0x80, 0x4, LodMod.NO_ELEMENT.get(), 0, new ElementSet(), new ElementSet(), 0x0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, ItemIcon.SWORD, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0));
  //
  public static final RegistryDelegate<Equipment> BROOM = EQUIPMENT_REGISTRAR.register("broom", () -> new Equipment(200, 0x0, EquipmentSlot.WEAPON, 0x80, 0x80, LodMod.NO_ELEMENT.get(), 0, new ElementSet(), new ElementSet(), 0x0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, ItemIcon.SWORD, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0));
  public static final RegistryDelegate<Equipment> TREE = EQUIPMENT_REGISTRAR.register("tree", () -> new Equipment(200, 0x0, EquipmentSlot.WEAPON, 0x80, 0x80, LodMod.NO_ELEMENT.get(), 0, new ElementSet(), new ElementSet(), 0x0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, 0, 0, 0, 0, 0, ItemIcon.SWORD, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0));

  static void register(final EquipmentRegistryEvent event) {
    EQUIPMENT_REGISTRAR.registryEvent(event);
  }
}
