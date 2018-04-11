package com.excilys.computer.database.console.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class Observer {
	private Map<String, List<IObservable>> listeners;
	
	public Observer() {
		listeners = new HashMap<String, List<IObservable>>();
	}
	
	public void Register(String mot, IObservable methode)
    {
        if (!listeners.containsKey(mot)) {
            listeners.put(mot, new ArrayList<IObservable>());
        }
        
        listeners.get(mot).add(methode);
    }

    public Boolean Trigger(String input)
    {
    	List<IObservable> inputlisteners = listeners.get(input);

        if (inputlisteners == null || inputlisteners.size() == 0) {
            return false;
        }
        for (IObservable observable : inputlisteners)
        {
            if (!observable.execute()) {
                return false;
            }
        }
        return true;
    }
}
