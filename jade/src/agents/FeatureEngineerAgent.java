package agents;

import java.util.Map;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import utils.DataSet;
import utils.Operations;

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
        private Operations ops = new Operations();

        @Override
        public void action() {

            String[] collectionDatasets = {
                    "simple-linear-regression",
                    "multiple-linear-regression",
                    "polynomial-regression"
            };

            int indexAnalyst = (int) (Math.random() * (collectionDatasets.length - 1) - 0 + 1) + 0;

            Map<String, Object> dataSets = new DataSet().dataSets();

            double[][] dataSetToAnalyst = (double[][]) dataSets.get(collectionDatasets[indexAnalyst]);

            AID analystAgentSelected = searchAgent("qualifying-dataset");
            if (analystAgentSelected == null) {
                System.out.println("Not agent to qualifying dataset");
                return;
            }

            ACLMessage publishDataset = new ACLMessage(ACLMessage.REQUEST);
            publishDataset.addReceiver(analystAgentSelected);
            publishDataset.setContent(dataSetToAnalyst.toString());
            publishDataset.setConversationId("qualifying-dataset");
            send(publishDataset);

            // Recibir recomendación de análisis
            ACLMessage classReply = blockingReceive();
            if (classReply != null && classReply.getPerformative() == ACLMessage.INFORM) {
                String analysisType = classReply.getContent();
                System.out.println("\nBest technic to analyst is: " + analysisType + "\n");

                String regressionServiceType = "";
                switch (analysisType) {
                    case "SLR":
                        regressionServiceType = "simple-regression-service";
                        break;
                    case "MLR":
                        regressionServiceType = "multiple-regression-service";
                        break;
                    case "PR":
                        regressionServiceType = "polynomial-regression-service";
                        break;
                    default:
                        System.out.println("No se como resolver eso");
                        return;
                }

                AID regressionTechToApply = searchAgent(regressionServiceType);

                if (regressionTechToApply != null) {

                    ACLMessage regressionRequest = new ACLMessage(ACLMessage.REQUEST);
                    regressionRequest.addReceiver(regressionTechToApply);
                    regressionRequest.setContent(dataSetToAnalyst.toString());
                    regressionRequest.setConversationId("regression-analysis");
                    send(regressionRequest);

                    ACLMessage geneticAnalyst = new ACLMessage(ACLMessage.REQUEST);
                    geneticAnalyst.addReceiver(geneticAnalyst);
                    geneticAnalyst.setContent(dataSetToAnalyst.toString());
                    geneticAnalyst.setConversationId("genetic-analyst");
                    send(geneticAnalyst);

                    ACLMessage analysisCompleted = blockingReceive();
                    ACLMessage geneticCompleted = blockingReceive();
                    if (analysisCompleted != null && analysisCompleted.getPerformative == ACLMessage.INFORM) {

                        Map<String, Object> analystResults = analysisCompleted.getContent();
                        Map<String, Object> geneticResult = geneticCompleted.getContent();

                        double[] betas = null;
                        double[] betasForGenetic = null;
                

                        switch (
                            (String) analystResults.get("regression-technic")) {
                            case "SLR":

                                betas = (double[]) analystResults.get("betas");
                                betasForGenetic = (double[]) geneticResult.get("betas");

                                double[] hatsForGenetic = ops.yHat(dataSetToAnalyst, betasForGenetic);
                                double[] hats = ops.yHat(dataSetToAnalyst, betasForGenetic);

                                break;
                            case "MLR":

                                betas = (double[]) analystResults.get("betas");
                                betasForGenetic = (double[]) geneticResult.get("betas");

                                break;

                            case "PR":

                                betas = (double[]) analystResults.get("betas");
                                betasForGenetic = (double[]) geneticResult.get("betas");

                                break;
                            default:
                                break;
                        }

                    }

                }

            }

        }

        @Override
        public boolean done() {
            return finished;
        }

        private AID searchAgent(String serviceType) {
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType(serviceType);
            template.addServices(sd);
            try {
                DFAgentDescription[] results = DFService.search(myAgent, template);
                if (results.length > 0) {
                    return results[0].getName();
                }
            } catch (FIPAException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}