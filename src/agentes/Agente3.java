
package agentes;

import agentesc.Contenedor;
import agentesc.GUIPredictor;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.apache.commons.codec.binary.Base64;

public class Agente3 extends Agent{
    @Override
    protected void setup(){ 
        addBehaviour(new Comportamiento());
    }
    
    class Comportamiento extends Behaviour{
        boolean terminado = false;
        @Override
        public void action() {
            GUIPredictor gui = (GUIPredictor)getArguments()[0];
            ACLMessage msj = blockingReceive();
            String ruta = "\"" + System.getProperty("user.dir")+"\\src\\python\\\\cargarModelo.py"+"\"";
            //Enviar path de la imagen
            String src = "src";
            String folder = "archivos";
            String foto = "imagen_a_predecir";
            String parametros = "src\\archivos\\imagen_a_predecir.jpg";
            
            /*Enviar Imagen hacia la Red Neuronal*/
            
            ProcessBuilder builder = new ProcessBuilder("python",ruta,parametros);
            System.out.println("Agente3: Empezando prediccion");
            try {
                Process process = builder.start();
                
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                
                String resultado = null;
                
                String prediccion = "";
                while ((resultado = reader.readLine())!=null){
                    if(resultado.contains("1/1") == false)
                        prediccion += resultado;
                }
                
                gui.txaResultados.setText(prediccion);
                System.out.println("Agente3: Prediccion realizada y mostrada");
                System.out.println("Enviando mensaje Agente1....");
                Mensajes.enviar(ACLMessage.INFORM, "AG GUI", "Activar Datos", "COD0301", getAgent());
                
            } catch (IOException ex) {
                Logger.getLogger(Agente3.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
}
