package agents;

import java.util.Map;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import utils.Regressions;


public class SLRAgent extends Agent {

    Regressions regressions = new Regressions();

    @Override
    protected void setup() {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("simple-linear-regression");
        sd.setName("Simple Linear Agent");
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);

            System.out.println("Agente Registrado con exito");
        } catch (FIPAException fe) {
            // TODO: handle exception

            fe.printStackTrace();
        }

        addBehaviour(new CalculateLinearRegression());
    }

    @Override
    protected void takeDown() {
        System.out.println("Agent " + getAID().getName() + " is terminating.");
    }

    private class CalculateLinearRegression extends Behaviour {

        private boolean finished = false;

        @Override
        public void action() {

            System.out.println("Agent " + getAID().getName() + " is executing behaviour.");

            Map<String, Object> results = regressions.linearRegressionAnalysis();

            System.out.println(results.get("beta0"));

            System.out.println("Datos de Betas");

            

            finished = true; // This is just a simple example, so we finish immediately
        }

        @Override
        public boolean done() {
            return finished;
        }
    }
}