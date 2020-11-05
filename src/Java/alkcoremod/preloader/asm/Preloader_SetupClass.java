package alkcoremod.preloader.asm;

import java.util.Map;

import alkcoremod.preloader.Preloader_Logger;
import cpw.mods.fml.relauncher.IFMLCallHook;

public class Preloader_SetupClass implements IFMLCallHook {

	@Override
	public Void call() throws Exception {
		Preloader_Logger.INFO("Executing IFMLCallHook");
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		
	}

}
