package com.gmail.nossr50.config.hocon.skills.swords;

import com.gmail.nossr50.config.ConfigConstants;
import com.gmail.nossr50.datatypes.skills.properties.AbstractMaxBonusLevel;
import com.gmail.nossr50.datatypes.skills.properties.MaxBonusLevel;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class ConfigSwordsCounterAttack {

    private static final double DAMAGE_MODIFIER_DEFAULT = 2.0;

    @Setting(value = ConfigConstants.MAX_CHANCE_FIELD_NAME, comment = ConfigConstants.MAX_CHANCE_FIELD_DESCRIPTION)
    private double maxChance = 30.0;

    @Setting(value = ConfigConstants.MAX_BONUS_LEVEL_FIELD_NAME, comment = ConfigConstants.MAX_BONUS_LEVEL_DESCRIPTION)
    private MaxBonusLevel maxBonusLevel = new AbstractMaxBonusLevel(100);

    @Setting(value = "Damage-Modifier", comment = "The damage returned from Counter-Attack will be equal to the damage dealt divided by this number." +
            "\nDefault value: "+DAMAGE_MODIFIER_DEFAULT)
    private double damageModifier = DAMAGE_MODIFIER_DEFAULT;

    public double getCounterAttackMaxChance() {
        return maxChance;
    }

    public MaxBonusLevel getCounterAttackMaxBonusLevel() {
        return maxBonusLevel;
    }

    public double getCounterAttackDamageModifier() {
        return damageModifier;
    }
}