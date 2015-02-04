package orf.frc4450.robot.events;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import orf.frc4450.robot.events.handlers.EventHandler;
import orf.frc4450.robot.events.handlers.Listener;
import orf.frc4450.robot.logging.ORFLogger;

/**
 * Class to manage firing and handling events.
 * <p>
 * This is unused for now, but may be implemented in the future, and is available for custom
 * implementations
 *
 * @author ShortCircuit908
 */
public final class EventBus{
	private static EventBus event_bus = new EventBus();
	ORFLogger logger = new ORFLogger(this);
	private List<Listener> handler_list = new LinkedList<>();

	/**
	 * Default constructor to create the EventBus instance
	 */
	protected EventBus(){

	}

	/**
	 * @return The current EventBus instance
	 */
	public static EventBus getEventBus(){
		return event_bus;
	}

	/**
	 * Register an event listener class
	 *
	 * @param listener The listener to register
	 */
	public void registerEvents(Listener listener){
		handler_list.add(listener);
		logger.info("Registered listener class " + listener.getClass().getSimpleName());
	}

	/**
	 * Unregister an event listener class
	 *
	 * @param listener The listener to unregister
	 */
	public void unregisterEvents(Listener listener){
		handler_list.remove(listener);
		logger.info("Unregistered listener class " + listener.getClass().getSimpleName());
	}

	/**
	 * Fire events. Use spooky reflection magic to pass the event on to all the EventHandlers
	 *
	 * @param event The event to call
	 */
	public void callEvent(final RobotEvent event){
		for(Listener listener : handler_list){
			// Get an array containing all methods contained in the listener
			for(Method method : listener.getClass().getDeclaredMethods()){
				// Check if the method is an EventHandler
				Annotation annotation = method.getDeclaredAnnotation(EventHandler.class);
				if(annotation != null){
					// We only want to deal with EventHandlers that take a single parameter
					if(method.getParameterCount() != 1){
						logger.warning("Found event handler with an invalid number of arguments:\n" +
								listener.getClass().getCanonicalName() + "." + method.getName());
						logger.warning("Skipping method invocation");
					}
					/*
					 * If the parameter type is the same as the event fired, invoke that method with
					 * the event as the parameter
					 */
					else if(method.getParameterTypes()[0].equals(event.getClass())){
						try{
							method.invoke(event);
						}
						// Bad stuff happened
						catch(InvocationTargetException | IllegalAccessException e){
							logger.exception(e);
						}
					}
				}
			}
		}
	}
}
