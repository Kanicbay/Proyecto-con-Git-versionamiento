
package agentesc;

import agentes.*;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;
import java.util.logging.Level;
import java.util.logging.Logger;
        
public class Contenedor {
    
    AgentContainer agentContainer;
    
    public void contenedor(){
        jade.core.Runtime runtime = jade.core.Runtime.instance();
        Profile p = new ProfileImpl(null, 1099, null);
        agentContainer = runtime.createMainContainer(p);
        agregarAgentes();
    }
    
}
