package lod.thelegendoftides;
import legend.core.platform.input.InputAction;
import legend.core.platform.input.InputActionRegistryEvent;
import legend.core.platform.input.InputKey;
import legend.core.platform.input.ScancodeInputActivation;
import legend.game.EngineStateEnum;
import legend.game.combat.Battle;
import legend.game.combat.SBtld;
import legend.game.combat.bent.BattleEntity27c;
import legend.game.combat.encounters.Encounter;
import legend.game.combat.environment.BattleCamera;
import legend.game.inventory.screens.MenuStack;
import legend.game.modding.events.RenderEvent;
import legend.game.modding.events.battle.BattleMusicEvent;
import legend.game.modding.events.battle.BattleStartedEvent;
import legend.game.modding.events.gamestate.GameLoadedEvent;
import legend.game.modding.events.input.InputReleasedEvent;
import legend.game.modding.events.input.RegisterDefaultInputBindingsEvent;
import legend.game.modding.events.submap.SubmapEnvironmentTextureEvent;
import legend.game.submap.SMap;
import legend.game.submap.SubmapObject210;
import legend.game.submap.SubmapState;
import org.joml.Vector3f;
import org.legendofdragoon.modloader.Mod;
import org.legendofdragoon.modloader.events.EventListener;
import org.legendofdragoon.modloader.registries.Registrar;
import org.legendofdragoon.modloader.registries.RegistryDelegate;
import org.legendofdragoon.modloader.registries.RegistryId;

import static legend.core.GameEngine.*;
import static legend.game.Scus94491BpeSegment_8002.playMenuSound;
import static legend.game.Scus94491BpeSegment_8004.currentEngineState_8004dd04;
import static legend.game.Scus94491BpeSegment_8004.engineStateOnceLoaded_8004dd24;
import static legend.game.Scus94491BpeSegment_800b.gameState_800babc8;
import static legend.game.Scus94491BpeSegment_800b.scriptStatePtrArr_800bc1c0;
import static legend.game.combat.bent.BattleEntity27c.FLAG_HIDE;
import static legend.game.Scus94491BpeSegment_8004.currentEngineState_8004dd04;
@Mod(id = Main.MOD_ID, version = "3.0.0")
public class Main {
  public static final String MOD_ID = "thelegendoftides";
  public static final Registrar<InputAction, InputActionRegistryEvent> TIDES_INPUT_REGISTRAR = new Registrar<>(REGISTRIES.inputActions, MOD_ID);
  public static final RegistryDelegate<InputAction> TIDES_INPUT_ACTION = TIDES_INPUT_REGISTRAR.register("tides_action", InputAction::editable);
  public static final RegistryDelegate<InputAction> TIDES_INPUT_CANCEL = TIDES_INPUT_REGISTRAR.register("tides_cancel", InputAction::editable);
  public static final RegistryDelegate<InputAction> TIDES_INPUT_FISH = TIDES_INPUT_REGISTRAR.register("tides_fish", InputAction::editable);

  private FishMeta meta;
  private FishLocationData currentCutFishingData;
  private boolean isFishEncounter = false;

  private final MenuStack menuStack = new MenuStack();

  public Main() {
    EVENTS.register(this);
  }

  public static RegistryId id(final String entryId) {
    return new RegistryId(MOD_ID, entryId);
  }

  @EventListener
  public void init(final GameLoadedEvent event) {
    this.meta = new FishMeta();
  }

  // Could not think of another way to not leak memory, lol
  private Runnable fishListScreenUnloader = null;
  @EventListener
  public void checkFishingMap(final SubmapEnvironmentTextureEvent event) {
    if(fishListScreenUnloader != null) { fishListScreenUnloader.run(); }
    this.menuStack.reset();
    this.currentCutFishingData = this.meta.getCutFish(event.submapCut);
    if(this.currentCutFishingData != null) {
      final FishListScreen screen = new FishListScreen(this.meta, this.currentCutFishingData);
      this.fishListScreenUnloader = screen::unload;
      this.menuStack.pushScreen(screen);
    }
  }

  @EventListener
  public void disableBentScriptsOnBattleStart(final BattleStartedEvent event) {
    if(!this.isFishEncounter) return;
    this.isFishEncounter = false;
    scriptStatePtrArr_800bc1c0[6].pause();
    scriptStatePtrArr_800bc1c0[11].pause();
    final BattleEntity27c player = (BattleEntity27c)scriptStatePtrArr_800bc1c0[6].innerStruct_00;
    final BattleEntity27c enemy = (BattleEntity27c)scriptStatePtrArr_800bc1c0[11].innerStruct_00;
    scriptStatePtrArr_800bc1c0[11].storage_44[7] |= FLAG_HIDE;

    scriptStatePtrArr_800bc1c0[5].pause();
    BattleCamera camera = ((Battle)currentEngineState_8004dd04).camera_800c67f0;
    ((Battle)currentEngineState_8004dd04).battleInitialCameraMovementFinished_800c66a8 = true;
    camera.resetCameraMovement();
    camera.setViewpoint(-8000.0f, -2000.0f, -15000.0f);
    camera.setRefpoint(0.0f, 0.0f, -5000.0f);
    camera.flags_11c = 3;
    camera.refpointBobj_80 = player;
    player.model_148.shadowType_cc = 3;
    player.model_148.modelPartWithShadowIndex_cd = 8;
    player.model_148.shadowSize_10c.x = 0x1800 / (float)0x1000;
    player.model_148.shadowSize_10c.z = 0x1800 / (float)0x1000;
    player.model_148.coord2_14.coord.transfer.x = -2680;
    player.model_148.coord2_14.coord.transfer.y = 0;
    player.model_148.coord2_14.coord.transfer.z = -5200;
    this.menuStack.pushScreen(new AdditionOverlayScreen());
  }
/*
  @EventListener
  public void handleEntranceCamera(final BattleEncounterStageDataEvent event) {
    event.stageData.monsterOpeningCamera_14 = -1;
    event.stageData.playerOpeningCamera_10 = -1;
    ((Battle)currentEngineState_8004dd04).battleInitialCameraMovementFinished_800c66a8 = true;
  }
 */
  @EventListener
  public void registerInputActions(final InputActionRegistryEvent event) {
    TIDES_INPUT_REGISTRAR.registryEvent(event);
  }

  @EventListener
  public void registerInput(final RegisterDefaultInputBindingsEvent event) {
    event.add(TIDES_INPUT_ACTION.get(), new ScancodeInputActivation(InputKey.SPACE));
    event.add(TIDES_INPUT_FISH.get(), new ScancodeInputActivation(InputKey.F));
    event.add(TIDES_INPUT_CANCEL.get(), new ScancodeInputActivation(InputKey.ESCAPE));
  }

  @EventListener
  public void renderLoop(final RenderEvent event) {
    this.menuStack.render();

  }

  @EventListener
  public void inputReleased(final InputReleasedEvent event) {
    if(event.action == TIDES_INPUT_FISH.get()
            && isOnFishingPrimitive(this.currentCutFishingData.collisionPrimitive())
            && !gameState_800babc8.indicatorsDisabled_4e3) {
      this.isFishEncounter = true;
      SBtld.startEncounter(new Encounter(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, new Encounter.Monster(1, new Vector3f())), 13);
      ((SMap)currentEngineState_8004dd04).smapLoadingStage_800cb430 = SubmapState.TRANSITION_TO_COMBAT_19;
      //this.meta.getRandomFishForBait(this.currentCutFishingData, "bait");
      //scriptStatePtrArr_800bc1c0[10].pause();
      //gameState_800babc8.indicatorsDisabled_4e3 = true;
      //playMenuSound(2);
      //this.menuStack.pushScreen(new BaitSelectionScreen(this.meta, this::handleBaitSelected));
    }
  }
  private void handleBaitSelected(final String bait, final Runnable unloadBaitSelectionScreen) {
    unloadBaitSelectionScreen.run();
    if(bait == null) {
      scriptStatePtrArr_800bc1c0[10].resume();
      gameState_800babc8.indicatorsDisabled_4e3 = false;
      return;
    }
    final Fish fish = this.meta.getRandomFishForBait(this.currentCutFishingData, bait);
    this.menuStack.pushScreen(new WaitingScreen(this.meta, fish));
  }

  public static boolean isOnFishingPrimitive(final int collisionPrimitive) {
    try {
      return ((SubmapObject210)scriptStatePtrArr_800bc1c0[10].innerStruct_00).collidedPrimitiveIndex_16c == collisionPrimitive;
    }catch(final Exception e) {
      return false;
    }
  }
}