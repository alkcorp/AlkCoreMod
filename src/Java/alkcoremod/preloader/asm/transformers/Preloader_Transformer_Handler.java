package alkcoremod.preloader.asm.transformers;

import static alkcoremod.preloader.asm.ClassesToTransform.*;

import java.io.File;
import java.io.IOException;

import alkcoremod.objects.data.AutoMap;
import alkcoremod.preloader.DevHelper;
import alkcoremod.preloader.Preloader_Logger;
import alkcoremod.preloader.asm.AsmConfig;
import cpw.mods.fml.relauncher.CoreModManager;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;

public class Preloader_Transformer_Handler implements IClassTransformer {

	public static final AsmConfig mConfig;	
	public static final AutoMap<String> IC2_WRENCH_PATCH_CLASS_NAMES = new AutoMap<String>();

	static {
		mConfig = new AsmConfig(new File("config/AlkCoreMod/asm.cfg"));
		Preloader_Logger.INFO("Config Location: "+AsmConfig.config.getConfigFile().getAbsolutePath());
		Preloader_Logger.INFO("Is DevHelper Valid? "+DevHelper.mIsValidHelper);
	}

	private static Boolean mObf = null;

	public boolean checkObfuscated() {
		if (mObf != null) {
			return mObf;
		}		
		boolean obfuscated = false;
		try {
			obfuscated = !(boolean) ReflectionHelper.findField(CoreModManager.class, "deobfuscatedEnvironment").get(null);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			byte[] bs;
			try {
				bs = Launch.classLoader.getClassBytes("net.minecraft.world.World");
				if (bs != null) {
					obfuscated = false;
				} else {
					obfuscated = true;
				}
			} catch (IOException e1) {
				e1.printStackTrace();
				obfuscated = false;
			}
		}	
		mObf = obfuscated;
		return obfuscated;
	}

	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		
		// Is this environment obfuscated? (Extra checks just in case some weird shit happens during the check)
		final boolean obfuscated = checkObfuscated();

		// Fix LWJGL index array out of bounds on keybinding IDs
		if ((transformedName.equals(LWJGL_KEYBOARD) || transformedName.equals(MINECRAFT_GAMESETTINGS_OBF) || transformedName.equals(MINECRAFT_GAMESETTINGS)) && AsmConfig.enabledLwjglKeybindingFix) {	
			boolean isClientSettingsClass = false;
			if (!transformedName.equals("org.lwjgl.input.Keyboard")) {
				isClientSettingsClass = true;
			}			
			Preloader_Logger.INFO("LWJGL Keybinding index out of bounds fix", "Transforming "+transformedName);
			return new ClassTransformer_LWJGL_Keyboard(basicClass, isClientSettingsClass).getWriter().toByteArray();
		}		

		//Enable mapping of Tickets and loaded chunks. - Forge
		if (transformedName.equals(FORGE_CHUNK_MANAGER) && AsmConfig.enableChunkDebugging) {	
			Preloader_Logger.INFO("Chunkloading Patch", "Transforming "+transformedName);
			return new ClassTransformer_Forge_ChunkLoading(basicClass, false).getWriter().toByteArray();
		}

		// Fix the OreDictionary - Forge
		if (transformedName.equals(FORGE_ORE_DICTIONARY) && AsmConfig.enableOreDictPatch) {
			
		}
		
		// Log Handling of CodeChicken
		if (transformedName.equals("codechicken.nei.guihook.GuiContainerManager")) {	
			Preloader_Logger.INFO("CodeChicken GuiContainerManager Patch", "Transforming "+transformedName);
			return new ClassTransformer_CC_GuiContainerManager(basicClass).getWriter().toByteArray();
		}
		// Fix the OreDictionary COFH
		if (transformedName.equals(COFH_ORE_DICTIONARY_ARBITER) && (AsmConfig.enableCofhPatch || !obfuscated)) {
			Preloader_Logger.INFO("COFH", "Transforming "+transformedName);
			return new ClassTransformer_COFH_OreDictionaryArbiter(basicClass).getWriter().toByteArray();
		}

		// Fix Tinkers Fluids
		if (transformedName.equals(TINKERS_FLUID_BLOCK) && AsmConfig.enableTiConFluidLighting) {
			Preloader_Logger.INFO("Bright Fluids", "Transforming "+transformedName);
			return new ClassTransformer_TiConFluids("getLightValue", obfuscated, basicClass).getWriter().toByteArray();
		}

		//Fix RC stuff
		//Patching PROCESS_VOLUME to allow more transfer limits
		if (transformedName.equals(RAILCRAFT_FLUID_HELPER) && (AsmConfig.enableRcFlowFix && AsmConfig.maxRailcraftTankProcessVolume != 4000)) {	
			Preloader_Logger.INFO("Railcraft PROCESS_VOLUME Patch", "Transforming "+transformedName);
			return new ClassTransformer_Railcraft_FluidHelper(basicClass, obfuscated).getWriter().toByteArray();
		}		
		//Patching TRANSFER_RATE in Fluid Loaders/Unloaders
		if ((transformedName.equals(RAILCRAFT_TILE_FLUID_LOADER) && AsmConfig.maxRailcraftFluidLoaderFlow != 20) || (transformedName.equals("mods.railcraft.common.blocks.machine.gamma.TileFluidUnloader") && AsmConfig.maxRailcraftFluidUnloaderFlow != 80)) {	
			Preloader_Logger.INFO("Railcraft TRANSFER_RATE Patch", "Transforming "+transformedName);
			return new ClassTransformer_Railcraft_FluidCartHandling(basicClass, obfuscated, transformedName).getWriter().toByteArray();
		}		
		//Fix Weird glitch involving negative itemstacks.
		if (transformedName.equals(RAILCRAFT_INVENTORY_TOOLS) && AsmConfig.enableRcItemDupeFix) {	
			Preloader_Logger.INFO("Railcraft negative ItemStack Fix", "Transforming "+transformedName);
			return new ClassTransformer_Railcraft_InvTools(basicClass, obfuscated).getWriter().toByteArray();
		}

		//Fix GC stuff
		if (AsmConfig.enableGcFuelChanges) {
			if (transformedName.equals(GALACTICRAFT_FLUID_UTILS)) {	
				Preloader_Logger.INFO("Galacticraft FluidUtils Patch", "Transforming "+transformedName);
				return new ClassTransformer_GC_FluidUtil(basicClass, false).getWriter().toByteArray();
			}
			if (transformedName.equals(GALACTICRAFT_TILE_ENTITY_FUEL_LOADER)) {
				Preloader_Logger.INFO("Galacticraft Fuel_Loader Patch", "Transforming "+transformedName);
				return new ClassTransformer_GC_FuelLoader(basicClass, false).getWriter().toByteArray();
			}
			if (transformedName.equals(GALACTICRAFT_ENTITY_AUTO_ROCKET)) {
				Preloader_Logger.INFO("Galacticraft EntityAutoRocket Patch", "Transforming "+transformedName);
				return new ClassTransformer_GC_EntityAutoRocket(basicClass, false).getWriter().toByteArray();
			}
		}


		/*
		 * Fix Thaumcraft Shit
		 */
		
		// Patching ItemWispEssence to allow invalid item handling
		if (transformedName.equals(THAUMCRAFT_ITEM_WISP_ESSENCE) && AsmConfig.enableTcAspectSafety) {	
			Preloader_Logger.INFO("Thaumcraft WispEssence_Patch", "Transforming "+transformedName);
			return new ClassTransformer_TC_ItemWispEssence(basicClass, obfuscated).getWriter().toByteArray();
		}
		// Patching ThaumcraftCraftingManager to fix infinite loops
		if (transformedName.equals(THAUMCRAFT_CRAFTING_MANAGER)) {	
			Preloader_Logger.INFO("Thaumcraft CraftingManager Patch", "Transforming "+transformedName);
			return new ClassTransformer_TC_ThaumcraftCraftingManager(basicClass).getWriter().toByteArray();
		}
		// Patching Alchemy Furnace to prevent tps hogging
		if (transformedName.equals(THAUMCRAFT_TILE_ALCHEMY_FURNACE)) {	
			Preloader_Logger.INFO("Thaumcraft Alchemy Furnace Patch", "Transforming "+transformedName);
			return new ClassTransformer_TC_AlchemicalFurnace(basicClass).getWriter().toByteArray();
		}

		return basicClass;
	}



}
