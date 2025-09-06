package lod.thelegendoftides.icons;

import legend.game.inventory.ItemIcon;
import legend.game.types.UiType;

public class FishIcon extends ItemIcon {
  public FishIcon(final int icon) {
    super(icon);
  }

  @Override
  protected UiType getUiType() {
    return FishIconUiType.FISH_ICONS;
  }
}
