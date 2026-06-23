package lod.thelegendoftides.items;

import legend.core.memory.Method;
import legend.game.characters.CharacterData2c;
import legend.game.characters.Element;
import legend.game.combat.bent.BattleEntity27c;
import legend.game.i18n.I18n;
import legend.game.inventory.ItemStack;
import legend.game.inventory.UseItemResponse;
import legend.game.scripting.ScriptState;
import legend.lodmod.LodMod;
import lod.thelegendoftides.TlotFish;

import static legend.game.Scus94491BpeSegment_800b.gameState_800babc8;
import static legend.lodmod.LodMod.ATTACK_STAT;
import static legend.lodmod.LodMod.DEFENSE_STAT;
import static legend.lodmod.LodMod.HP_STAT;
import static legend.lodmod.LodMod.MAGIC_ATTACK_STAT;
import static legend.lodmod.LodMod.MAGIC_DEFENSE_STAT;
import static legend.lodmod.LodMod.MP_STAT;
import static legend.lodmod.LodMod.SPEED_STAT;
import static lod.thelegendoftides.Tlot.TLOT_RAND;

public class FishPrimeItem extends FishItem {
  public FishPrimeItem() {
    super(TlotFish.FISH_PRIME);
  }

  @Override
  int getUnitPrice() {
   return 200;
  }

  @Override
  public boolean canBeUsed(final ItemStack stack, final UsageLocation location) {
    return location == UsageLocation.MENU;
  }

  @Override
  public boolean isRepeat(final ItemStack stack) {
    return true;
  }

  @Override
  @Method(0x80022d88L)
  public void useInMenu(final ItemStack stack, final UseItemResponse response, final int charId) {
    final CharacterData2c character = gameState_800babc8.charData_32c.get(charId);
    final int statIndex = TLOT_RAND.nextInt(5);
    switch(statIndex) {
      case 0 -> character.stats.getStat(SPEED_STAT.get()).setRaw(character.stats.getStat(SPEED_STAT.get()).get() + 1);
      case 1 -> character.stats.getStat(ATTACK_STAT.get()).setRaw(character.stats.getStat(ATTACK_STAT.get()).get() + 1);
      case 2 -> character.stats.getStat(MAGIC_ATTACK_STAT.get()).setRaw(character.stats.getStat(MAGIC_ATTACK_STAT.get()).get() + 1);
      case 3 -> character.stats.getStat(DEFENSE_STAT.get()).setRaw(character.stats.getStat(DEFENSE_STAT.get()).get() + 1);
      case 4 -> character.stats.getStat(MAGIC_DEFENSE_STAT.get()).setRaw(character.stats.getStat(MAGIC_DEFENSE_STAT.get()).get() + 1);
    }

    response.success(I18n.translate(this.getTranslationKey("use_%d".formatted(statIndex))));
  }
}
