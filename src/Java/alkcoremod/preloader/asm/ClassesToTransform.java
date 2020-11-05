package alkcoremod.preloader.asm;

public class ClassesToTransform {

	/*
	 * LWJGL
	 */
	
	public static final String LWJGL_KEYBOARD = "org.lwjgl.input.Keyboard";

	/*
	 * Vanilla
	 */

	public static final String MINECRAFT_GAMESETTINGS = "net.minecraft.client.settings.GameSettings";
	public static final String MINECRAFT_GAMESETTINGS_OBF = "bbj";

	/*
	 * Forge
	 */

	public static final String FORGE_CHUNK_MANAGER = "net.minecraftforge.common.ForgeChunkManager";
	public static final String FORGE_ORE_DICTIONARY = "net.minecraftforge.oredict.OreDictionary";

	/*
	 * COFH
	 */

	public static final String COFH_ORE_DICTIONARY_ARBITER = "cofh.core.util.oredict.OreDictionaryArbiter";

	/*
	 * Tinkers Construct
	 */

	public static final String TINKERS_FLUID_BLOCK = "tconstruct.smeltery.blocks.TConstructFluid";

	/*
	 * Railcraft
	 */

	public static final String RAILCRAFT_FLUID_HELPER = "mods.railcraft.common.fluids.FluidHelper";
	public static final String RAILCRAFT_TILE_FLUID_LOADER = "mods.railcraft.common.blocks.machine.gamma.TileFluidLoader";
	public static final String RAILCRAFT_INVENTORY_TOOLS = "mods.railcraft.common.util.inventory.InvTools";

	/*
	 * Galacticraft
	 */

	public static final String GALACTICRAFT_FLUID_UTILS = "micdoodle8.mods.galacticraft.core.util.FluidUtil";
	public static final String GALACTICRAFT_TILE_ENTITY_FUEL_LOADER = "micdoodle8.mods.galacticraft.core.tile.TileEntityFuelLoader";
	public static final String GALACTICRAFT_ENTITY_AUTO_ROCKET = "micdoodle8.mods.galacticraft.api.prefab.entity.EntityAutoRocket";

	
	/*
	 * Thaumcraft
	 */

	public static final String THAUMCRAFT_ITEM_WISP_ESSENCE = "thaumcraft.common.items.ItemWispEssence";
	public static final String THAUMCRAFT_CRAFTING_MANAGER = "thaumcraft.common.lib.crafting.ThaumcraftCraftingManager";
	public static final String THAUMCRAFT_TILE_ALCHEMY_FURNACE = "thaumcraft.common.tiles.TileAlchemyFurnace";
	public static final String THAUMICTINKERER_TILE_REPAIRER = "thaumic.tinkerer.common.block.tile.TileRepairer";

}
