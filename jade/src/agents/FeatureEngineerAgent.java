package agents;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

public class FeatureEngineerAgent extends Agent {

    

    @Override
    public void setup() {

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("search-regression-type");
        sd.setName("Regression Selector Type");
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);

            System.out.println("Agente Registrado con exito");
        } catch (FIPAException fe) {
            // TODO: handle exception

            fe.printStackTrace();
        }

    }

    
    @Override
    public void takeDown() {

    }


    private class SearchRegressionTechnic extends Behaviour {

        private boolean finished = false;

        @Override
        public void action() {

            
            
        }

        @Override
        public boolean done() {
            return finished;
        }
    }
}