package lod.thelegendoftides.icons;

import legend.core.QueuedModelStandard;
import legend.core.gpu.Bpp;
import legend.core.opengl.Texture;
import legend.game.types.RenderableMetrics14;
import legend.game.types.UiPart;
import legend.game.types.UiType;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public final class FishIconUiType {
  private FishIconUiType() { }

  private static final float TEXTURE_WIDTH = 32.0f * 19;
  private static final float ICON_WIDTH = 32.0f;
  private static final float WIDTH_DIVIDER = ICON_WIDTH / TEXTURE_WIDTH;

  public static final UiType FISH_ICONS = new UiType(new UiPart[] {
    // Carp
    new UiPart(new Metrics[] {addIcon(0)}, 1),
    // Rainbow trout
    new UiPart(new Metrics[] {addIcon(1)}, 1),
    // Pufferfish
    new UiPart(new Metrics[] {addIcon(2)}, 1),
    // Black Bass
    new UiPart(new Metrics[] {addIcon(3)}, 1),
    // Goldenfish
    new UiPart(new Metrics[] {addIcon(4)}, 1),
    // Sturgeon
    new UiPart(new Metrics[] {addIcon(5)}, 1),
    // Swordfish
    new UiPart(new Metrics[] {addIcon(6)}, 1),
    // Prickleback
    new UiPart(new Metrics[] {addIcon(7)}, 1),
    // Koi
    new UiPart(new Metrics[] {addIcon(8)}, 1),
    // Last Kraken Jr.
    new UiPart(new Metrics[] {addIcon(9)}, 1),
    // Azeel Gladiator
    new UiPart(new Metrics[] {addIcon(10)}, 1),
    // Message Bottle
    new UiPart(new Metrics[] {addIcon(11)}, 1),
    // Glowstick
    new UiPart(new Metrics[] {addIcon(12)}, 1),
    // Bianca
    new UiPart(new Metrics[] {addIcon(13)}, 1),
    // Nameless Spear
    new UiPart(new Metrics[] {addIcon(14)}, 1),
    // Oversized Key
    new UiPart(new Metrics[] {addIcon(15)}, 1),
    // Energy Sword
    new UiPart(new Metrics[] {addIcon(16)}, 1),
    // Pufferfish Knuckles
    new UiPart(new Metrics[] {addIcon(17)}, 1),
    // Guitar
    new UiPart(new Metrics[] {addIcon(18)}, 1),

  });

  private static Texture TEXTURE;

  private static Metrics addIcon(final int index) {
    return new Metrics(index * WIDTH_DIVIDER, 0.0f, 0, 0, 16, 16, WIDTH_DIVIDER, 1.0f);
  }

  public static class Metrics extends RenderableMetrics14 {
    public Metrics(final float u, final float v, final int x, final int y, final int width, final int height, final float textureWidth, final float textureHeight) {
      super(u, v, x, y, 0, Bpp.BITS_24.ordinal() << 7, width, height, textureWidth, textureHeight);
    }

    @Override
    public void useTexture(@NotNull final QueuedModelStandard model) {
      if(TEXTURE == null) {
        TEXTURE = Texture.png(Path.of("mods", "tlot", "fish.png"));
      }

      model.texture(TEXTURE);
    }
  }
}
