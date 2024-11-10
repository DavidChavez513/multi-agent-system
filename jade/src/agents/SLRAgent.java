package agents;

import java.util.Map;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import utils.Regressions;


public class SLRAgent extends Agent {

    Regressions regressions = new Regressions();

    @Override
    protected void setup() {
        System.out.println("Hello! Agent " + getAID().getName() + " is ready.");

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

            Map<String, Object> results = regressions.fitByLinearRegression();


            System.out.println("Datos de Betas");

            

            finished = true; // This is just a simple example, so we finish immediately
        }

        @Override
        public boolean done() {
            return finished;
        }
    }
}