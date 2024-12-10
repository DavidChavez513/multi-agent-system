package agents;

import java.util.Map;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import utils.Regressions;


public class MLRAgent extends Agent {

    private double[][] dataset;

    @Override
    protected void setup() {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("multiple-linear-regression");
        sd.setName("Multiple Linear Agent");
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);

            System.out.println("Agente Registrado con exito");
        } catch (FIPAException fe) {
            // TODO: handle exception

            fe.printStackTrace();
        }

        addBehaviour(new AnalystDataSet());
        addBehaviour(new ReceptDataMessage());
    }

    public class ReceptDataMessage extends Behaviour {
    
        @Override
        public void action() {
            ACLMessage msgReceived = receive();

            if (msgReceived != null && msgReceived.getConversation().equals("analyst-dataset")) {
                
            }

        }

        
    }


    class AnalystDataSet extends Behaviour {
    
        Regressions analystTechnics = new Regressions();

        
        @Override
        protected void action() {

            System.out.println("Agent " + getAID().getName() + " is executing behaviour.");

            Map<String, Object> results = analystTechnics.multipleLinearRegressionAnalysis();

            System.out.println(results.get("beta0"));

            System.out.println("Datos de Betas");

        }

    }

    
}