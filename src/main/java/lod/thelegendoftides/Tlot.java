package lod.thelegendoftides;

import legend.core.AddRegistryEvent;
import legend.core.MathHelper;
import legend.core.QueuedModelStandard;
import legend.core.gte.MV;
import legend.core.platform.input.InputAction;
import legend.core.platform.input.InputActionRegistryEvent;
import legend.core.platform.input.InputKey;
import legend.core.platform.input.ScancodeInputActivation;
import legend.game.EngineState;
import legend.game.combat.Battle;
import legend.game.combat.SBtld;
import legend.game.combat.SEffe;
import legend.game.combat.bent.PlayerBattleEntity;
import legend.game.combat.deff.DeffPackage;
import legend.game.combat.deff.DeffPart;
import legend.game.combat.deff.RegisterDeffsEvent;
import legend.game.combat.effects.AdditionSparksEffect08;
import legend.game.combat.effects.EffectManagerData6c;
import legend.game.combat.effects.EffectManagerParams;
import legend.game.combat.effects.GenericAttachment1c;
import legend.game.combat.encounters.Encounter;
import legend.game.combat.environment.BattleCamera;
import legend.game.combat.types.AdditionHitProperties10;
import legend.game.combat.types.AdditionHits80;
import legend.game.combat.types.AdditionSound;
import legend.game.inventory.Equipment;
import legend.game.inventory.EquipmentRegistryEvent;
import legend.game.inventory.ItemRegistryEvent;
import legend.game.inventory.ItemStack;
import legend.game.inventory.WhichMenu;
import legend.game.inventory.screens.MenuStack;
import legend.game.inventory.screens.ShopScreen;
import legend.game.modding.coremod.CoreMod;
import legend.game.modding.events.RenderEvent;
import legend.game.modding.events.battle.BattleEndedEvent;
import legend.game.modding.events.battle.BattleStartedEvent;
import legend.game.modding.events.battle.CombatantModelLoadedEvent;
import legend.game.modding.events.input.InputReleasedEvent;
import legend.game.modding.events.input.RegisterDefaultInputBindingsEvent;
import legend.game.modding.events.inventory.ShopContentsEvent;
import legend.game.modding.events.submap.SubmapEnvironmentTextureEvent;
import legend.game.saves.ConfigEntry;
import legend.game.saves.ConfigRegistryEvent;
import legend.game.scripting.ScriptState;
import legend.game.scripting.ScriptedObject;
import legend.game.submap.SMap;
import legend.game.submap.SubmapObject210;
import legend.game.submap.SubmapState;
import legend.game.types.EquipmentSlot;
import legend.game.types.TmdAnimationFile;
import lod.thelegendoftides.configs.CatchFlagsConfig;
import lod.thelegendoftides.icons.FishIconUiType;
import lod.thelegendoftides.screens.AdditionOverlayScreen;
import lod.thelegendoftides.screens.BaitSelectionScreen;
import lod.thelegendoftides.screens.FishAcquiredScreen;
import lod.thelegendoftides.screens.FishBookScreen;
import lod.thelegendoftides.screens.FishListScreen;
import lod.thelegendoftides.screens.MessageScreen;
import lod.thelegendoftides.screens.TimedMessageScreen;
import lod.thelegendoftides.screens.WaitingBiteScreen;
import org.joml.Vector3f;
import org.legendofdragoon.modloader.Mod;
import org.legendofdragoon.modloader.events.EventListener;
import org.legendofdragoon.modloader.registries.Registrar;
import org.legendofdragoon.modloader.registries.Registry;
import org.legendofdragoon.modloader.registries.RegistryDelegate;
import org.legendofdragoon.modloader.registries.RegistryId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static legend.core.GameEngine.CONFIG;
import static legend.core.GameEngine.EVENTS;
import static legend.core.GameEngine.REGISTRIES;
import static legend.core.GameEngine.RENDERER;
import static legend.core.GameEngine.SCRIPTS;
import static legend.game.Audio.playSound;
import static legend.game.DrgnFiles.loadDrgnDir;
import static legend.game.DrgnFiles.loadDrgnFileSync;
import static legend.game.EngineStates.currentEngineState_8004dd04;
import static legend.game.Graphics.GsGetLw;
import static legend.game.Graphics.displayHeight_1f8003e4;
import static legend.game.Graphics.displayWidth_1f8003e0;
import static legend.game.Menus.whichMenu_800bdc38;
import static legend.game.SItem.buildUiRenderable;
import static legend.game.Scus94491BpeSegment.battlePreloadedEntities_1f8003f4;
import static legend.game.Scus94491BpeSegment_8004.additionCounts_8004f5c0;
import static legend.game.Scus94491BpeSegment_8005.collidedPrimitiveIndex_80052c38;
import static legend.game.Scus94491BpeSegment_8005.submapCut_80052c30;
import static legend.game.Scus94491BpeSegment_8006.battleState_8006e398;
import static legend.game.Scus94491BpeSegment_800b.gameState_800babc8;
import static legend.game.Scus94491BpeSegment_800b.postBattleAction_800bc974;
import static legend.game.combat.SBtld.loadAdditions;
import static legend.game.combat.SEffe.allocateEffectManager;
import static legend.game.combat.bent.BattleEntity27c.FLAG_ANIMATE_ONCE;
import static legend.game.combat.bent.BattleEntity27c.FLAG_DRAGOON;
import static legend.game.combat.bent.BattleEntity27c.FLAG_HIDE;
import static legend.lodmod.LodMod.INPUT_ACTION_SMAP_INTERACT;

@Mod(id = Tlot.MOD_ID, version = "3.0.0")
public class Tlot {
  public static final String MOD_ID = "thelegendoftides";

  public static final Registrar<InputAction, InputActionRegistryEvent> TIDES_INPUT_REGISTRAR = new Registrar<>(REGISTRIES.inputActions, MOD_ID);
  public static final Registrar<ConfigEntry<?>, ConfigRegistryEvent> TIDES_CONFIG_REGISTRAR = new Registrar<>(REGISTRIES.config, MOD_ID);
  public static final RegistryDelegate<CatchFlagsConfig> CATCH_FLAGS = TIDES_CONFIG_REGISTRAR.register("catch_flags", CatchFlagsConfig::new);
  public static final RegistryDelegate<InputAction> TIDES_INPUT_FISH_MENU = TIDES_INPUT_REGISTRAR.register("tides_fish_menu", InputAction::editable);
  public static final Registry<Bait> BAIT_REGISTRY = new BaitRegistry();
  public static final Registry<Fish> FISH_REGISTRY = new FishRegistry();
  public static final Registry<FishBaitWeight> FISH_BAIT_WEIGHT_REGISTRY = new FishBaitWeightRegistry();
  public static final Registry<FishingHole> FISHING_HOLE_REGISTRY = new FishingHoleRegistry();
  public static final Registry<FishingStage> FISHING_STAGE_REGISTRY = new FishingStageRegistry();
  public static final Registrar<DeffPackage, RegisterDeffsEvent> TIDES_DEFF_REGISTRAR = new Registrar<>(REGISTRIES.deff, MOD_ID);

  private final Random rand = new Random();

  private List<FishingHole> currentCutFishingHoles = new ArrayList<>();
  private List<FishingIndicator> fishingIndicators = new ArrayList<>();
  private FishingHole currentFishingHole;
  public static boolean isFishEncounter;

  private final MenuStack menuStack = new MenuStack();
  private FishListScreen fishListScreen;

  private CollisionMesh[] stageCollision;

  private FishingState state = FishingState.NOT_FISHING;
  private FishingRod fishingRod;
  private Battle battle;
  private ScriptState<PlayerBattleEntity> playerState;
  private PlayerBattleEntity player;
  private Bait bait;

  private TmdAnimationFile victoryAnimation;
  private boolean usingVictoryAnimation;

  private int loadingAnimIndex = -1;
  private int animationFrames;

  private float bobberHorizontalAcceleration;
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

  private Fish capturingFish;
  private FishReelingHandler fishReelingHandler;
  private AdditionOverlayScreen additionScreen;
  private AdditionHits80 activeAddition;
  private AdditionHitProperties10 activeAdditionHit;
  private int additionTicks;

  private int fishCaughtTicks;
  private int fishLostTicks;
  private int holdingUpFishTicks;
  private boolean acquiredFishScreenCleared;

  private HashMap<Integer, SpecialWeapon> specialWeaponList = new HashMap();

  public Tlot() {
    isFishEncounter = false;
    EVENTS.register(this);
  }

  public static RegistryId id(final String entryId) {
    return new RegistryId(MOD_ID, entryId);
  }

  @EventListener
  public void registerRegistries(final AddRegistryEvent event) {
    event.addRegistry(BAIT_REGISTRY, RegisterBaitEvent::new);
    event.addRegistry(FISH_REGISTRY, RegisterFishEvent::new);
    event.addRegistry(FISH_BAIT_WEIGHT_REGISTRY, RegisterFishBaitWeightEvent::new);
    event.addRegistry(FISHING_STAGE_REGISTRY, RegisterFishingStageEvent::new);
    event.addRegistry(FISHING_HOLE_REGISTRY, RegisterFishingHoleEvent::new);
  }

  @EventListener
  public void registerItems(final ItemRegistryEvent event) {
    TlotItems.register(event);
  }

  @EventListener
  public void registerEquipments(final EquipmentRegistryEvent event) {
    TlotEquipments.register(event);
  }

  @EventListener
  public void registerBait(final RegisterBaitEvent event) {
    TlotBait.register(event);
  }

  @EventListener
  public void registerFish(final RegisterFishEvent event) {
    TlotFish.register(event);
  }

  @EventListener
  public void registerFishBaitWeights(final RegisterFishBaitWeightEvent event) {
    TlotFishBaitWeights.register(event);
  }

  @EventListener
  public void registerFishingHoles(final RegisterFishingHoleEvent event) {
    TlotFishingHoles.register(event);
  }

  @EventListener
  public void registerFishingStages(final RegisterFishingStageEvent event) {
    TlotFishingStages.register(event);
  }

  @EventListener
  public void checkFishingMap(final SubmapEnvironmentTextureEvent event) {
    if(this.fishListScreen != null) { this.fishListScreen.unload(); }
    this.fishingIndicators.clear();
    this.menuStack.reset();

    this.currentCutFishingHoles = TlotFishingHoles.getFishingHolesForCut(event.submapCut);
    for(final FishingHole hole : this.currentCutFishingHoles) {
      this.fishingIndicators.add(new FishingIndicator(hole.indicatorPosition));
    }
  }

  @EventListener
  public void disableBentScriptsOnBattleStart(final BattleStartedEvent event) {
    if(!isFishEncounter) {
      return;
    }
    isFishEncounter = false;

    SCRIPTS.getState(5).pause();
    SCRIPTS.getState(6).pause();
    SCRIPTS.getState(11).pause();
    SCRIPTS.getState(11).setFlag(FLAG_HIDE);

    this.battle = ((Battle)currentEngineState_8004dd04);
    this.playerState = SCRIPTS.getState(6, PlayerBattleEntity.class);
    this.player = this.playerState.innerStruct_00;
    this.stageCollision = new CollisionMesh[battlePreloadedEntities_1f8003f4.stage_963c.dobj2s_00.length];
    Arrays.setAll(this.stageCollision, i -> new CollisionMesh(battlePreloadedEntities_1f8003f4.stage_963c.dobj2s_00[i]));
    final BattleCamera camera = ((Battle)currentEngineState_8004dd04).camera_800c67f0;

    this.battle.battleInitialCameraMovementFinished_800c66a8 = true;
    camera.resetCameraMovement();

    final FishingStage fishingStage = this.currentFishingHole.fishingStage.get();

    final Vector3f viewPoint = fishingStage.cameraViewpoint;
    final Vector3f refPoint = fishingStage.cameraRefpoint;
    camera.setViewpoint(viewPoint.x, viewPoint.y, viewPoint.z);
    camera.setRefpoint(refPoint.x, refPoint.y, refPoint.z);
    camera.flags_11c = 3;
    camera.refpointBobj_80 = this.player;

    this.player.model_148.shadowType_cc = 3;
    this.player.model_148.modelPartWithShadowIndex_cd = 8;
    this.player.model_148.shadowSize_10c.x = 0x1800 / (float)0x1000;
    this.player.model_148.shadowSize_10c.z = 0x1800 / (float)0x1000;
    this.player.model_148.shadowOffset_118.y = fishingStage.playerPosition.y;
    this.player.model_148.coord2_14.coord.transfer.set(fishingStage.playerPosition);
    this.player.model_148.coord2_14.transforms.rotate.y = fishingStage.playerRotation;

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
  public void  loadCombatantSpecialWeapon(final CombatantModelLoadedEvent event) {
    if(event.combatant.charSlot_19c == -1 || isFishEncounter) return;
    
    final PlayerBattleEntity player = SCRIPTS.getObject(6 + event.combatant.charSlot_19c, PlayerBattleEntity.class);
    final ScriptState state = SCRIPTS.getState(6 + event.combatant.charSlot_19c);
    final int playerId = (event.combatant.charIndex_1a2 - 0x200) / 2;

    final boolean isDragoon = (state.getStor(0x7) & FLAG_DRAGOON) != 0;
    final int modelPartIndex;
    int modelPartIndex2 = -1;
    final Vector3f dragoonRotation = new Vector3f();
    final long partFlags;
    
    //TODO nuke this and canRender
    if(isDragoon) {
      this.specialWeaponList.get(event.combatant.charSlot_19c).canRender = false;
      return;
    } else {
      if(this.specialWeaponList.containsKey(event.combatant.charSlot_19c))
        this.specialWeaponList.get(event.combatant.charSlot_19c).canRender = true;
    }
    
    switch(player.charId_272) {
      case 2, 8 -> {
        modelPartIndex = isDragoon ? 0 : 2;
        partFlags = isDragoon ? 0L : 0x1L << 2 | 0x1L << 4 | 0x1L << 3;
      }
      case 4 -> {
        modelPartIndex = isDragoon ? 0 : 5;
        modelPartIndex2 = isDragoon ? 0 : 6;
        partFlags = isDragoon ? 0L : 0x1L << 5 | 0x1L << 6;
      }
      default -> {
        modelPartIndex = isDragoon ? 18 : player.getWeaponModelPart();
        partFlags = isDragoon ? 0x1L << 0x12 : 0x1L << player.getWeaponModelPart();
      }
    }
    
    // If weapon already exists (dragoons), reparent models to new bent models instead of loading another one
    if(this.specialWeaponList.containsKey(event.combatant.charSlot_19c)) {
      this.specialWeaponList.get(event.combatant.charSlot_19c).setParent(event.model.modelParts_00[modelPartIndex].coord2_04, event.model);
      this.specialWeaponList.get(event.combatant.charSlot_19c).withDragoonRotation(dragoonRotation);
      if(playerId == 4) {
        this.specialWeaponList.get(event.combatant.charSlot_19c + 10).setParent(event.model.modelParts_00[modelPartIndex].coord2_04, event.model);
        this.specialWeaponList.get(event.combatant.charSlot_19c + 10).withDragoonRotation(dragoonRotation);
      }
      player.model_148.partInvisible_f4 |= partFlags;
      return;
    }
    
    final Equipment specialWeapon = switch(playerId) {
      case 0 -> TlotEquipments.LIGHTSABER.get();
      case 1, 5 -> TlotEquipments.DRAGONSLAYER_SWORDSPEAR.get();
      case 2, 8 -> TlotEquipments.BIANCA.get();
      case 3 -> TlotEquipments.ENERGY_SWORD.get();
      case 4 -> TlotEquipments.PUFFERFISH_KNUCKLES.get();
      case 6 -> TlotEquipments.GUITAR.get();
      case 7 -> TlotEquipments.ENDS_OF_THE_EARTH.get();
      default -> null;
    };
    if(specialWeapon == null) return;

    if(gameState_800babc8.charData_32c[playerId].equipment_14.get(EquipmentSlot.WEAPON) == specialWeapon) {
      player.model_148.partInvisible_f4 |= partFlags;
      this.specialWeaponList.put(event.combatant.charSlot_19c, new SpecialWeapon(specialWeapon.getRegistryId(), player.model_148.modelParts_00[modelPartIndex].coord2_04, player.model_148, event.combatant.charSlot_19c));
      if(playerId == 4) {
        this.specialWeaponList.put(event.combatant.charSlot_19c + 10, new SpecialWeapon(specialWeapon.getRegistryId(), player.model_148.modelParts_00[modelPartIndex2].coord2_04, player.model_148, event.combatant.charSlot_19c));
      }
    }
  }

  @EventListener
  public void onBattleEnded(final BattleEndedEvent event) {
    this.specialWeaponList.values().forEach(SpecialWeapon::unload);
    this.specialWeaponList.values().clear();
  }

  @EventListener
  public void renderLoop(final RenderEvent event) {
    this.specialWeaponList.values().forEach(SpecialWeapon::render);
    if(whichMenu_800bdc38 != WhichMenu.NONE_0) return;

    if(FishIconUiType.FISH_ICONS.obj == null) {
      FishIconUiType.FISH_ICONS.obj = buildUiRenderable(FishIconUiType.FISH_ICONS, "Fish icons");
    }

    if(currentEngineState_8004dd04 instanceof SMap) {
      for(final FishingIndicator indicator : this.fishingIndicators) {
        indicator.render();
      }
    }

    if(this.state != FishingState.NOT_FISHING) {
      this.renderFishing();
    } else if(!isFishEncounter && !this.currentCutFishingHoles.isEmpty()) {
      this.currentFishingHole = isAtFishingHole(this.currentCutFishingHoles);

      if(this.fishListScreen == null && this.currentFishingHole != null) {
        this.fishListScreen = new FishListScreen(this.currentFishingHole);
        this.menuStack.pushScreen(this.fishListScreen);
      } else if(this.fishListScreen != null && this.currentFishingHole == null) {
        this.fishListScreen = null;
        this.menuStack.popScreen();
      }
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
            this.bobberHorizontalAcceleration = 450.0f;
            this.bobberVerticalAcceleration = 100.0f;
          } else {
            final MV lw = new MV();
            GsGetLw(this.player.model_148.coord2_14, lw);
            final Vector3f movement = new Vector3f(0.0f, -this.bobberVerticalAcceleration, -this.bobberHorizontalAcceleration).mul(lw).rotateY(-this.player.model_148.coord2_14.transforms.rotate.y);

            boolean collided = false;
            for(final CollisionMesh collisionMesh : this.stageCollision) {
              if(collisionMesh.checkCollision(this.fishingRod.bobberTransforms.transfer, movement)) {
                collided = true;
                break;
              }
            }

            if(collided) {
              if(this.castingTicks > this.animationFrames) {
                this.capturingFish = this.fishListScreen.fishingHole.getFishForBait(this.bait);
                this.menuStack.pushScreen(new WaitingBiteScreen(this::onFishNibbling, this.capturingFish, this::onFishHooked, this::onNoFishBiting, this::onFishEscaped));
                this.state = FishingState.WAITING_FOR_BITE;
              }
            } else {
              this.fishingRod.bobberCoord2.coord.transfer.add(movement);
              this.fishingRod.bobberCoord2.flg = 0;
              this.bobberHorizontalAcceleration -= 30.0f;
              this.bobberVerticalAcceleration -= 30.0f;

              // Prevent it from starting to move back towards caster
              this.bobberHorizontalAcceleration = Math.max(this.bobberHorizontalAcceleration, 0.0f);
            }
          }
        }

        case NIBBLING -> {
          final long time = System.nanoTime();

          if(this.nextBobTime < time && this.bobCount < BOB_COUNT) {
            final AdditionSparksEffect08 effect = new AdditionSparksEffect08(8, 0x200, 4, 0x20, 0x40, 0xff);
            final ScriptState<EffectManagerData6c<EffectManagerParams.VoidType>> state = allocateEffectManager("AdditionSparksEffect08", null, effect);

            state.innerStruct_00.getPosition().set(this.fishingRod.bobberTransforms.transfer);

            final GenericAttachment1c attachment = state.innerStruct_00.addAttachment(0, 0, SEffe::tickLifespanAttachment, new GenericAttachment1c());
            attachment.ticksRemaining_1a = 8;

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
          this.fishReelingHandler.tick();
          this.additionTicks--;

          if(this.additionTicks <= 0) {
            this.player.model_148.animationState_9c = 2; // pause
            this.loadRandomAdditionHit();
            for(final AdditionSound sound : this.activeAdditionHit.sounds) {
              playSound(1, sound.soundIndex, sound.initialDelay, 0);
            }
          }
        }

        case FISH_PULL_FROM_WATER -> {
          if(this.player.model_148.remainingFrames_9e == 0) {
            this.playerState.clearFlag(FLAG_ANIMATE_ONCE);

            this.menuStack.popScreen();
            this.menuStack.pushScreen(new FishAcquiredScreen(this.capturingFish, () -> this.acquiredFishScreenCleared = true));

            this.setVictoryAnimation();
            this.fishCaughtTicks = 0;
            this.state = FishingState.FISH_CAUGHT;
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

          if(this.holdingUpFishTicks > 50 && this.acquiredFishScreenCleared) {
            this.acquiredFishScreenCleared = false;
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
    if(whichMenu_800bdc38 != WhichMenu.NONE_0) return;

    if(event.action == TIDES_INPUT_FISH_MENU.get() && !SCRIPTS.isPaused()) {
      SCRIPTS.pause();
      this.menuStack.pushScreen(new FishBookScreen());
    } else if(
      event.action == INPUT_ACTION_SMAP_INTERACT.get() &&
      currentEngineState_8004dd04 instanceof SMap &&
      !gameState_800babc8.indicatorsDisabled_4e3 &&
      this.fishListScreen != null
    ) {
      isFishEncounter = true;
      this.fishListScreen.isFishListScreenDisabled = true;

      SBtld.startEncounter(new Encounter(1, 0, 0, 0, 0, 0, 0, 0, submapCut_80052c30, collidedPrimitiveIndex_80052c38, new Encounter.Monster(1, new Vector3f())), this.currentFishingHole.fishingStage.get().stageId);
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
    this.loadAnimations(4031 + this.player.charId_272 * 8);
  }

  private void setIdleAnimation() {
    this.playerState.clearFlag(FLAG_ANIMATE_ONCE);
    this.loadingAnimIndex = 0;
  }

  private void setHurtAnimation() {
    this.playerState.setFlag(FLAG_ANIMATE_ONCE);
    this.loadingAnimIndex = 1;
  }

  private void setThrowAnimation() {
    this.playerState.setFlag(FLAG_ANIMATE_ONCE);
    this.loadingAnimIndex = 7;
  }

  private void setVictoryAnimation() {
    this.usingVictoryAnimation = true;
    this.victoryAnimation.loadIntoModel(this.player.model_148);
    this.player.model_148.animationState_9c = 2;
  }

  private void showBaitScreen() {
    this.menuStack.pushScreen(new BaitSelectionScreen(gameState_800babc8.items_2e9, this::handleBaitSelected, this.fishListScreen::setSelectedBait));
    // yeah
    this.fishListScreen.onResized(null, 0, 0);
    this.fishListScreen.isFishListScreenDisabled = false;
  }

  private void handleBaitSelected(final Bait bait, final Runnable unloadBaitSelectionScreen) {
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
    this.fishReelingHandler = new FishReelingHandler(this.capturingFish, this::fishCapturedCallback, this::fishLostCallback);
    this.additionScreen = new AdditionOverlayScreen(this.fishReelingHandler::additionSuccessHandler, this.fishReelingHandler::additionFailCallback);
    this.menuStack.pushScreen(this.additionScreen);
    this.loadRandomAdditionHit();
    this.state = FishingState.START_REELING;
  }

  private void onNoFishBiting() {
    this.showBaitScreen();
    this.menuStack.pushScreen(new MessageScreen("message_no_bites"));
    this.state = FishingState.IDLE;
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

  public static FishingHole isAtFishingHole(final List<FishingHole> fishingHoles) {
    if(SCRIPTS.getObject(10, ScriptedObject.class) instanceof final SubmapObject210 player) {
      for(int i = 0; i < fishingHoles.size(); i++) {
        final FishingHole fishingHole = fishingHoles.get(i);

        if(fishingHole.collisionPrimitive == player.collidedPrimitiveIndex_16c) {
          return fishingHole;
        }
      }
    }

    return null;
  }

  private void fishCapturedCallback() {
    this.loadStandardAnimations();
    this.loadingAnimIndex = 8;
    this.playerState.setFlag(FLAG_ANIMATE_ONCE);
    this.state = FishingState.FISH_PULL_FROM_WATER;
  }

  private void fishLostCallback() {
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

  @EventListener
  public void deffRegistry(final RegisterDeffsEvent event) {
    TIDES_DEFF_REGISTRAR.register("rockhead_pufferfish", TidesItemDeffPackage::new);
    TIDES_DEFF_REGISTRAR.register("koi", TidesItemDeffPackage::new);
    TIDES_DEFF_REGISTRAR.register("swordfish", TidesItemDeffPackage::new);
    TIDES_DEFF_REGISTRAR.register("sturgeon", TidesItemDeffPackage::new);
    TIDES_DEFF_REGISTRAR.register("prickleback", TidesItemDeffPackage::new);
    TIDES_DEFF_REGISTRAR.register("carp", TidesItemDeffPackage::new);
    TIDES_DEFF_REGISTRAR.register("silver_carp", TidesItemDeffPackage::new);
    TIDES_DEFF_REGISTRAR.register("grand_carp", TidesItemDeffPackage::new);
    TIDES_DEFF_REGISTRAR.register("rainbow_trout", TidesItemDeffPackage::new);
    TIDES_DEFF_REGISTRAR.register("grand_rainbow_trout", TidesItemDeffPackage::new);
    TIDES_DEFF_REGISTRAR.register("azeel_gladiator", TidesItemDeffPackage::new);

    TIDES_DEFF_REGISTRAR.register("message_bottle", TidesItemDeffPackage::new);
    TIDES_DEFF_REGISTRAR.registryEvent(event);
  }

  @EventListener
  public void configRegistry(final ConfigRegistryEvent event) {
    TIDES_CONFIG_REGISTRAR.registryEvent(event);
  }

  @EventListener
  public void registerInputActions(final InputActionRegistryEvent event) {
    TIDES_INPUT_REGISTRAR.registryEvent(event);
  }

  @EventListener
  public void registerInput(final RegisterDefaultInputBindingsEvent event) {
    event.add(TIDES_INPUT_FISH_MENU.get(), new ScancodeInputActivation(InputKey.Z));
  }

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

  public static String getTranslationKey(final String... args) {
    return MOD_ID + '.' + String.join(".", args);
  }

  // Will probably switch to new shops rather than adding to existing ones
  @EventListener
  public void shopBaitsEvent(final ShopContentsEvent event) {
    if(event.shop.shopType_00 == 0) return;

    event.contents.add(new ShopScreen.ShopEntry<>(new ItemStack(TlotItems.REGULAR_BAIT_BOX.get(), 1, TlotItems.REGULAR_BAIT_BOX.get().getMaxDurability(null)), TlotItems.REGULAR_BAIT_BOX.get().getPrice(null)));
    event.contents.add(new ShopScreen.ShopEntry<>(new ItemStack(TlotItems.SPARKLING_BAIT_BOX.get(), 1, TlotItems.SPARKLING_BAIT_BOX.get().getMaxDurability(null)), TlotItems.SPARKLING_BAIT_BOX.get().getPrice(null)));
    event.contents.add(new ShopScreen.ShopEntry<>(new ItemStack(TlotItems.INFUSED_BAIT_BOX.get(), 1, TlotItems.INFUSED_BAIT_BOX.get().getMaxDurability(null)), TlotItems.INFUSED_BAIT_BOX.get().getPrice(null)));
    event.contents.add(new ShopScreen.ShopEntry<>(new ItemStack(TlotItems.MAGNETIC_BAIT_BOX.get(), 1, TlotItems.MAGNETIC_BAIT_BOX.get().getMaxDurability(null)), TlotItems.MAGNETIC_BAIT_BOX.get().getPrice(null)));
  }
}
