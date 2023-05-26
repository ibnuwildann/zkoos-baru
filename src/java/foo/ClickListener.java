/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foo;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;

/**
 *
 * @author PERSONAL
 */
public class ClickListener implements EventListener<Event> {

    public ClickListener() {
    }

    @Override
    public void onEvent(Event t) throws Exception {
        Executions.sendRedirect(null);
    }
    
}
