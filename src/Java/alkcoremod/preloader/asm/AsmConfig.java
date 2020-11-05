package alkcoremod.preloader.asm;

import java.io.File;
import java.util.ArrayList;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class AsmConfig {
	
	public static boolean loaded;
	public static Configuration config;

	public static boolean enableOreDictPatch;
	public static boolean enableTiConFluidLighting;
	public static boolean enableChunkDebugging;
	public static boolean enableCofhPatch;
	public static boolean enableGcFuelChanges;
	public static boolean enableRcFlowFix;
	public static int maxRailcraftTankProcessVolume;
	public static int maxRailcraftFluidLoaderFlow;
	public static int maxRailcraftFluidUnloaderFlow;
	public static boolean enableRcItemDupeFix;
	public static boolean enableTcAspectSafety;
	public static boolean enabledLwjglKeybindingFix;
	public static boolean enabledFixEntitySetHealth;
	public static boolean enableThaumicTinkererRepairFix;

	public static boolean disableAllLogging;
	public static boolean debugMode;

	public AsmConfig(File file) {
		if (!loaded) {
			config = new Configuration(file);
			syncConfig(true);
		}

	}

	public static void syncConfig(boolean load) {
		ArrayList<String> propOrder = new ArrayList<String>();
		ArrayList<String> propOrderDebug = new ArrayList<String>();

		try {
			if (!config.isChild && load) {
				config.load();
			}

			Property prop;	
			
			//Debug			
			prop = config.get("debug", "enableChunkDebugging", false);
			prop.comment = "Enable/Disable Chunk Debugging Features, Must Be enabled on Client and Server.";
			prop.setLanguageKey("alkcoremod.enableChunkDebugging").setRequiresMcRestart(true);
			enableChunkDebugging = prop.getBoolean(false);
			propOrderDebug.add(prop.getName());
			
			prop = config.get("debug", "enableCofhPatch", false);
			prop.comment = "Enable/Disable COFH OreDictionaryArbiter Patch (Useful for Development)";
			prop.setLanguageKey("alkcoremod.enableCofhPatch").setRequiresMcRestart(true);
			enableCofhPatch = prop.getBoolean(false);
			propOrderDebug.add(prop.getName());
			
			prop = config.get("debug", "enableOreDictPatch", false);
			prop.comment = "Enable/Disable Forge OreDictionary Patch (Useful for Development)";
			prop.setLanguageKey("alkcoremod.enableOreDictPatch").setRequiresMcRestart(true);
			enableOreDictPatch = prop.getBoolean(false);
			propOrderDebug.add(prop.getName());	
			
			
			
			
			

			//General Features
			prop = config.get("general", "enableTiConFluidLighting", true);
			prop.comment = "Enable/Disable Brightness Visuals for Tinkers Fluids, only required on the Client.";
			prop.setLanguageKey("alkcoremod.enableTiConFluidLighting").setRequiresMcRestart(true);
			enableTiConFluidLighting = prop.getBoolean(true);
			propOrder.add(prop.getName());

			prop = config.get("general", "enabledLwjglKeybindingFix", true);
			prop.comment = "Prevents the game crashing from having invalid keybinds. https://github.com/alkcorp/GTplusplus/issues/544";
			prop.setLanguageKey("alkcoremod.enabledLwjglKeybindingFix").setRequiresMcRestart(true);
			enabledLwjglKeybindingFix = prop.getBoolean(true);
			propOrder.add(prop.getName());			
			
			//Railcraft Tank fix
			prop = config.get("general", "enableRcFlowFix", true);
			prop.comment = "Allows Custom max IO rates on RC tanks";
			prop.setLanguageKey("alkcoremod.enableRcFlowFix").setRequiresMcRestart(true);
			enableRcFlowFix = prop.getBoolean(true);
			propOrder.add(prop.getName());
			
			prop = config.get("general", "maxRailcraftTankProcessVolume", 4000);
			prop.comment = "Max IO for RC fluid tanks (Not Carts). 'enableRcFlowFix' Must be enabled.";
			prop.setLanguageKey("alkcoremod.maxRailcraftTankProcessVolume").setRequiresMcRestart(true);
			maxRailcraftTankProcessVolume = prop.getInt(4000);
			propOrder.add(prop.getName());
			
			// Railcraft Loader Max flowrate
			prop = config.get("general", "maxRailcraftFluidLoaderFlow", 20);
			prop.comment = "Max Output rate for RC Fluid Loaders";
			prop.setLanguageKey("alkcoremod.maxRailcraftFluidLoaderFlow").setRequiresMcRestart(true);
			maxRailcraftFluidLoaderFlow = prop.getInt(20);
			propOrder.add(prop.getName());
			
			// Railcraft Unloader Max flowrate
			prop = config.get("general", "maxRailcraftFluidUnloaderFlow", 80);
			prop.comment = "Max Output rate for RC Fluid Unloaders";
			prop.setLanguageKey("alkcoremod.maxRailcraftFluidUnloaderFlow").setRequiresMcRestart(true);
			maxRailcraftFluidUnloaderFlow = prop.getInt(80);
			propOrder.add(prop.getName());
			
			//Railcraft Dupe Fix			
			prop = config.get("general", "enableRcItemDupeFix", true);
			prop.comment = "Fixes possible negative itemstacks";
			prop.setLanguageKey("alkcoremod.enableRcItemDupeFix").setRequiresMcRestart(true);
			enableRcItemDupeFix = prop.getBoolean(true);
			propOrder.add(prop.getName());
			
			
			//TC Aspect Safety			
			prop = config.get("general", "enableTcAspectSafety", true);
			prop.comment = "Fixes small oversights in Thaumcraft 4.";
			prop.setLanguageKey("alkcoremod.enableTcAspectSafety").setRequiresMcRestart(true);
			enableTcAspectSafety = prop.getBoolean(true);
			propOrder.add(prop.getName());
			

			config.setCategoryPropertyOrder("general", propOrder);
			config.setCategoryPropertyOrder("debug", propOrderDebug);
			if (config.hasChanged()) {
				config.save();
			}
			
		} catch (Exception var3) {
			FMLLog.log(Level.ERROR, var3, "AlkCoreMod had a problem loading it's config", new Object[0]);
		}

	}
}