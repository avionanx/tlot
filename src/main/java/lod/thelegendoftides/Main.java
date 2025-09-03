package lod.thelegendoftides;

import legend.core.QueuedModelStandard;
import legend.core.gte.MV;
import legend.game.EngineState;
import legend.game.combat.Battle;
import legend.game.combat.SBtld;
import legend.game.combat.bent.PlayerBattleEntity;
import legend.game.combat.deff.DeffPart;
import legend.game.combat.encounters.Encounter;
import legend.game.combat.environment.BattleCamera;
import legend.game.combat.types.AdditionHitProperties10;
import legend.game.combat.types.AdditionHits80;
import legend.game.combat.types.AdditionSound;
import legend.game.inventory.screens.MenuStack;
import legend.game.modding.coremod.CoreMod;
import legend.game.modding.events.RenderEvent;
import legend.game.modding.events.battle.BattleStartedEvent;
import legend.game.modding.events.gamestate.GameLoadedEvent;
import legend.game.modding.events.input.InputReleasedEvent;
import legend.game.modding.events.submap.SubmapEnvironmentTextureEvent;
import legend.game.submap.SMap;
import legend.game.submap.SubmapObject210;
import legend.game.submap.SubmapState;
import legend.game.types.TmdAnimationFile;
import org.joml.Vector3f;
import org.legendofdragoon.modloader.Mod;
import org.legendofdragoon.modloader.events.EventListener;
import org.legendofdragoon.modloader.registries.RegistryId;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static legend.core.GameEngine.*;
import static legend.game.Scus94491BpeSegment.*;
import static legend.game.Scus94491BpeSegment_8003.GsGetLw;
import static legend.game.Scus94491BpeSegment_8004.additionCounts_8004f5c0;
import static legend.game.Scus94491BpeSegment_8004.currentEngineState_8004dd04;
import static legend.game.Scus94491BpeSegment_8005.collidedPrimitiveIndex_80052c38;
import static legend.game.Scus94491BpeSegment_8005.submapCut_80052c30;
import static legend.game.Scus94491BpeSegment_8006.battleState_8006e398;
import static legend.game.Scus94491BpeSegment_800b.gameState_800babc8;
import static legend.game.Scus94491BpeSegment_800b.postBattleAction_800bc974;
import static legend.game.Scus94491BpeSegment_800b.scriptStatePtrArr_800bc1c0;
import static legend.game.combat.SBtld.loadAdditions;
import static legend.game.combat.bent.BattleEntity27c.FLAG_HIDE;
import static legend.lodmod.LodMod.INPUT_ACTION_SMAP_INTERACT;

@Mod(id = Main.MOD_ID, version = "3.0.0")
public class Main {
  public static final String MOD_ID = "thelegendoftides";

  private final Random rand = new Random();

  private FishMeta meta;
  private FishLocationData currentCutFishingData;
  private boolean isFishEncounter = false;

  private final MenuStack menuStack = new MenuStack();
  private FishListScreen fishListScreen;

  private FishingState state = FishingState.NOT_FISHING;
  private FishingRod fishingRod;
  private Battle battle;
  private PlayerBattleEntity player;
  private String bait;

  private TmdAnimationFile victoryAnimation;
  private boolean usingVictoryAnimation;

  private int loadingAnimIndex = -1;
  private int animationFrames;

  private float bobberVerticalAcceleration;
  private int castingTicks;

  /** The length of each bob */
  private static final long BOB_TIME = 250_000_000L;
  /** The interval between groups of bobs (a +-20% random factor is also applied */
  private static final long BOB_INTERVAL = 1_000_000_000L;
  /** The number of bobs in each interval */
  private static final int BOB_COUNT = 2;
  private final Vector3f nibbleStartPos = new Vector3f();
  private final Vector3f nibbleEndPos = new Vector3f();
  private long nextBobTime;
  private long nextBobInterval;
  private int bobCount;

  private FishReelingHandler fishReelingHandler;
  private AdditionOverlayScreen additionScreen;
  private AdditionHits80 activeAddition;
  private AdditionHitProperties10 activeAdditionHit;
  private int additionTicks;

  private int fishCaughtTicks;
  private int fishLostTicks;
  private int holdingUpFishTicks;

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

  @EventListener
  public void checkFishingMap(final SubmapEnvironmentTextureEvent event) {
    if(this.fishListScreen != null) { this.fishListScreen.unload(); }
    this.menuStack.reset();
    this.currentCutFishingData = this.meta.getCutFish(event.submapCut);
    if(this.currentCutFishingData != null) {
      this.fishListScreen = new FishListScreen(this.meta, this.currentCutFishingData);
      this.menuStack.pushScreen(this.fishListScreen);
    }
  }

  @EventListener
  public void disableBentScriptsOnBattleStart(final BattleStartedEvent event) {
    if(!this.isFishEncounter) return;
    this.isFishEncounter = false;

    scriptStatePtrArr_800bc1c0[5].pause();
    scriptStatePtrArr_800bc1c0[6].pause();
    scriptStatePtrArr_800bc1c0[11].pause();
    scriptStatePtrArr_800bc1c0[11].storage_44[7] |= FLAG_HIDE;

    final FishingStageData stageData = this.fishingStageData.get(submapCut_80052c30);

    this.battle = ((Battle)currentEngineState_8004dd04);
    this.player = (PlayerBattleEntity)scriptStatePtrArr_800bc1c0[6].innerStruct_00;
    BattleCamera camera = ((Battle)currentEngineState_8004dd04).camera_800c67f0;

    this.battle.battleInitialCameraMovementFinished_800c66a8 = true;
    camera.resetCameraMovement();
    final Vector3f viewPoint = stageData.cameraViewpoint();
    final Vector3f refPoint = stageData.cameraRefpoint();
    camera.setViewpoint(viewPoint.x, viewPoint.y, viewPoint.z);
    camera.setRefpoint(refPoint.x, refPoint.y, refPoint.z);
    camera.flags_11c = 3;
    camera.refpointBobj_80 = player;

    this.player.model_148.shadowType_cc = 3;
    this.player.model_148.modelPartWithShadowIndex_cd = 8;
    this.player.model_148.shadowSize_10c.x = 0x1800 / (float)0x1000;
    this.player.model_148.shadowSize_10c.z = 0x1800 / (float)0x1000;
    this.player.model_148.coord2_14.coord.transfer.set(stageData.playerPosition());
    this.player.model_148.coord2_14.transforms.rotate.y = stageData.playerRotation();

    // Hide player's weapon
    this.player.model_148.partInvisible_f4 |= 0x1L << this.player.getWeaponModelPart();

    // Load animation of Dart holding up divine dragoon spirit during transformation
    loadDrgnFileSync(0, "4232/0/1", data -> this.victoryAnimation = (TmdAnimationFile)((DeffPart.AnimatedTmdType)DeffPart.getDeffPart(List.of(data), 0)).anim_14);

    if(this.fishingRod != null) {
      throw new IllegalStateException("Need to clean up after fishing");
    }

    this.fishingRod = new FishingRod(this.player.model_148.modelParts_00[this.player.getWeaponModelPart()].coord2_04);
    this.showBaitScreen();
    this.state = FishingState.IDLE;
  }

  @EventListener
  public void renderLoop(final RenderEvent event) {
    if(this.state != FishingState.NOT_FISHING) {
      this.renderFishing();
    }

    // We're using a DEFF animation for the victory animation so we have to pause normal animation and run the animation code ourself
    if(this.usingVictoryAnimation) {
      this.player.model_148.animationState_9c = 1;
      this.player.model_148.anim_08.apply(this.fishCaughtTicks);
      this.player.model_148.animationState_9c = 2;
    }

    this.menuStack.render();
  }

  private void renderFishing() {
    if(this.loadingAnimIndex != -1) {
      // Wait for animation to finish loading and then load it into the player
      final TmdAnimationFile asset = battleState_8006e398.getAnimationGlobalAsset(this.player.combatant_144, this.loadingAnimIndex);

      if(asset != null) {
        asset.loadIntoModel(this.player.model_148);
        this.animationFrames = asset.totalFrames_0e;
        this.loadingAnimIndex = -1;
      }
    } else {
      switch(this.state) {
        case CASTING -> {
          this.castingTicks++;

          if(this.castingTicks > this.animationFrames) {
            this.setIdleAnimation();
          }

          if(this.castingTicks < 18) {
            this.fishingRod.bobberCoord2.set(this.player.model_148.modelParts_00[this.player.getRightHandModelPart()].coord2_04);
            this.bobberVerticalAcceleration = 100.0f;
          } else if(this.castingTicks < 32) {
            final MV lw = new MV();
            GsGetLw(this.player.model_148.coord2_14, lw);
            this.fishingRod.bobberCoord2.coord.transfer.add(new Vector3f(0.0f, -this.bobberVerticalAcceleration, -450.0f).mul(lw));
            this.fishingRod.bobberCoord2.flg = 0;
            this.bobberVerticalAcceleration -= 30.0f;
          }

          if(this.castingTicks > 45) {
            //TODO use fish
            final Fish fish = this.meta.getRandomFishForBait(this.currentCutFishingData, bait);
            this.menuStack.pushScreen(new WaitingBiteScreen(this::onFishNibbling, this::onFishHooked, this::onFishEscaped));
            this.state = FishingState.WAITING_FOR_BITE;
          }
        }

        case NIBBLING -> {
          final long time = System.nanoTime();

          if(this.nextBobTime < time && this.bobCount < BOB_COUNT) {
            this.nextBobTime = time + BOB_TIME;
            this.bobCount++;
          }

          if(this.nextBobInterval < time) {
            this.nextBobInterval = time + (long)(BOB_INTERVAL * (0.8f + this.rand.nextFloat() * 0.4f));
            this.bobCount = 0;
          }

          final float t = Math.clamp((this.nextBobTime - time) / (float)BOB_TIME, 0.0f, 1.0f);
          this.nibbleEndPos.lerp(this.nibbleStartPos, t, this.fishingRod.bobberCoord2.coord.transfer);
          this.fishingRod.bobberCoord2.flg = 0;
        }

        case START_REELING -> {
          this.additionTicks--;

          for(final AdditionSound sound : this.activeAdditionHit.sounds) {
            playSound(1, sound.soundIndex, sound.initialDelay, 0);
          }

          this.state = FishingState.REELING;
        }

        case REELING -> {
          this.additionTicks--;

          if(this.additionTicks <= 0) {
            this.player.model_148.animationState_9c = 2; // pause
            this.loadRandomAdditionHit();
            for(final AdditionSound sound : this.activeAdditionHit.sounds) {
              playSound(1, sound.soundIndex, sound.initialDelay, 0);
            }
          }
        }

        case FISH_CAUGHT -> {
          this.fishCaughtTicks++;

          if(this.fishCaughtTicks >= this.victoryAnimation.totalFrames_0e * 2 - 1) {
            this.holdingUpFishTicks = 0;
            this.usingVictoryAnimation = false;
            this.state = FishingState.HOLDING_UP_FISH;
          }
        }

        case HOLDING_UP_FISH -> {
          this.holdingUpFishTicks++;

          if(this.holdingUpFishTicks > 50) {
            this.setIdleAnimation();
            this.showBaitScreen();
            this.state = FishingState.IDLE;
          }
        }

        case FISH_LOST -> {
          this.fishLostTicks++;

          if(this.fishLostTicks > this.animationFrames) {
            this.setIdleAnimation();
          }

          if(this.fishLostTicks > this.animationFrames + 20) {
            this.showBaitScreen();
            this.state = FishingState.IDLE;
          }
        }
      }
    }

    this.fishingRod.renderRod(this.player.model_148.zOffset_a0 * 4);

    if(this.state.ordinal() >= FishingState.CASTING.ordinal() && this.state.ordinal() < FishingState.FISH_CAUGHT.ordinal()) {
      this.fishingRod.renderBobber();
    }

    if(this.state.ordinal() > FishingState.IDLE.ordinal() && this.state.ordinal() < FishingState.FISH_CAUGHT.ordinal()) {
      this.fishingRod.processString(3);
      this.fishingRod.renderString();
    }

    if(this.state == FishingState.REELING) {
      this.renderStamina();
    }
  }

  private void renderStamina() {
    final float stamina = this.fishReelingHandler.getStaminaFraction();

    final MV transforms = new MV();
    transforms.scaling(8.0f, 40.0f, 1.0f);
    transforms.transfer.set(190.0f, 150.0f, 11.0f);
    RENDERER.queueOrthoModel(RENDERER.centredQuadBMinusF, transforms, QueuedModelStandard.class)
      .colour(0.3f, 0.3f, 0.3f);

    transforms.scaling(8.0f, 40.0f * (1.0f - stamina), 1.0f);
    transforms.transfer.z = 10.0f;
    final QueuedModelStandard model = RENDERER.queueOrthoModel(RENDERER.centredQuadOpaque, transforms, QueuedModelStandard.class);

    if(stamina < 0.333f) {
      model.colour(0.0f, 0.8f, 0.0f);
    } else if(stamina < 0.6667f) {
      model.colour(0.8f, 0.8f, 0.0f);
    } else {
      model.colour(0.8f, 0.0f, 0.0f);
    }
  }

  @EventListener
  public void inputReleased(final InputReleasedEvent event) {
    if(event.action == INPUT_ACTION_SMAP_INTERACT.get()
            && isOnFishingPrimitive(this.currentCutFishingData)
            && !gameState_800babc8.indicatorsDisabled_4e3) {

      this.isFishEncounter = true;
      this.fishListScreen.isFishListScreenDisabled = true;

      SBtld.startEncounter(new Encounter(1, 0, 0, 0, 0, 0, 0, 0, submapCut_80052c30, collidedPrimitiveIndex_80052c38, new Encounter.Monster(1, new Vector3f())), this.fishingStageData.get(submapCut_80052c30).stageId());
      ((SMap)currentEngineState_8004dd04).smapLoadingStage_800cb430 = SubmapState.TRANSITION_TO_COMBAT_19;
    }
  }

  private void loadAnimations(final int fileIndex) {
    loadDrgnDir(0, fileIndex, files -> {
      this.player.combatant_144.mrg_04 = null;
      this.battle.attackAnimationsLoaded(files, this.player.combatant_144, false, this.player.combatant_144.charSlot_19c);
      // Finish asset loading - some animations need to be decompressed
      this.battle.FUN_800c9e10(this.player.combatant_144, this.loadingAnimIndex);
    });
  }

  private void loadStandardAnimations() {
    this.loadAnimations(4031 * this.player.charId_272 * 8);
  }

  private void setIdleAnimation() {
    this.loadingAnimIndex = 0;
  }

  private void setHurtAnimation() {
    this.loadingAnimIndex = 1;
  }

  private void setThrowAnimation() {
    this.loadingAnimIndex = 7;
  }

  private void setVictoryAnimation() {
    this.usingVictoryAnimation = true;
    this.victoryAnimation.loadIntoModel(this.player.model_148);
    this.player.model_148.animationState_9c = 2;
  }

  private void showBaitScreen() {
    this.menuStack.pushScreen(new BaitSelectionScreen(this.meta, this::handleBaitSelected));
    this.fishListScreen.isFishListScreenDisabled = false;
  }

  private void handleBaitSelected(final String bait, final Runnable unloadBaitSelectionScreen) {
    unloadBaitSelectionScreen.run();
    this.fishListScreen.isFishListScreenDisabled = true;

    if(bait == null) {
      this.stopFishing();
      postBattleAction_800bc974 = 5;
      return;
    }

    this.bait = bait;
    this.cast();
  }

  private void cast() {
    this.fishingRod.bobberCoord2.set(this.player.model_148.modelParts_00[this.player.getRightHandModelPart()].coord2_04);
    this.fishingRod.initString();
    playSound(0x0, 0x15, 0x10, 0);
    this.loadAnimations(4031 + this.player.charId_272 * 8);
    this.setThrowAnimation();
    this.castingTicks = 0;
    this.state = FishingState.CASTING;
  }

  private void onFishNibbling() {
    this.nibbleEndPos.set(this.fishingRod.bobberCoord2.coord.transfer);
    this.nibbleStartPos.set(this.nibbleEndPos).add(0.0f, 100.0f, 0.0f);
    this.nextBobTime = 0; // Bob immediately
    this.nextBobInterval = System.nanoTime() + BOB_INTERVAL;
    this.bobCount = 0;
    this.state = FishingState.NIBBLING;
  }

  private void onFishHooked() {
    this.fishReelingHandler = new FishReelingHandler(this::fishCapturedCallback, this::fishLostCallback);
    this.additionScreen = new AdditionOverlayScreen(fishReelingHandler::additionSuccessHandler, fishReelingHandler::additionFailCallback);
    this.menuStack.pushScreen(this.additionScreen);
    this.loadRandomAdditionHit();
    this.state = FishingState.START_REELING;
  }

  private void onFishEscaped() {
    this.showBaitScreen();
    this.menuStack.pushScreen(new MessageScreen("message_bait_lost"));
    this.state = FishingState.IDLE;
  }

  private void loadRandomAdditionHit() {
    final int charId = this.player.charId_272;
    final int additionCount = additionCounts_8004f5c0[charId];
    final int randomAddition = this.rand.nextInt(additionCount);
    final int fileIndex = 4031 + charId * 8 + randomAddition;

    final int oldAddition = gameState_800babc8.charData_32c[charId].selectedAddition_19;
    gameState_800babc8.charData_32c[charId].selectedAddition_19 = randomAddition;
    loadAdditions();
    gameState_800babc8.charData_32c[charId].selectedAddition_19 = oldAddition;

    this.activeAddition = battlePreloadedEntities_1f8003f4.additionHits_38[0];
    int hitCount = 0;

    for(int i = 0; i < this.activeAddition.hits_00.length; i++) {
      if(this.activeAddition.hits_00[i].flags_00 != 0) {
        hitCount++;
      }
    }

    this.loadingAnimIndex = 16 + this.rand.nextInt(hitCount);

    this.activeAdditionHit = this.activeAddition.hits_00[this.loadingAnimIndex - 16];
    this.additionTicks = this.activeAdditionHit.totalFrames_01;

    this.loadAnimations(fileIndex);
    this.additionScreen.addHit();
  }

  public static boolean isOnFishingPrimitive(final FishLocationData locationData) {
    try {
      return ((SubmapObject210)scriptStatePtrArr_800bc1c0[10].innerStruct_00).collidedPrimitiveIndex_16c == locationData.collisionPrimitive();
    }catch(final Exception e) {
      return false;
    }
  }

  private void fishCapturedCallback() {
    //TODO fish caught

    this.menuStack.popScreen();
    this.setVictoryAnimation();
    this.fishCaughtTicks = 0;
    this.state = FishingState.FISH_CAUGHT;
  }

  private void fishLostCallback() {
    //TODO fish lost

    this.menuStack.popScreen();
    this.setHurtAnimation();
    playSound(1, 6, 0, 0); // hurt
    this.fishLostTicks = 0;
    this.state = FishingState.FISH_LOST;
    this.menuStack.pushScreen(new TimedMessageScreen("message_fish_escaped", this.animationFrames + 20));
  }

  private void stopFishing() {
    this.fishingRod.delete();
    this.fishingRod = null;

    this.battle = null;
    this.player = null;

    this.additionScreen = null;

    this.state = FishingState.NOT_FISHING;
  }

  // TODO dont use cut, maybe submapId?
  private final Map<Integer, FishingStageData> fishingStageData = Map.<Integer, FishingStageData>ofEntries(
          Map.entry(-1, new FishingStageData(13, new Vector3f(-2680, 0, -5200), 0, new Vector3f(-8000.0f, -2000.0f, -15000.0f), new Vector3f(0.0f, 0.0f, -5000.0f))),
          Map.entry(133, new FishingStageData(7, new Vector3f(1000, 0, -4800), 0, new Vector3f(10000.0f, -1200.0f, -8000.0f), new Vector3f(-3500.0f, 0.0f, -7500.0f)))
  );

  public static float getExtraWidth() {
    final boolean widescreen = RENDERER.getRenderMode() == EngineState.RenderMode.PERSPECTIVE && CONFIG.getConfig(CoreMod.ALLOW_WIDESCREEN_CONFIG.get());
    final float fullWidth;
    if(widescreen) {
      fullWidth = Math.max(displayWidth_1f8003e0, RENDERER.window().getWidth() / (float)RENDERER.window().getHeight() * displayHeight_1f8003e4);
    } else {
      fullWidth = displayWidth_1f8003e0;
    }

    return fullWidth - displayWidth_1f8003e0;
  }
  public static String getTranslationKey(String... args) {
    return MOD_ID + "." + String.join(".", args);
  }
}
