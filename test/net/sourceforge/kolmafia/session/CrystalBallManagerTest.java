package net.sourceforge.kolmafia.session;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.sourceforge.kolmafia.KoLCharacter;
import net.sourceforge.kolmafia.MonsterData;
import net.sourceforge.kolmafia.combat.MonsterStatusTracker;
import net.sourceforge.kolmafia.persistence.MonsterDatabase;
import net.sourceforge.kolmafia.preferences.Preferences;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CrystalBallManagerTest {
  static final MonsterData NAILER = MonsterDatabase.findMonster("smut orc nailer");
  static final MonsterData SKELELTON = MonsterDatabase.findMonster("spiny skelelton");
  static final MonsterData SKELTEON = MonsterDatabase.findMonster("party skelteon");

  @BeforeAll
  public static void beforeAll() {
    Preferences.saveSettingsToFile = false;
  }

  @BeforeEach
  public void beforeEach() {
    KoLCharacter.reset("fakeUserName");
    Preferences.reset("fakeUsername");
    Preferences.setString(
        "crystalBallPredictions",
        "0:The Smut Orc Logging Camp:smut orc nailer|0:The Defiled Nook:party skelteon");
    CrystalBallManager.reset();
  }

  @Test
  public static void crystalBallZoneTest() {
    assertTrue(CrystalBallManager.isCrystalBallZone("The Smut Orc Logging Camp"));
    assertTrue(CrystalBallManager.isCrystalBallZone("The Defiled Nook"));
    assertFalse(CrystalBallManager.isCrystalBallZone("The Defiled Niche"));
  }

  @Test
  public static void crystalBallMonsterTestString() {
    assertTrue(
        CrystalBallManager.isCrystalBallMonster("smut orc nailer", "The Smut Orc Logging Camp"));
    assertTrue(CrystalBallManager.isCrystalBallMonster("party skelteon", "The Defiled Nook"));
    assertFalse(CrystalBallManager.isCrystalBallMonster("spiny skelelton", "The Defiled Niche"));
  }

  @Test
  public static void crystalBallMonsterTestMonsterData() {
    assertTrue(CrystalBallManager.isCrystalBallMonster(NAILER, "The Smut Orc Logging Camp"));
    assertTrue(CrystalBallManager.isCrystalBallMonster(SKELTEON, "The Defiled Nook"));
    assertFalse(CrystalBallManager.isCrystalBallMonster(SKELELTON, "The Defiled Niche"));
  }

  @Test
  public static void crystalBallMonsterTestNextEncounter() {
    assertFalse(CrystalBallManager.isCrystalBallMonster());

    MonsterStatusTracker.setNextMonster(SKELTEON);
    assertFalse(CrystalBallManager.isCrystalBallMonster());

    Preferences.setString("nextAdventure", "The Defiled Nook");
    assertTrue(CrystalBallManager.isCrystalBallMonster());
  }
}
