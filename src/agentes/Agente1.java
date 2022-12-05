package agentes;

import agentesc.Contenedor;
import agentesc.GUIPredictor;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.awt.Image;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Agente1 extends Agent {
    
    int contadorCliente = 0;
    
    @Override
    protected void setup() { 
        addBehaviour(new Comportamiento());
    }


    class Comportamiento extends Behaviour {

        boolean terminado = false;

        @Override
        public void action() {
            
            GUIPredictor gui = (GUIPredictor)getArguments()[0];
            gui.setVisible(true);
            
            while(gui.isIspressBotonImagen()==true){
                System.out.println("Agente1: Activando GUI y recibiendo Datos....");
                String rutaImagen = gui.getRutaI();
                JFileChooser jFileChooser =new JFileChooser();
                
                //en este apartado limitados la extension en la que vamos a recibir la imagen a predecir
                FileNameExtensionFilter filtrosImagenes = new FileNameExtensionFilter("JPG and PGN","JPG","PNG");
                jFileChooser.setFileFilter(filtrosImagenes);
                int respuesta=jFileChooser.showOpenDialog(gui);
                
                //Mensajes.enviar(ACLMessage.INFORM, "AG DATOS", String.valueOf(respuesta), "COD0102", getAgent());

                if(respuesta==JFileChooser.APPROVE_OPTION){
                    System.out.println("Agente1: Subiendo Imagen....");
                    rutaImagen=jFileChooser.getSelectedFile().getPath();
                    Image imagen=new ImageIcon(rutaImagen).getImage();
                    ImageIcon icono=new ImageIcon(imagen.getScaledInstance(gui.lblMostrar.getWidth(),gui.lblMostrar.getHeight(), Image.SCALE_SMOOTH));
                    System.out.println("Agente1: Enviando imagen Agente2..");
                    Mensajes.enviarS(ACLMessage.INFORM, "AG IMAGEN", (Serializable) icono, "COD0102", getAgent());
                    gui.setIspressBotonImagen(false);
                    ACLMessage acl = blockingReceive();
                    System.out.println("Agente1: Recibiendo respuesta de Agente3, activando subida de imagen....");
                    gui.btnCargarImagen.setEnabled(true);
                }
                else{
                    gui.setIspressBotonImagen(false);
                }
                
                
            }
            if(receive() != null){
                ACLMessage acl =blockingReceive();
                System.out.println("Agente1: Recibiendo respuesta de Agente3, activando subida de imagen....");
                String mensaje = acl.getContent();
                if(mensaje.equalsIgnoreCase("Activar Datos"))
                    gui.btnCargarImagen.setEnabled(true);
            }
        }

        @Override
        public boolean done() {
            return terminado;
        }

    }
}
