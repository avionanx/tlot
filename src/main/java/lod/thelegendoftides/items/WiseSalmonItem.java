package lod.thelegendoftides.items;

import legend.core.memory.Method;
import legend.game.characters.CharacterData2c;
import legend.game.i18n.I18n;
import legend.game.inventory.ItemStack;
import legend.game.inventory.UseItemResponse;
import lod.thelegendoftides.TlotFish;

import static legend.game.Scus94491BpeSegment_800b.gameState_800babc8;

public class WiseSalmonItem extends FishItem {
  public WiseSalmonItem() {
    super(TlotFish.WISE_SALMON);
  }

  @Override
  int getUnitPrice() {
   return 100;
  }

 @Override
  public boolean canBeUsed(final ItemStack stack, final UsageLocation location) {
    return location == UsageLocation.MENU;
  }

  @Override
  @Method(0x80022d88L)
  public void useInMenu(final ItemStack stack, final UseItemResponse response, final int charId) {
    final CharacterData2c character = gameState_800babc8.charData_32c.get(charId);

    int xp = character.xp_00;
    if(xp <= 999999) {
      xp = xp + 800;
    } else {
      xp = 999999;
    }
    character.xp_00 = xp;

    if(character.xp_00 >= character.getXpToNextLevel() && character.level_12 < 60) {
      character.level_12++;
    }

    response.success(I18n.translate(this.getTranslationKey("use")));
  }
}
