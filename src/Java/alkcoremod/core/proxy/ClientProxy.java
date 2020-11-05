package alkcoremod.core.proxy;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy implements Runnable {


	public ClientProxy(){
		
	}

	@SubscribeEvent
	public void receiveRenderSpecialsEvent(net.minecraftforge.client.event.RenderPlayerEvent.Specials.Pre aEvent) {
		
	}

	@Override
	public void preInit(final FMLPreInitializationEvent e) {
		super.preInit(e);
	}

	@Override
	public void init(final FMLInitializationEvent e) {		
		super.init(e);
	}

	@Override
	public void postInit(final FMLPostInitializationEvent e) {
		super.postInit(e);
	}	

	@Override
	public void serverStarting(final FMLServerStartingEvent e) {
		super.serverStarting(e);
	}

	@Override
	public void onLoadComplete(FMLLoadCompleteEvent event) {
		super.onLoadComplete(event);
	}

	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}

	@Override
	public void run() {
		
	}

}
