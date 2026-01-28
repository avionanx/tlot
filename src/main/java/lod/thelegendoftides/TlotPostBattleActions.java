package lod.thelegendoftides;

import legend.game.combat.postbattleactions.PostBattleAction;
import legend.game.combat.postbattleactions.RegisterPostBattleActionsEvent;
import org.legendofdragoon.modloader.registries.Registrar;
import org.legendofdragoon.modloader.registries.RegistryDelegate;

import static legend.core.GameEngine.REGISTRIES;

public final class TlotPostBattleActions {
  private TlotPostBattleActions() { }

  private static final Registrar<PostBattleAction<?, ?>, RegisterPostBattleActionsEvent> REGISTRAR = new Registrar<>(REGISTRIES.postBattleActions, Tlot.MOD_ID);

  public static final RegistryDelegate<TooManyItemsPostBattleAction> TOO_MANY_ITEMS = REGISTRAR.register("too_many_items", TooManyItemsPostBattleAction::new);

  static void register(final RegisterPostBattleActionsEvent event) {
    REGISTRAR.registryEvent(event);
  }
}
