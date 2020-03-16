package org.d3ifcool.cubeacon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Beacon {

    @SerializedName("information")
    @Expose
    private Information information;
    @SerializedName("algorithm")
    @Expose
    private List<Algorithm> algorithm = null;

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public List<Algorithm> getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(List<Algorithm> algorithm) {
        this.algorithm = algorithm;
    }

    class Information {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("node")
        @Expose
        private String node;
        @SerializedName("tags")
        @Expose
        private String tags;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNode() {
            return node;
        }

        public void setNode(String node) {
            this.node = node;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

    }

    class Algorithm {

        @SerializedName("neightbor")
        @Expose
        private List<String> neightbor = null;
        @SerializedName("cost")
        @Expose
        private List<Double> cost = null;
        @SerializedName("sign")
        @Expose
        private List<String> sign = null;
        @SerializedName("edge")
        @Expose
        private List<String> edge = null;

        public List<String> getNeightbor() {
            return neightbor;
        }

        public void setNeightbor(List<String> neightbor) {
            this.neightbor = neightbor;
        }

        public List<Double> getCost() {
            return cost;
        }

        public void setCost(List<Double> cost) {
            this.cost = cost;
        }

        public List<String> getSign() {
            return sign;
        }

        public void setSign(List<String> sign) {
            this.sign = sign;
        }

        public List<String> getEdge() {
            return edge;
        }

        public void setEdge(List<String> edge) {
            this.edge = edge;
        }

    }
}
