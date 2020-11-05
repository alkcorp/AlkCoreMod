package alkcoremod.utils.mc;

import cpw.mods.fml.common.FMLCommonHandler;

public class Utils {

	public static final boolean isServer() {
		return FMLCommonHandler.instance().getEffectiveSide().isServer();
	}
	
	public static final boolean isClient() {
		return FMLCommonHandler.instance().getEffectiveSide().isClient();
	}
	
}
