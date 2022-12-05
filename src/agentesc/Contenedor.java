
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
    
    private void agregarAgentes(){
        try{
            Object[] gui = new Object[]{new GUIPredictor()};
            agentContainer.createNewAgent("AG GUI", Agente1.class.getName(), gui).start();
            agentContainer.createNewAgent("AG IMAGEN", Agente2.class.getName(), gui).start();
            agentContainer.createNewAgent("AG RESULTADOS", Agente3.class.getName(), gui).start();
        } catch (StaleProxyException ex){
            Logger.getLogger(Contenedor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void crearHijos(String alias, Object[] conocimiento){
        try{
            agentContainer.createNewAgent(alias, Agente1.class.getName(), conocimiento).start();
        } catch (StaleProxyException ex) {
            Logger.getLogger(Contenedor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
