package com.gmail.nossr50.config;

import com.gmail.nossr50.config.collectionconfigs.RepairConfig;
import com.gmail.nossr50.config.collectionconfigs.SalvageConfig;
import com.gmail.nossr50.config.experience.ExperienceConfig;
import com.gmail.nossr50.config.hocon.CustomEnumValueSerializer;
import com.gmail.nossr50.config.hocon.SerializedConfigLoader;
import com.gmail.nossr50.config.hocon.admin.ConfigAdmin;
import com.gmail.nossr50.config.hocon.antiexploit.ConfigExploitPrevention;
import com.gmail.nossr50.config.hocon.backup.ConfigAutomatedBackups;
import com.gmail.nossr50.config.hocon.commands.ConfigCommands;
import com.gmail.nossr50.config.hocon.database.ConfigDatabase;
import com.gmail.nossr50.config.hocon.donation.ConfigAuthorAdvertisements;
import com.gmail.nossr50.config.hocon.hardcore.ConfigHardcore;
import com.gmail.nossr50.config.hocon.items.ConfigItems;
import com.gmail.nossr50.config.hocon.language.ConfigLanguage;
import com.gmail.nossr50.config.hocon.metrics.ConfigMetrics;
import com.gmail.nossr50.config.hocon.mobs.ConfigMobs;
import com.gmail.nossr50.config.hocon.motd.ConfigMOTD;
import com.gmail.nossr50.config.hocon.notifications.ConfigNotifications;
import com.gmail.nossr50.config.hocon.particles.ConfigParticles;
import com.gmail.nossr50.config.hocon.party.ConfigParty;
import com.gmail.nossr50.config.hocon.party.data.ConfigPartyData;
import com.gmail.nossr50.config.hocon.playerleveling.ConfigLeveling;
import com.gmail.nossr50.config.hocon.scoreboard.ConfigScoreboard;
import com.gmail.nossr50.config.hocon.skills.acrobatics.ConfigAcrobatics;
import com.gmail.nossr50.config.hocon.skills.alchemy.ConfigAlchemy;
import com.gmail.nossr50.config.hocon.skills.archery.ConfigArchery;
import com.gmail.nossr50.config.hocon.skills.axes.ConfigAxes;
import com.gmail.nossr50.config.hocon.skills.excavation.ConfigExcavation;
import com.gmail.nossr50.config.hocon.skills.fishing.ConfigFishing;
import com.gmail.nossr50.config.hocon.skills.herbalism.ConfigHerbalism;
import com.gmail.nossr50.config.hocon.skills.mining.ConfigMining;
import com.gmail.nossr50.config.hocon.skills.repair.ConfigRepair;
import com.gmail.nossr50.config.hocon.skills.salvage.ConfigSalvage;
import com.gmail.nossr50.config.hocon.skills.smelting.ConfigSmelting;
import com.gmail.nossr50.config.hocon.skills.swords.ConfigSwords;
import com.gmail.nossr50.config.hocon.skills.taming.ConfigTaming;
import com.gmail.nossr50.config.hocon.skills.unarmed.ConfigUnarmed;
import com.gmail.nossr50.config.hocon.skills.woodcutting.ConfigWoodcutting;
import com.gmail.nossr50.config.hocon.superabilities.ConfigSuperAbilities;
import com.gmail.nossr50.config.hocon.worldblacklist.ConfigWorldBlacklist;
import com.gmail.nossr50.config.skills.alchemy.PotionConfig;
import com.gmail.nossr50.config.treasure.ExcavationTreasureConfig;
import com.gmail.nossr50.config.treasure.FishingTreasureConfig;
import com.gmail.nossr50.config.treasure.HerbalismTreasureConfig;
import com.gmail.nossr50.datatypes.party.PartyFeature;
import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.skills.repair.repairables.Repairable;
import com.gmail.nossr50.skills.repair.repairables.SimpleRepairableManager;
import com.gmail.nossr50.skills.salvage.salvageables.Salvageable;
import com.gmail.nossr50.skills.salvage.salvageables.SimpleSalvageableManager;
import com.gmail.nossr50.util.experience.ExperienceMapManager;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.bukkit.Material;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * The Config Manager handles initializing, loading, and unloading registers for all configs that mcMMO uses
 * This makes sure that mcMMO properly loads and unloads its values on reload
 *
 * Config Manager also holds all of our MultiConfigContainers
 */
public final class ConfigManager {



    /* UNLOAD REGISTER */

    private ArrayList<Unload> unloadables;
    private ArrayList<File> userFiles;

    /* MULTI CONFIG INSTANCES */

    //private MultiConfigContainer<Repairable> repairableMultiConfigContainer;
    //private MultiConfigContainer<Salvageable> salvageableMultiConfigContainer;

    /* COLLECTION MANAGERS */

    private SimpleRepairableManager simpleRepairableManager;
    private SimpleSalvageableManager simpleSalvageableManager;

    /* MOD MANAGERS */

    //TODO: Add these back when modded servers become a thing again

    /* MISC MANAGERS */

    private ExperienceMapManager experienceMapManager;

    //private ModManager modManager;

    /*private ToolConfigManager toolConfigManager;
    private ArmorConfigManager armorConfigManager;
    private BlockConfigManager blockConfigManager;
    private EntityConfigManager entityConfigManager;*/

    /* CONFIG INSTANCES */

    private SerializedConfigLoader<ConfigDatabase> configDatabase;
    private SerializedConfigLoader<ConfigScoreboard> configScoreboard;
    private SerializedConfigLoader<ConfigLeveling> configLeveling;
    private SerializedConfigLoader<ConfigWorldBlacklist> configWorldBlacklist;
    private SerializedConfigLoader<ConfigExploitPrevention> configExploitPrevention;
    private SerializedConfigLoader<ConfigHardcore> configHardcore;
    private SerializedConfigLoader<ConfigMetrics> configMetrics;
    private SerializedConfigLoader<ConfigMOTD> configMOTD;
    private SerializedConfigLoader<ConfigAuthorAdvertisements> configAuthorAdvertisements;
    private SerializedConfigLoader<ConfigAutomatedBackups> configAutomatedBackups;
    private SerializedConfigLoader<ConfigCommands> configCommands;
    private SerializedConfigLoader<ConfigItems> configItems;
    private SerializedConfigLoader<ConfigLanguage> configLanguage;
    private SerializedConfigLoader<ConfigParticles> configParticles;
    private SerializedConfigLoader<ConfigParty> configParty;
    private SerializedConfigLoader<ConfigNotifications> configNotifications;
    private SerializedConfigLoader<ConfigSuperAbilities> configSuperAbilities;
    private SerializedConfigLoader<ConfigAdmin> configAdmin;
    private SerializedConfigLoader<ConfigMobs> configMobs;

    private SerializedConfigLoader<ConfigAcrobatics> configAcrobatics;
    private SerializedConfigLoader<ConfigAlchemy> configAlchemy;
    private SerializedConfigLoader<ConfigArchery> configArchery;
    private SerializedConfigLoader<ConfigAxes> configAxes;
    private SerializedConfigLoader<ConfigExcavation> configExcavation;
    private SerializedConfigLoader<ConfigFishing> configFishing;
    private SerializedConfigLoader<ConfigHerbalism> configHerbalism;
    private SerializedConfigLoader<ConfigMining> configMining;
    private SerializedConfigLoader<ConfigRepair> configRepair;
    private SerializedConfigLoader<ConfigSwords> configSwords;
    private SerializedConfigLoader<ConfigTaming> configTaming;
    private SerializedConfigLoader<ConfigUnarmed> configUnarmed;
    private SerializedConfigLoader<ConfigWoodcutting> configWoodcutting;
    private SerializedConfigLoader<ConfigSmelting> configSmelting;
    private SerializedConfigLoader<ConfigSalvage> configSalvage;

    //Data
    private SerializedConfigLoader<ConfigPartyData> partyData;

    private MainConfig mainConfig;
    private FishingTreasureConfig fishingTreasureConfig;
    private ExcavationTreasureConfig excavationTreasureConfig;
    private HerbalismTreasureConfig herbalismTreasureConfig;
    private ExperienceConfig experienceConfig;
    private AdvancedConfig advancedConfig;
    private PotionConfig potionConfig;
    private CoreSkillsConfig coreSkillsConfig;
    private SoundConfig soundConfig;
    private RankConfig rankConfig;
    private RepairConfig repairConfig;
    private SalvageConfig salvageConfig;

    private HashMap<Material, Integer> partyItemWeights;
    private HashMap<PartyFeature, Integer> partyFeatureUnlocks;

    /* CONFIG ERRORS */

    private ArrayList<String> configErrors; //Collect errors to whine about to server admins

    public ConfigManager()
    {
        unloadables = new ArrayList<>();
        userFiles = new ArrayList<>();
    }

    public void loadConfigs()
    {
        // Load Config Files
        // I'm pretty these are supposed to be done in a specific order, so don't rearrange them willy nilly

        //Register Custom Serializers
        mcMMO.p.getLogger().info("Registering custom type serializers with Configurate...");

        /*
         TypeTokens are obtained in two ways

            For Raw basic classes:

                TypeToken<String> stringTok = TypeToken.of(String.class);
                TypeToken<Integer> intTok = TypeToken.of(Integer.class);

            For Generics:

                TypeToken<List<String>> stringListTok = new TypeToken<List<String>>() {};

            Wildcard example:

                TypeToken<Map<?, ?>> wildMapTok = new TypeToken<Map<?, ?>>() {};

         */

        /*
            List of default serializers for reference
            DEFAULT_SERIALIZERS.registerType(TypeToken.of(URI.class), new URISerializer());
            DEFAULT_SERIALIZERS.registerType(TypeToken.of(URL.class), new URLSerializer());
            DEFAULT_SERIALIZERS.registerType(TypeToken.of(UUID.class), new UUIDSerializer());
            DEFAULT_SERIALIZERS.registerPredicate(input -> input.getRawType().isAnnotationPresent(ConfigSerializable.class), new AnnotatedObjectSerializer());
            DEFAULT_SERIALIZERS.registerPredicate(NumberSerializer.getPredicate(), new NumberSerializer());
            DEFAULT_SERIALIZERS.registerType(TypeToken.of(String.class), new StringSerializer());
            DEFAULT_SERIALIZERS.registerType(TypeToken.of(Boolean.class), new BooleanSerializer());
            DEFAULT_SERIALIZERS.registerType(new TypeToken<Map<?, ?>>() {}, new MapSerializer());
            DEFAULT_SERIALIZERS.registerType(new TypeToken<List<?>>() {}, new ListSerializer());
            DEFAULT_SERIALIZERS.registerType(new TypeToken<Enum<?>>() {}, new EnumValueSerializer());
            DEFAULT_SERIALIZERS.registerType(TypeToken.of(Pattern.class), new PatternSerializer());
         */

        TypeSerializers.getDefaultSerializers().registerType(new TypeToken<Material>() {}, new CustomEnumValueSerializer());
        TypeSerializers.getDefaultSerializers().registerType(new TypeToken<PartyFeature>() {}, new CustomEnumValueSerializer());


        mcMMO.p.getLogger().info("Deserializing configs...");
        //TODO: Not sure about the order of MainConfig
        //Serialized Configs
        configDatabase = new SerializedConfigLoader<>(ConfigDatabase.class, "database_settings.conf", null);
        configScoreboard = new SerializedConfigLoader<>(ConfigScoreboard.class, "scoreboard.conf", null);
        configLeveling = new SerializedConfigLoader<>(ConfigLeveling.class, "player_leveling.conf", null);
        configWorldBlacklist = new SerializedConfigLoader<>(ConfigWorldBlacklist.class, "world_blacklist.conf", null);
        configExploitPrevention = new SerializedConfigLoader<>(ConfigExploitPrevention.class, "exploit_prevention.conf", null);
        configMOTD = new SerializedConfigLoader<>(ConfigMOTD.class, "message_of_the_day.conf", null);
        configHardcore = new SerializedConfigLoader<>(ConfigHardcore.class, "hardcore_mode.conf", null);
        configMetrics = new SerializedConfigLoader<>(ConfigMetrics.class, "analytics_reporting.conf", null);
        configAuthorAdvertisements = new SerializedConfigLoader<>(ConfigAuthorAdvertisements.class, "author_support_advertisements.conf", null);
        configAutomatedBackups = new SerializedConfigLoader<>(ConfigAutomatedBackups.class, "automated_backups.conf", null);
        configCommands = new SerializedConfigLoader<>(ConfigCommands.class, "commands.conf", null);
        configItems = new SerializedConfigLoader<>(ConfigItems.class, "custom_items.conf", null);
        configLanguage = new SerializedConfigLoader<>(ConfigLanguage.class, "language.conf", null);
        configParticles = new SerializedConfigLoader<>(ConfigParticles.class, "particle_spawning.conf", null);
        configParty = new SerializedConfigLoader<>(ConfigParty.class, "party.conf", null);
        configNotifications = new SerializedConfigLoader<>(ConfigNotifications.class, "chat_and_hud_notifications.conf", null);
        configSuperAbilities = new SerializedConfigLoader<>(ConfigSuperAbilities.class, "skill_super_abilities.conf", null);
        configAdmin = new SerializedConfigLoader<>(ConfigAdmin.class, "admin.conf", null);
        configMobs = new SerializedConfigLoader<>(ConfigMobs.class, "creatures.conf", null);

        configAcrobatics = new SerializedConfigLoader<>(ConfigAcrobatics.class, "acrobatics.conf", null);
        configSalvage = new SerializedConfigLoader<>(ConfigSalvage.class, "salvage.conf", null);
        configArchery = new SerializedConfigLoader<>(ConfigArchery.class, "archery.conf", null);
        configAxes = new SerializedConfigLoader<>(ConfigAxes.class, "axes.conf", null);
        configExcavation = new SerializedConfigLoader<>(ConfigExcavation.class, "excavation.conf", null);
        configFishing = new SerializedConfigLoader<>(ConfigFishing.class, "fishing.conf", null);
        configHerbalism = new SerializedConfigLoader<>(ConfigHerbalism.class, "herbalism.conf", null);
        configMining = new SerializedConfigLoader<>(ConfigMining.class, "mining.conf", null);
        configRepair = new SerializedConfigLoader<>(ConfigRepair.class, "repair.conf", null);
        configSwords = new SerializedConfigLoader<>(ConfigSwords.class, "swords.conf", null);
        configTaming = new SerializedConfigLoader<>(ConfigTaming.class, "taming.conf", null);
        configUnarmed = new SerializedConfigLoader<>(ConfigUnarmed.class, "unarmed.conf", null);
        configWoodcutting = new SerializedConfigLoader<>(ConfigWoodcutting.class, "woodcutting.conf", null);
        configSmelting = new SerializedConfigLoader<>(ConfigSmelting.class, "smelting.conf", null);

        //Serialized Data
        partyData = new SerializedConfigLoader<>(ConfigPartyData.class, "partydata.conf", null);

        //Assign Maps
        partyItemWeights = Maps.newHashMap(configParty.getConfig().getPartyItemShare().getItemShareMap()); //Item Share Weights
        partyFeatureUnlocks = Maps.newHashMap(configParty.getConfig().getPartyXP().getPartyLevel().getPartyFeatureUnlockMap()); //Party Progression

        //YAML Configs
        mainConfig = new MainConfig();

        fishingTreasureConfig = new FishingTreasureConfig();
        excavationTreasureConfig = new ExcavationTreasureConfig();
        herbalismTreasureConfig = new HerbalismTreasureConfig();

        advancedConfig = new AdvancedConfig();

        //TODO: Not sure about the order of experience config
        experienceConfig = new ExperienceConfig();

        potionConfig = new PotionConfig();

        coreSkillsConfig = new CoreSkillsConfig();

        soundConfig = new SoundConfig();

        rankConfig = new RankConfig();

        repairConfig = new RepairConfig();

        salvageConfig = new SalvageConfig();

        /*
         * Managers
         */

        // Register Managers
        initMiscManagers();
        initCollectionManagers();
    }

    /**
     * Misc managers
     */
    private void initMiscManagers()
    {
        experienceMapManager = new ExperienceMapManager();
    }

    /**
     * Initializes all of our Multi Config Containers
     */
    /*private void initMultiConfigContainers()
    {
        //Repair
        repairableMultiConfigContainer = new MultiConfigContainer<>("repair", CollectionClassType.REPAIR);
        unloadables.add(repairableMultiConfigContainer);

        //Salvage
        salvageableMultiConfigContainer = new MultiConfigContainer<>("salvage", CollectionClassType.SALVAGE);
        unloadables.add(salvageableMultiConfigContainer);
    }*/

    /**
     * Initializes any managers related to config collections
     */
    private void initCollectionManagers()
    {
        // Handles registration of repairables
        simpleRepairableManager = new SimpleRepairableManager(getRepairables());
        unloadables.add(simpleRepairableManager);

        // Handles registration of salvageables
        simpleSalvageableManager = new SimpleSalvageableManager(getSalvageables());
        unloadables.add(simpleSalvageableManager);
    }

    /**
     * Get all loaded repairables (loaded from all repairable configs)
     * @return the currently loaded repairables
     */
    public ArrayList<Repairable> getRepairables()
    {
        return (ArrayList<Repairable>) repairConfig.genericCollection;
    }

    /**
     * Get all loaded salvageables (loaded from all salvageable configs)
     * @return the currently loaded salvageables
     */
    public ArrayList<Salvageable> getSalvageables()
    {
        return (ArrayList<Salvageable>) salvageConfig.genericCollection;
    }

    /**
     * Unloads all config options (prepares for reload)
     */
    public void unloadAllConfigsAndRegisters()
    {
        //Unload
        for(Unload unloadable : unloadables)
        {
            unloadable.unload();
        }

        //Clear
        unloadables.clear();
        userFiles.clear();
    }

    /**
     * Registers an unloadable
     * Unloadables call unload() on plugin disable to cleanup registries
     */
    public void registerUnloadable(Unload unload)
    {
        if(!unloadables.contains(unload))
            unloadables.add(unload);
    }

    /**
     * Registers an unloadable
     * Unloadables call unload() on plugin disable to cleanup registries
     */
    public void registerUserFile(File userFile)
    {
        if(!userFiles.contains(userFile))
            userFiles.add(userFile);
    }

    /*
     * GETTER BOILER PLATE
     */

    /**
     * Used to back up our zip files real easily
     * @return
     */
    public ArrayList<File> getConfigFiles()
    {
        return userFiles;
    }

    public SimpleRepairableManager getSimpleRepairableManager() {
        return simpleRepairableManager;
    }

    public SimpleSalvageableManager getSimpleSalvageableManager() {
        return simpleSalvageableManager;
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public FishingTreasureConfig getFishingTreasureConfig() {
        return fishingTreasureConfig;
    }

    public ExcavationTreasureConfig getExcavationTreasureConfig() {
        return excavationTreasureConfig;
    }

    public HerbalismTreasureConfig getHerbalismTreasureConfig() {
        return herbalismTreasureConfig;
    }

    public AdvancedConfig getAdvancedConfig() {
        return advancedConfig;
    }

    public PotionConfig getPotionConfig() {
        return potionConfig;
    }

    public CoreSkillsConfig getCoreSkillsConfig() {
        return coreSkillsConfig;
    }

    public SoundConfig getSoundConfig() {
        return soundConfig;
    }

    public RankConfig getRankConfig() {
        return rankConfig;
    }

    public ExperienceConfig getExperienceConfig() {
        return experienceConfig;
    }

    public ExperienceMapManager getExperienceMapManager() {
        return experienceMapManager;
    }

    public ConfigDatabase getConfigDatabase() { return configDatabase.getConfig(); }

    public ConfigScoreboard getConfigScoreboard() { return configScoreboard.getConfig(); }

    public ConfigLeveling getConfigLeveling() {
        return configLeveling.getConfig();
    }

    public ConfigWorldBlacklist getConfigWorldBlacklist() {
        return configWorldBlacklist.getConfig();
    }

    public ConfigExploitPrevention getConfigExploitPrevention() {
        return configExploitPrevention.getConfig();
    }

    public ConfigMOTD getConfigMOTD() {
        return configMOTD.getConfig();
    }

    public ConfigHardcore getConfigHardcore() {
        return configHardcore.getConfig();
    }

    public ConfigMetrics getConfigMetrics() {
        return configMetrics.getConfig();
    }

    public ConfigAuthorAdvertisements getConfigAds() {
        return configAuthorAdvertisements.getConfig();
    }

    public ConfigAutomatedBackups getConfigAutomatedBackups() {
        return configAutomatedBackups.getConfig();
    }

    public ConfigCommands getConfigCommands() {
        return configCommands.getConfig();
    }

    public ConfigItems getConfigItems() {
        return configItems.getConfig();
    }

    public ConfigLanguage getConfigLanguage() {
        return configLanguage.getConfig();
    }

    public ConfigParticles getConfigParticles() {
        return configParticles.getConfig();
    }

    public ConfigParty getConfigParty() {
        return configParty.getConfig();
    }

    public ConfigNotifications getConfigNotifications() {
        return configNotifications.getConfig();
    }

    public ConfigSuperAbilities getConfigSuperAbilities() {
        return configSuperAbilities.getConfig();
    }

    public HashMap<Material, Integer> getPartyItemWeights() {
        return partyItemWeights;
    }

    public HashMap<PartyFeature, Integer> getPartyFeatureUnlocks() {
        return partyFeatureUnlocks;
    }

    public ConfigAdmin getConfigAdmin() {
        return configAdmin.getConfig();
    }

    public ConfigMobs getConfigMobs() {
        return configMobs.getConfig();
    }

    public ConfigAcrobatics getConfigAcrobatics() {
        return configAcrobatics.getConfig();
    }

    public ConfigAlchemy getConfigAlchemy() {
        return configAlchemy.getConfig();
    }

    public ConfigArchery getConfigArchery() {
        return configArchery.getConfig();
    }

    public ConfigAxes getConfigAxes() {
        return configAxes.getConfig();
    }

    public ConfigExcavation getConfigExcavation() {
        return configExcavation.getConfig();
    }

    public ConfigFishing getConfigFishing() {
        return configFishing.getConfig();
    }

    public ConfigHerbalism getConfigHerbalism() {
        return configHerbalism.getConfig();
    }

    public ConfigMining getConfigMining() {
        return configMining.getConfig();
    }

    public ConfigRepair getConfigRepair() {
        return configRepair.getConfig();
    }

    public ConfigSwords getConfigSwords() {
        return configSwords.getConfig();
    }

    public ConfigTaming getConfigTaming() {
        return configTaming.getConfig();
    }

    public ConfigUnarmed getConfigUnarmed() {
        return configUnarmed.getConfig();
    }

    public ConfigWoodcutting getConfigWoodcutting() {
        return configWoodcutting.getConfig();
    }

    public ConfigSmelting getConfigSmelting() {
        return configSmelting.getConfig();
    }

    public ConfigSalvage getConfigSalvage() {
        return configSalvage.getConfig();
    }
}