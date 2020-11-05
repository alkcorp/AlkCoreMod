package alkcoremod.utils.mc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import alkcoremod.core.LoadedMods;
import alkcoremod.objects.log.Logger;
import alkcoremod.utils.data.MathUtils;
import alkcoremod.utils.data.StringUtils;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemUtils {

	public static ItemStack getSimpleStack(final Item x) {
		return getSimpleStack(x, 1);
	}

	public static ItemStack getSimpleStack(final Block x) {
		return simpleMetaStack(Item.getItemFromBlock(x), 0, 1);
	}


	public static ItemStack getSimpleStack(final Block x, int i) {
		if (i == 0) {
			return getSimpleStack(x, i, 1);			
		}

		return getSimpleStack(x, 0, i);
	}

	public static ItemStack getSimpleStack(final Block x, int meta, int i) {
		return simpleMetaStack(Item.getItemFromBlock(x), meta, i);
	}

	public static ItemStack getSimpleStack(final Item x, final int i) {
		try {
			final ItemStack r = new ItemStack(x, i);
			return r.copy();
		} catch (final Throwable e) {
			return null;
		}
	}

	public static ItemStack getSimpleStack(final ItemStack x, final int i) {
		try {
			final ItemStack r = x.copy();
			r.stackSize = i;
			return r;
		} catch (final Throwable e) {
			return null;
		}
	}

	public static final int WILDCARD_VALUE = Short.MAX_VALUE;

	public static ItemStack getWildcardStack(final Item x) {
		final ItemStack y = new ItemStack(x, 1, WILDCARD_VALUE);
		return y;
	}

	public static ItemStack getWildcardStack(final ItemStack x) {
		final ItemStack y = ItemUtils.simpleMetaStack(x, WILDCARD_VALUE, 1);
		return y;
	}


	public static void addItemToOreDictionary(ItemStack stack, final String oreDictName, boolean useWildcardMeta) {
		if (useWildcardMeta) {
			stack = ItemUtils.getWildcardStack(stack);
		}
		try {
			OreDictionary.registerOre(oreDictName, stack);
		} catch (final NullPointerException e) {
			Logger.ERROR(ItemUtils.getItemName(stack) + " not registered. [NULL]");
		}
	}

	public static void addItemToOreDictionary(final ItemStack stack, final String oreDictName) {
		addItemToOreDictionary(stack, oreDictName, false);
	}

	public static ItemStack getItemStackWithMeta(final boolean MOD, final String FQRN, final String itemName,
			final int meta, final int itemstackSize) {
		if (MOD) {
			try {
				Item em = null;
				final Item em1 = getItemFromFQRN(FQRN);
				// Utils.LOG_WARNING("Found: "+em1.getUnlocalizedName()+":"+meta);
				if (em1 != null) {
					if (null == em) {
						em = em1;
					}
					if (em != null) {
						final ItemStack metaStack = new ItemStack(em, itemstackSize, meta);
						return metaStack;
					}
				}
				return null;
			} catch (final NullPointerException e) {
				Logger.ERROR(itemName + " not found. [NULL]");
				return null;
			}
		}
		return null;
	}

	public static ItemStack simpleMetaStack(final String FQRN, final int meta, final int itemstackSize) {
		try {
			Item em = null;
			final Item em1 = getItemFromFQRN(FQRN);
			// Utils.LOG_WARNING("Found: "+em1.getUnlocalizedName()+":"+meta);
			if (em1 != null) {
				if (null == em) {
					em = em1;
				}
				if (em != null) {
					final ItemStack metaStack = new ItemStack(em, itemstackSize, meta);
					return metaStack;
				}
			}
			return null;
		} catch (final NullPointerException e) {
			Logger.ERROR(FQRN + " not found. [NULL]");
			return null;
		}
	}

	public static ItemStack simpleMetaStack(ItemStack simpleStack, int meta, int size) {
		return simpleMetaStack(simpleStack.getItem(), meta, size);
	}

	public static ItemStack simpleMetaStack(final Item item, int meta, int size) {
		if (item == null) {
			return null;
		}
		if (meta < 0 || meta > Short.MAX_VALUE) {
			meta = 0;
		}
		if (size < 0 || size > 64) {
			size = 1;
		}
		//Logger.INFO("Found Metastack: " + item.getUnlocalizedName() + ":" + meta);
		//Logger.INFO(""+ReflectionUtils.getMethodName(0));
		//Logger.INFO(""+ReflectionUtils.getMethodName(1));
		//Logger.INFO(""+ReflectionUtils.getMethodName(2));
		//Logger.INFO(""+ReflectionUtils.getMethodName(3));
		//Logger.INFO(""+ReflectionUtils.getMethodName(4));
		final ItemStack metaStack = new ItemStack(item, size, meta);
		return metaStack;
	}

	public static ItemStack simpleMetaStack(final Block block, final int meta, final int size) {
		return simpleMetaStack(Item.getItemFromBlock(block), meta, size);
	}

	public static ItemStack getCorrectStacktype(final String fqrn, final int stackSize) {
		final String oreDict = "ore:";
		ItemStack temp;
		if (fqrn.toLowerCase().contains(oreDict.toLowerCase())) {
			final String sanitizedName = fqrn.replace(oreDict, "");
			temp = ItemUtils.getItemStackFromFQRN(sanitizedName, stackSize);
			return temp;
		}
		final String[] fqrnSplit = fqrn.split(":");
		String temp1;
		String temp2;
		temp1 = fqrnSplit[1];
		if (fqrnSplit.length < 3) {
			temp2 = "0";
		} else {
			temp2 = fqrnSplit[2];
		}
		temp = ItemUtils.getItemStackWithMeta(LoadedMods.MiscUtils, fqrn, temp1, Integer.parseInt(temp2), stackSize);
		return temp;
	}

	public static Item getItemFromFQRN(final String fqrn) // fqrn = fully qualified resource name
	{
		final String[] fqrnSplit = fqrn.split(":");
		return GameRegistry.findItem(fqrnSplit[0], fqrnSplit[1]);
	}

	public static ItemStack getItemStackFromFQRN(final String fqrn, final int Size) // fqrn = fully qualified resource name
	{
		Logger.INFO("Trying to split string '"+fqrn+"'.");
		final String[] fqrnSplit = fqrn.split(":");
		if (fqrnSplit.length < 2) {
			return null;
		}
		else {
			if (fqrnSplit.length == 2) {
				Logger.INFO("Mod: "+fqrnSplit[0]+", Item: "+fqrnSplit[1]);
				return GameRegistry.findItemStack(fqrnSplit[0], fqrnSplit[1], Size);
			}
			else if (fqrnSplit.length == 3 && fqrnSplit[2] != null && fqrnSplit[2].length() > 0) {
				Logger.INFO("Mod: "+fqrnSplit[0]+", Item: "+fqrnSplit[1]+", Meta: "+fqrnSplit[2]);
				ItemStack aStack = GameRegistry.findItemStack(fqrnSplit[0], fqrnSplit[1], Size);
				int aMeta = Integer.parseInt(fqrnSplit[2]);
				if (aStack != null && (aMeta >= 0 && aMeta <= Short.MAX_VALUE)){
					return ItemUtils.simpleMetaStack(aStack, aMeta, Size);
				}
				else {
					Logger.INFO("Could not find instance of Item: "+fqrnSplit[1]);
					
				}
			}

		}
		return null;
	}

	public static ItemStack getItemStackOfAmountFromOreDict(String oredictName, final int amount) {
		String mTemp = oredictName;

		if (oredictName.contains("-") || oredictName.contains("_")) {
			mTemp = StringUtils.sanitizeString(mTemp, new char[] {'-', '_'});			
		}
		else {
			mTemp = StringUtils.sanitizeString(mTemp);			
		}
		
		if (oredictName.contains("rod")) {
			String s = "stick"+oredictName.substring(3);
			oredictName = s;
		}

		// Banned Materials and replacements for GT5.8 compat.
		if (oredictName.toLowerCase().contains("ingotclay")) {
			return getSimpleStack(Items.clay_ball, amount);
		}
		
		final ArrayList<ItemStack> oreDictList = OreDictionary.getOres(mTemp);
		if (!oreDictList.isEmpty()) {
			final ItemStack returnValue = oreDictList.get(0).copy();
			returnValue.stackSize = amount;
			return returnValue;
		}
		Logger.INFO("Failed to find `" + oredictName + "` in OD.");
		return null;
	}


	public static String getArrayStackNames(final FluidStack[] aStack) {
		String itemNames = "Fluid Array: ";
		for (final FluidStack alph : aStack) {
			if (alph != null) {
				final String temp = itemNames;
				itemNames = temp + ", " + alph.getLocalizedName() + " x" + alph.amount;
			} else {
				final String temp = itemNames;
				itemNames = temp + ", " + "null" + " x" + "0";
			}
		}
		return itemNames;
	}

	public static String getArrayStackNames(final ItemStack[] aStack) {
		String itemNames = "";
		int aPos = 0;
		for (final ItemStack alph : aStack) {			
			if (alph == null) {
				continue;
			}
			if (alph != null) {
				final String temp = itemNames;
				itemNames = temp + (aPos > 0 ? ", " : "") + alph.getDisplayName() + " x" + alph.stackSize;
				aPos++;
			}
		}
		return itemNames;
	}

	public static String[] getArrayStackNamesAsArray(final ItemStack[] aStack) {
		final String[] itemNames = aStack == null ? new String[] {} : new String[aStack.length];
		if (aStack != null){ 
			Logger.INFO(""+aStack.length);
		}

		if (aStack == null || aStack.length < 1) {
			return itemNames;
		}

		int arpos = 0;
		for (final ItemStack alph : aStack) {			
			if (alph == null) {
				continue;
			}			
			try {
				itemNames[arpos] = alph.getDisplayName();
				arpos++;
			}
			catch (Throwable t) {
				t.printStackTrace();
			}
		}
		return itemNames;

	}

	public static String getFluidArrayStackNames(final FluidStack[] aStack) {
		String itemNames = "Fluid Array: ";
		for (final FluidStack alph : aStack) {
			final String temp = itemNames;
			itemNames = temp + ", " + alph.getFluid().getName() + " x" + alph.amount;
		}
		return itemNames;

	}

	public static ItemStack getGregtechCircuit(final int Meta) {
		return ItemUtils.getItemStackWithMeta(LoadedMods.Gregtech, "gregtech:gt.integrated_circuit", "Gregtech Circuit",
				Meta, 0);
	}

	public static ItemStack[] getBlockDrops(final ArrayList<ItemStack> blockDrops) {
		if (blockDrops == null) {
			return null;
		}
		if (blockDrops.isEmpty()) {
			return null;
		}
		final ItemStack[] outputs = new ItemStack[blockDrops.size()];
		short forCounter = 0;
		for (final ItemStack I : blockDrops) {
			outputs[forCounter++] = I;
		}
		return outputs;
	}

	private static Map<Item, String> mModidCache = new HashMap<Item, String>();

	private static String getModId(final Item item) {
		if (mModidCache.containsKey(item)) {
			return mModidCache.get(item);
		}
		String value = "";
		try {
			final GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(item);
			if (id != null) {
				final String modname = (id.modId == null ? id.name : id.modId);
				value = ((id == null) || id.modId.equals("")) ? "minecraft" : modname;
			}
		} catch (final Throwable t) {
			try {
				final UniqueIdentifier t2 = GameRegistry.findUniqueIdentifierFor(Block.getBlockFromItem(item));
				if (t2 != null) {
					final String modname = (t2.modId == null ? t2.name : t2.modId);
					value = ((t2 == null) || t2.modId.equals("")) ? "minecraft" : modname;
				}
			} catch (final Throwable t3) {
				t3.printStackTrace();
				value = "bad modid";
			}
		}
		if (!mModidCache.containsKey(item)) {
			return mModidCache.put(item, value);
		}
		return value;
	}

	public static String getModId(final ItemStack key) {
		return getModId(key.getItem());
	}

	public static String getFluidName(FluidStack aFluid) {
		return aFluid != null ? aFluid.getFluid().getLocalizedName(aFluid) : "NULL";
	}

	public static String getFluidName(Fluid aFluid) {
		return aFluid != null ? aFluid.getLocalizedName() : "NULL";
	}

	public static String getItemName(ItemStack aStack) {
		if (aStack == null) {
			return "ERROR - Empty Stack";
		}
		String aDisplay = null;
		try {
			aDisplay = ("" + StatCollector
					.translateToLocal(aStack.getItem().getUnlocalizedNameInefficiently(aStack) + ".name"))
					.trim();
			if (aStack.hasTagCompound()) {
				if (aStack.stackTagCompound != null && aStack.stackTagCompound.hasKey("display", 10)) {
					NBTTagCompound nbttagcompound = aStack.stackTagCompound.getCompoundTag("display");

					if (nbttagcompound.hasKey("Name", 8)) {
						aDisplay = nbttagcompound.getString("Name");
					}
				}
			}
		} catch (Throwable t) {

		}
		if (aDisplay == null || aDisplay.length() <= 0) {
			aDisplay = aStack.getUnlocalizedName() + ":" + aStack.getItemDamage();
		} else {
			aDisplay += " | Meta: " + aStack.getItemDamage();
		}
		return aDisplay;
	}

	public static String getUnlocalizedItemName(ItemStack aStack) {
		if (aStack == null) {
			return "ERROR.Empty.Stack";
		}
		String aDisplay = null;
		try {
			aDisplay = (aStack.getUnlocalizedName()).trim();
		} 
		catch (Throwable t) {
			aDisplay = aStack.getItem().getUnlocalizedName();
		}
		if (aDisplay == null || aDisplay.length() <= 0) {
			aDisplay = aStack.getItem().getUnlocalizedNameInefficiently(aStack);
		}
		return aDisplay;
	}

	public static ItemStack getEnchantedBook(Enchantment aEnch, int aLevel) {
		return enchantItem(new ItemStack(Items.enchanted_book), aEnch, aLevel);		
	}

	public static ItemStack enchantItem(ItemStack aStack, Enchantment aEnch, int aLevel) {
		Items.enchanted_book.addEnchantment(aStack, new EnchantmentData(aEnch, aLevel));
		return aStack;	
	}
	public static void hideItemFromNEI(ItemStack aItemToHide) {
		codechicken.nei.api.API.hideItem(aItemToHide);		
	}

	public static ItemStack depleteStack(ItemStack aStack) {
		return depleteStack(aStack, 1);
	}

	public static ItemStack depleteStack(ItemStack aStack, int aAmount) {
		final int cap = aStack.stackSize;
		if (cap >= 1 && cap >= aAmount) {
			ItemStack aDepStack = aStack.copy();
			aDepStack.stackSize = (MathUtils.balance((aDepStack.stackSize - 1), 0, 64));
			if (aDepStack.stackSize > 0) {
				return aDepStack;				
			}
		}
		return null;
	}

	public static boolean areItemsEqual(ItemStack aStack1, ItemStack aStack2) {
		return areItemsEqual(aStack1, aStack2, true);
	}
	
	public static boolean areItemsEqual(ItemStack aStack1, ItemStack aStack2, boolean aIgnoreNBT) {
		return areStacksEqual(aStack1, aStack2, aIgnoreNBT);
	}

    public static boolean areStacksEqual(ItemStack aStack1, ItemStack aStack2) {
        return areStacksEqual(aStack1, aStack2, false);
    }

    public static boolean areStacksEqual(ItemStack aStack1, ItemStack aStack2, boolean aIgnoreNBT) {
    	short W = OreDictionary.WILDCARD_VALUE;
        return aStack1 != null && aStack2 != null && aStack1.getItem() == aStack2.getItem() && (aIgnoreNBT || ((aStack1.getTagCompound() == null) == (aStack2.getTagCompound() == null)) && (aStack1.getTagCompound() == null || aStack1.getTagCompound().equals(aStack2.getTagCompound()))) && (Items.feather.getDamage(aStack1) == Items.feather.getDamage(aStack2) || Items.feather.getDamage(aStack1) == W || Items.feather.getDamage(aStack2) == W);
    }

}
