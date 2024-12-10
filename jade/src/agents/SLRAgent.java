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


public class SLRAgent extends Agent {

    Regressions regressions = new Regressions();
    double[][] dataToAnalysis;

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

        
    }

    @Override
    protected void takeDown() {
        System.out.println("Agent " + getAID().getName() + " is terminating.");
    }

    private class ReceiveDataToAnalysis extends Behaviour {

        boolean finished = false;
    
        @Override
        protected void action() {
            ACLMessage analysisData = receive();
            if (msg != null && msg.getConversationId().equals("regression-analysis")) {
                dataToAnalysis = analysisData.getConten();
                addBehaviour(new CalculateLinearRegression(dataToAnalysis));

                finished = true;
            } else {
                block();
            }
        }

        @Override
        public boolean done() {
            return finished;
        }
        
    }

    private class CalculateLinearRegression extends Behaviour {

        private double[][] dataset;

        private boolean finished = false;

        public CalculateLinearRegression(double[][] _dataset){
            this.dataset = _dataset;
        }

        @Override
        public void action() {

            System.out.println("Agent " + getAID().getName() + " is executing behaviour.");

            Map<String, Object> results = regressions.linearRegressionAnalysis(this.dataset);

            results.put("regression-technic", "SLR");

            System.out.println(results.get("beta0"));

            System.out.println("Datos de Betas");

            ACLMessage reply = msg.createReply();
            reply.setPerformative(ACLMessage.INFORM);
            reply.setContent(results);
            send(reply);

            finished = true; // This is just a simple example, so we finish immediately
        }

        @Override
        public boolean done() {
            return finished;
        }
    }





}