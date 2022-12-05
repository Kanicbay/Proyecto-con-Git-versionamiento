package agentes;

import agentesc.GUIPredictor;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
        

public class Agente2 extends Agent {
    
    @Override
    protected void setup(){
        addBehaviour(new Comportamiento());
    }
    class Comportamiento extends CyclicBehaviour{

        @Override
        public void action() {
            try {
                GUIPredictor gui = (GUIPredictor)getArguments()[0];
                ACLMessage msj = blockingReceive();
                String idC = msj.getConversationId();
                if(idC.equalsIgnoreCase("COD0102")){
                    //Obteniendo Imagen
                    System.out.println("Agente2: Recibiendo la imagen....");
                    ImageIcon icono = (ImageIcon)msj.getContentObject();
                    //Mostrando la imagen
                    System.out.println("Agente2: Mostrando imagen en la GUI....");
                    gui.lblMostrar.setIcon(icono);
                    gui.getBtnCargarImagen().setEnabled(false);
                    //Guardando imagen en archivos como imagen_a_predecir
                    System.out.println("Agente2: Guardando imagen como imagen_a_predecir.jpg....");
                    Image img = icono.getImage();
                    BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
                    Graphics2D g2 = bi.createGraphics();
                    g2.drawImage(img, 0, 0,null);
                    g2.dispose();
                    ImageIO.write(bi, "jpg", new File("src\\archivos\\imagen_a_predecir.jpg"));
                    
                    System.out.println("Agente2: Enviando respueta Agente3 para predecir");
                    //Mensajes.enviarS(ACLMessage.INFORM, "AG ANN", carro, "COD0203", getAgent());
                    Mensajes.enviarS(ACLMessage.INFORM, "AG RESULTADOS", (Serializable) icono, "COD0203", getAgent());
                }
            } catch (UnreadableException ex) {
                Logger.getLogger(Agente2.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Agente2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
