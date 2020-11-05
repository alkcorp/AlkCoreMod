package alkcoremod.preloader.asm;

import java.util.Map;

import alkcoremod.core.ModInfo;
import alkcoremod.preloader.Preloader_Logger;
import alkcoremod.preloader.asm.transformers.Preloader_Transformer_Handler;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
import net.minecraft.launchwrapper.Launch;

@SortingIndex(10097) 
@MCVersion(value = "1.7.10")
@IFMLLoadingPlugin.TransformerExclusions("alkcoremod.preloader")
@IFMLLoadingPlugin.Name(ModInfo.NAME+"_Plugin")
public class Preloader_FMLLoadingPlugin implements IFMLLoadingPlugin  {

	//-Dfml.coreMods.load=alkcoremod.preloader.asm.Preloader_FMLLoadingPlugin
	
	static {
		Preloader_Logger.INFO("Initializing IFMLLoadingPlugin");
	}
	
	@Override
	public String getAccessTransformerClass() {
		return null;
	}

	@Override
	public String[] getASMTransformerClass() {
		//This will return the name of the class
		return new String[]{
				Preloader_Transformer_Handler.class.getName()
		};
	}

	@Override
	public String getModContainerClass() {
		//This is the name of our dummy container
		return Preloader_DummyContainer.class.getName();
	}

	@Override
	public String getSetupClass() {
		//return Preloader_SetupClass.class.getName();
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		boolean isDeObf = (boolean) data.get("runtimeDeobfuscationEnabled");
		Preloader_Logger.INFO("runtimeDeobfuscationEnabled: "+isDeObf);		
        ModInfo.DEV_ENVIRONMENT = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
	}

}