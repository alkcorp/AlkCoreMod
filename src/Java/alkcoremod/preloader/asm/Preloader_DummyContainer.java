package alkcoremod.preloader.asm;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import alkcoremod.core.ModInfo;
import alkcoremod.preloader.Preloader_Logger;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.*;

public class Preloader_DummyContainer extends DummyModContainer {

	public Preloader_DummyContainer() {
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId = ModInfo.MODID+"_Plugin";
		meta.name = ModInfo.NAME+"_Plugin";
		meta.version = ModInfo.VERSION;
		meta.credits = "Roll Credits ...";
		meta.authorList = Arrays.asList("Alkalus");
		meta.description = "";
		meta.url = "";
		meta.updateUrl = "";
		meta.screenshots = new String[0];
		meta.logoFile = "";
		//meta.dependencies = (List<ArtifactVersion>) CORE_Preloader.DEPENDENCIES;
		Preloader_Logger.INFO("Initializing DummyModContainer");

	}
	
	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}

	@Subscribe
	public void modConstruction(FMLConstructionEvent evt){
		Preloader_Logger.INFO("Constructing DummyModContainer");

	}
	
	@Subscribe
	public void preInit(FMLPreInitializationEvent event) {
		
	}

	@Subscribe
	public void init(FMLInitializationEvent evt) {
		
	}

	@Subscribe
	public void postInit(FMLPostInitializationEvent evt) {
		
	}
	
}