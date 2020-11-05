package alkcoremod;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import alkcoremod.core.ModInfo;
import alkcoremod.core.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;

@MCVersion(value = "1.7.10")
@Mod(modid = ModInfo.MODID, name = ModInfo.NAME, version = ModInfo.VERSION, dependencies = "required-after:Forge; after:TConstruct; after:PlayerAPI; after:dreamcraft; after:IC2; after:ihl; after:psychedelicraft; after:gregtech; after:Forestry; after:MagicBees; after:CoFHCore; after:Growthcraft; after:Railcraft; after:CompactWindmills; after:ForbiddenMagic; after:MorePlanet; after:PneumaticCraft; after:ExtraUtilities; after:Thaumcraft; after:rftools; after:simplyjetpacks; after:BigReactors; after:EnderIO; after:tectech; after:GTRedtech; after:beyondrealitycore; after:OpenBlocks; after:IC2NuclearControl; after:TGregworks; after:StevesCarts; after:xreliquary;")
public class AlkCoreMod implements ActionListener {

	// Mod Instance
	@Mod.Instance(ModInfo.MODID)
	public static AlkCoreMod instance;

	// GT++ Proxy Instances
	@SidedProxy(clientSide = "alkcoremod.core.proxy.ClientProxy", serverSide = "alkcoremod.core.proxy.CommonProxy")
	public static CommonProxy proxy;

	// Pre-Init
	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		
	}

	// Init
	@Mod.EventHandler
	public void init(final FMLInitializationEvent event) {
		
	}

	// Post-Init
	@Mod.EventHandler
	public void postInit(final FMLPostInitializationEvent event) {
		
	}

	@EventHandler
	public synchronized void serverStarting(final FMLServerStartingEvent event) {
		
	}

	@Mod.EventHandler
	public synchronized void serverStopping(final FMLServerStoppingEvent event) {
		
	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {

	}

	/**
	 * This {@link EventHandler} is called after the
	 * {@link FMLPostInitializationEvent} stages of all loaded mods executes
	 * successfully. {@link #onLoadComplete(FMLLoadCompleteEvent)} exists to
	 * inject recipe generation after Gregtech and all other mods are entirely
	 * loaded and initialized.
	 * 
	 * @param event
	 *            - The {@link EventHandler} object passed through from FML to
	 *            {@link #GTplusplus()}'s {@link #instance}.
	 */
	@Mod.EventHandler
	public void onLoadComplete(FMLLoadCompleteEvent event) {
		
	}


}